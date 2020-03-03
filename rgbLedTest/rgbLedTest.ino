struct Color {
  int r;
  int g;
  int b;
};

int red_light_pin= 9;
int green_light_pin = 10;
int blue_light_pin = 11;
const int N_COLORS = 2;
Color colors[N_COLORS];
int cur_color = 0;
String color_codes[N_COLORS] = {"red", "blue"};
void setup() {
  pinMode(red_light_pin, OUTPUT);
  pinMode(green_light_pin, OUTPUT);
  pinMode(blue_light_pin, OUTPUT);

  colors[0] = {10,0,0};
  colors[1] = {0,0,10};
  Serial.begin(9600);
}

void loop() {
  Serial.println(color_codes[cur_color]);
  RGB_color(colors[cur_color].r, colors[cur_color].g, colors[cur_color].b);
  cur_color = (cur_color+1)%N_COLORS;
  delay(1500);
}
void RGB_color(int red_light_value, int green_light_value, int blue_light_value)
 {
  analogWrite(red_light_pin, red_light_value);
  analogWrite(green_light_pin, green_light_value);
  analogWrite(blue_light_pin, blue_light_value);
}
