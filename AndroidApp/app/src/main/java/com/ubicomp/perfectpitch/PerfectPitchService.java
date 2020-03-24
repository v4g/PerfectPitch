package com.ubicomp.perfectpitch;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ubicomp.perfectpitch.dummy.PlayContent;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.content.ContentValues.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PerfectPitchService extends IntentService {
   private BluetoothAdapter bluetoothAdapter;
    private boolean mScanning;
    public final String DEVICE_NAME = "PerfectPitch";
    private Handler handler = new Handler();
    private BluetoothGatt bluetoothGatt;
    private Timer mTimer;
    private BluetoothGattCharacteristic characteristic;
    private BluetoothGatt gatt;
    private UUID SERVICE_UUID = UUID.fromString("4fafc201-1fb5-459e-8fcc-c5c9c331914b");
    private UUID CHARACTERISTIC_UUID = UUID.fromString("beb5483e-36e1-4688-b7f5-ea07361b26a8");
    private boolean deviceFound = false;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    private static final long NOTIFY_INTERVAL = 2000;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    public PerfectPitchService() {
        super("PerfectPitchService");
        mTimer = new Timer();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1, notification);

        if (intent != null) {
            SoundManager.initialize(this);

            // Initializes Bluetooth adapter.
            final BluetoothManager bluetoothManager =
                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            bluetoothAdapter = bluetoothManager.getAdapter();
            if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
                Intent i = new Intent("PServiceMessage");
                i.putExtra("bluetoothDisabled", true);
                LocalBroadcastManager.getInstance(this).sendBroadcast(i);
                return;
            }
            scanLeDevice(bluetoothAdapter.isEnabled());
            try {
                while (true) {
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {

            }

        }
    }

    @Override
    public void onDestroy() {
        if (mTimer != null)
            mTimer.cancel();
        sendFinishedNotification();
        switchOffLED();
        super.onDestroy();
    }

    private void switchOffLED() {
        if (characteristic != null) {
            characteristic.setValue("off");
            gatt.writeCharacteristic(characteristic);
        }
        if (bluetoothGatt != null) {
            bluetoothGatt.disconnect();
        }
    }

    private void sendFinishedNotification() {
        Intent i = new Intent("PServiceMessage");
        i.putExtra("finished", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        return;
    }

    private void sendDeviceNotFoundNotification() {
        Intent i = new Intent("PServiceMessage");
        i.putExtra("deviceNotFound", true);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
        return;
    }

    private void scanLeDevice(final boolean enable) {
        final Context context = this;
        final BluetoothGattCallback gattCallback =
                new BluetoothGattCallback() {
                    @Override
                    public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                                        int newState) {
                        if (newState == BluetoothProfile.STATE_CONNECTED) {
                            Log.i(TAG, "Connected to GATT server.");
                            Log.i(TAG, "Attempting to start service discovery:" +
                                    bluetoothGatt.discoverServices());

                        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                            Log.i(TAG, "Disconnected from GATT server.");
                        }
                    }

                    @Override
                    // New services discovered
                    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                        if (status == BluetoothGatt.GATT_SUCCESS) {
                            Log.d("PerfectPitchService", "Services discovered");
                            BluetoothGattService s = gatt.getService(SERVICE_UUID);
                            BluetoothGattCharacteristic c = s.getCharacteristic(CHARACTERISTIC_UUID);
                            startTransmitting(gatt, c);
                        } else {
                            Log.w(TAG, "onServicesDiscovered received: " + status);
                        }
                    }

                };
        final BluetoothAdapter.LeScanCallback leScanCallback =
                new BluetoothAdapter.LeScanCallback() {
                    @Override
                    public void onLeScan(final BluetoothDevice device, int rssi,
                                         byte[] scanRecord) {
                        if (device.getName() != null && device.getName().equalsIgnoreCase(DEVICE_NAME)) {
                            bluetoothGatt = device.connectGatt(context, false, gattCallback);
                            Log.d("PerfectPitchService", bluetoothGatt.toString());
                            deviceFound = true;
                        }
                        Log.d("Perfect Pitch", device.getName() + device.getAddress() + device.toString());

                    }
                };
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    if (!deviceFound) {
                        sendDeviceNotFoundNotification();
                    }
                    bluetoothAdapter.stopLeScan(leScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(leScanCallback);
        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(leScanCallback);
        }

    }

    public void startTransmitting(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        this.gatt = gatt;
        this.characteristic = characteristic;
        Log.d("PerfectPitchService", this.characteristic.getUuid().toString());
        mTimer.scheduleAtFixedRate(new PerfectPitchService.TransmitToDevice(), 0, NOTIFY_INTERVAL);
    }

    class TransmitToDevice extends TimerTask {
        Map<Integer, String> colorToCodeMap = new HashMap<Integer, String>();
        MusicNote musicNotes[];
        int i = 0;

        public TransmitToDevice() {
            colorToCodeMap.put(Color.MAGENTA, "m");
            colorToCodeMap.put(Color.WHITE, "w");
            colorToCodeMap.put(Color.BLUE, "b");
            colorToCodeMap.put(Color.CYAN, "c");
            colorToCodeMap.put(Color.GREEN, "g");
            colorToCodeMap.put(Color.YELLOW, "y");
            colorToCodeMap.put(Color.RED, "r");
        }

        @Override
        public void run() {
            // run on another thread
            Note item = PlayContent.ITEMS.get(i);
            Log.d("PerfectPitchService", "Playing" + item.name());
            SoundManager.getInstance().stop();
            SoundManager.getInstance().play(item);
            String col = colorToCodeMap.get(item.getColor());
            characteristic.setValue(col);
            i = (i + 1) % PlayContent.ITEMS.size();
            gatt.writeCharacteristic(characteristic);
        }
    }


}
