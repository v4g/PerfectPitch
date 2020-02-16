import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

import processing.serial.*;
Serial myPort;  // Create object from Serial class
String val; 
Minim minim;
AudioPlayer player;
AudioInput input;
AudioOutput out;
void setup()
{
  // I know that the first port in the serial list on my mac
  // is Serial.list()[0].
  // On Windows machines, this generally opens COM1.
  // Open whatever port is the one you're using.
  String portName = Serial.list()[0]; //change the 0 to a 1 or 2 etc. to match your port
  myPort = new Serial(this, portName, 9600);
  minim = new Minim(this);
  input = minim.getLineIn();
  out = minim.getLineOut();
} 

void draw()
{
  if ( myPort.available() > 0) 
  {  // If data is available,
    val = myPort.readStringUntil('\n');
    out.playNote( 2.0, 2.9, "C3" );
  } 
println(val); //print it out in the console
}
