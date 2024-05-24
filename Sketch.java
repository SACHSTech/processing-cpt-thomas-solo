import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  PImage imgPlayer;

  public void settings() {
    size(960, 540);
  }

  public void setup() {
    background(102, 204, 255);
    imgPlayer = loadImage("Player.png");
    imgPlayer.resize(imgPlayer.width*2, imgPlayer.height*2);
  }

  public void draw() {
	  image(imgPlayer, 3, 3);
  }
  
}