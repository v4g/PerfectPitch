package com.ubicomp.perfectpitch;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ubicomp.perfectpitch.dummy.PlayContent;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button quizButton;
    private Button historyButton;

    private static final int REQUEST_ENABLE_BT = 11;
    private boolean isServiceRunning = false;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quizButton = (Button) findViewById(R.id.quizButton);
        quizButton.setOnClickListener(this);

        historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(this);

        playButton = (Button) findViewById(R.id.playButton);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
        }
        SoundManager.initialize(this);
        PlayContent.load(this);

    }

    private void deviceNotFoundToast() {
        Log.d("PerfectPitchService", "Device Not Found");
        Toast.makeText(this, R.string.device_not_found, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == quizButton) {
            // navigate to quiz screen
            Intent intentQuiz = new Intent(getApplicationContext(), QuizSelectNotesActivity.class);
            startActivity(intentQuiz);
        }
        if (view == historyButton) {
            // navigate to history screen
            Intent intentHistory = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(intentHistory);
        }
    }

    public void onPlayClick(View view) {
        if (isServiceRunning) {
            stopService();
        } else {
            if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
                Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show();
            } else {
                startService();
            }
        }
    }

    public void onSettingsClick(View view) {
        // navigate to quiz screen
        Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intentSettings);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("PServiceMessage"));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("PerfectPitch", "Received a broadcast message");
            if (intent.hasExtra("bluetoothDisabled")) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            if (intent.hasExtra("finished")) {
                Log.d("PerfectPitchService", "Service Stopped");
                serviceStopped();
            }
            if (intent.hasExtra("deviceNotFound")) {
                deviceNotFoundToast();
                stopService();
            }
        }
    };

    public void startService() {
        Intent i = new Intent(getApplicationContext(), PerfectPitchService.class);
        startService(i);
        serviceStarted();
        Toast.makeText(this, R.string.searching_device, Toast.LENGTH_SHORT).show();

    }

    public void stopService() {
        Intent i = new Intent(getApplicationContext(), PerfectPitchService.class);
        stopService(i);
        serviceStopped();
    }

    private void serviceStopped() {
        isServiceRunning = false;
        playButton.setBackgroundResource(android.R.drawable.ic_media_play);
    }

    private void serviceStarted() {
        isServiceRunning = true;
        playButton.setBackgroundResource(android.R.drawable.ic_media_pause);
    }
}
