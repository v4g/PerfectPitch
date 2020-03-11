#include "esp32-hal-ledc.h"
#include "RGBLed.h"

#define RED 'r'
#define GREEN 'g'
#define BLUE 'b'

const int off[3] = {0, 0, 0};
const int red[3] = {10, 0, 0};
const int green[3] = {0, 10, 0};
const int blue[3] = {0, 0, 10};

const int redChannel = 0;
const int greenChannel = 1;
const int blueChannel = 2;

const int freq = 5000;
const int resolution = 8;

RGBLed::RGBLed(int redPin, int greenPin, int bluePin) {
  this->redPin = redPin;
  this->greenPin = greenPin;
  this->bluePin = bluePin;
}

void RGBLed::setup() {
  ledcSetup(redChannel, freq, resolution);
  ledcSetup(blueChannel, freq, resolution);
  ledcSetup(greenChannel, freq, resolution);
  
  // attach the channel to the GPIO to be controlled
  ledcAttachPin(redPin, redChannel);
  ledcAttachPin(greenPin, greenChannel);
  ledcAttachPin(bluePin, blueChannel);
}

void RGBLed::updateLed(char color) {
  const int *rgbVal;
  switch (color) {
    case RED:
      rgbVal = red;
      break;
    case GREEN:
      rgbVal = green;
      break;
    case BLUE:
      rgbVal = blue;
      break;
    default:
      rgbVal = off;
  }
  writeLed(rgbVal);
}

void RGBLed::writeLed(const int *rgbVal) {
  ledcWrite(redChannel, *(rgbVal + 0));
  ledcWrite(greenChannel, *(rgbVal + 1));
  ledcWrite(blueChannel, *(rgbVal + 2));
}
