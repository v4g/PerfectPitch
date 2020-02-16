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
  out.setVolume(2.0);
} 

void draw()
{
  if ( myPort.available() > 0) 
  {  // If data is available,
    val = myPort.readStringUntil('\n');
    if (val != null) {
      String[] result = val.split("\n");
      if (compareStrings("red", result[0].trim())){        
        out.playNote( 0.0, 1.0, "G3" );
      } else if (compareStrings("blue", result[0].trim())){        
        out.playNote( 0.0, 1.0, "C3" );
      } 
      println(result[0]); //print it out in the console

    }
  }
}

boolean compareStrings(String s1, String s2){
  if (s1.length() != s2.length()){
    return false;
  }
  for (int i = 0; i < s1.length(); i++) {
    if (s1.charAt(i) != s2.charAt(i)){
      return false;
    }
  }
  return true;  
}
