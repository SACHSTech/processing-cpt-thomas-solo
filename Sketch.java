import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  PImage imgPlayer;
  PImage imgGrass;
  PImage imgDirt;

  ArrayList<Integer> intPlatformY = new ArrayList<Integer>();
  ArrayList<Integer> intPlatformX = new ArrayList<Integer>();


  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolLeft = false;
  boolean boolRight = false;

  float fltGravity = 0;
  float fltPX = 100;
  float fltPY = 300;
  
  public void settings() {
    size(960, 540);
  }

  public void setup() {
    background(102, 204, 255);
    loadImages();
  }

  public void loadImages() {

    imgPlayer = loadImage("Player/Idle1.png");
    imgPlayer.resize(imgPlayer.width*2, imgPlayer.height*2);

    imgGrass = loadImage("Objects/Grass.png");
    imgGrass.resize(imgGrass.width*2, imgGrass.height*2);

    imgDirt = loadImage("Objects/Dirt.png");
    imgDirt.resize(imgDirt.width*2, imgDirt.height*2);

  }

  public void draw() {
	  
    background(102, 204, 255);

    player();
  
    level();

  }

  public void player() {

    if(boolRight == true){
      fltPX += 5;
    }
    if(boolLeft == true){
      fltPX -= 5;
    }
    if(boolUp == true && isOnTop() == true){
      fltPY -= 5;
      fltGravity = 11;
    }

    fltPY -= fltGravity;

    if(isOnTop() == true){
      fltGravity = 0;
    } else {
      fltGravity -= 0.5;
    }

    image(imgPlayer, fltPX, fltPY);

  }

  public void level() {
    for(int x = 0; x < width / 2; x += imgGrass.width - 2){
      
      createPlatform(x, height - imgGrass.height * 3, imgGrass);

    }

    for(int y = height - imgDirt.height * 2 - 2; y < height; y += imgDirt.height - 2){
      for(int x = 0; x < width / 2; x += imgDirt.width - 2){
        
        createPlatform(x, y, imgDirt);

      }
    }
    
  }

  public void createPlatform(int x, int y, PImage image) {
    intPlatformX.add(x);
    intPlatformY.add(y);
    image(image, x, y);
  }

  public boolean isOnTop() {

    for(int i = 0; i < intPlatformX.size(); i++){
      if (fltPY + imgPlayer.height >= intPlatformY.get(i) + 28 && fltPY + imgPlayer.height <= intPlatformY.get(i) + 28 + imgGrass.height && fltPX + imgPlayer.width - 5 >= intPlatformX.get(i) && fltPX + 5 <= intPlatformX.get(i) + imgGrass.width) {
        if (fltPY + imgPlayer.height > intPlatformY.get(i) + 28){
            fltPY -= 1;
        }
          return true;
        }

    }

    return false;

  }

  public void animate() {
   
  }
  
  public void keyPressed() {
    // change bools
    if (key == 'w'){
      boolUp = true;
    }
    if (key == 'a'){
      boolLeft = true;
    }
    if (key == 's'){
      boolDown = true;
    }
    if (key == 'd'){
      boolRight = true;
    }
  }

  public void keyReleased() {
    // change bools for movement
    if (key == 'w'){
      boolUp = false;
    }
    if (key == 'a'){
      boolLeft = false;
    }
    if (key == 's'){
      boolDown = false;
    }
    if (key == 'd'){
      boolRight = false;
    }
  }

}