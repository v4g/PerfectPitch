#include <dummy.h>
#include <BLEDevice.h>
#include <BLEUtils.h>
#include <BLEServer.h>
#include "RGBLed.h"

/* General notes:
 *  feather: PWM available on every GPIO pin but we have to set the PWM properties
 *  bluetooth: code modeled after the ESP32 BLE example sketch: Arduino BLE_write
 *  BLE archived repo: https://github.com/nkolban/ESP32_BLE_Arduino
 *    (the BLE code is included in Arduino now)
 */

 // RGB LED pins
 int red_pin = 14;
 int green_pin = 15;
 int blue_pin = 27;

 RGBLed led_out = RGBLed(red_pin, green_pin, blue_pin);

 // BLE service and characteristic UUIDs
#define SERVICE_UUID        "4fafc201-1fb5-459e-8fcc-c5c9c331914b"
#define CHARACTERISTIC_UUID "beb5483e-36e1-4688-b7f5-ea07361b26a8"

// BLE on write callback
class CharacteristicCallbacks: public BLECharacteristicCallbacks {
    void onWrite(BLECharacteristic *pCharacteristic) {
      std::string value = pCharacteristic->getValue();

      if (value.length() > 0) {
        led_out.updateLed(value[0]);
      }
    }
};

// turn off LED when disconnecting from bluetooth
class ServerCallbacks: public BLEServerCallbacks {
  void onDisconnect(BLEServer *pServer) {
    // turn off the LED when disconnected
    led_out.updateLed('f');
  }
};
 
void setup() {
  led_out.setup();

  // BLE setup
  Serial.begin(115200);
  Serial.println("Starting bluetooth setup");
  BLEDevice::init("PerfectPitch");
  BLEServer *pServer = BLEDevice::createServer();

  BLEService *pService = pServer->createService(SERVICE_UUID);

  pServer->setCallbacks(new ServerCallbacks);

 BLECharacteristic *pCharacteristic=pService->createCharacteristic(
                                       CHARACTERISTIC_UUID,
                                       BLECharacteristic::PROPERTY_WRITE
                                     );
  pCharacteristic->setCallbacks(new CharacteristicCallbacks());

  pCharacteristic->setValue("off");
  pService->start();

  BLEAdvertising *pAdvertising = pServer->getAdvertising();
  pAdvertising->start();
}

void loop() {
  delay(50);
}
