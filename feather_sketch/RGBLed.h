#ifndef RGBLed_h
#define RGBLed_h

class RGBLed {
  int redPin, greenPin, bluePin;
  private:
    void writeLed(const int*);
  public:
    RGBLed(int, int, int);
    void setup();
    void updateLed(char);
};
#endif
