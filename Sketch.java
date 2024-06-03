import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  PImage imgGrass;
  PImage imgDirt;

  ArrayList<Integer> intPlatformY = new ArrayList<Integer>();
  ArrayList<Integer> intPlatformX = new ArrayList<Integer>();


  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolLeft = false;
  boolean boolRight = false;
  boolean boolShift = false;
  float timeSince = 0;
  int intFrame = 0;

  Player player = new Player();
  
  public void settings() {
    size(960, 540);
  }

  public void setup() {
    background(102, 204, 255);
    loadImages();
  }

  public void loadImages() {

    player.imgPlayer = loadImage("Player/Idle1.png");
    player.imgPlayer.resize(player.imgPlayer.width*2, player.imgPlayer.height*2);
    intFrame = 0;
    timeSince = millis();

    imgGrass = loadImage("Objects/Grass.png");
    imgGrass.resize(imgGrass.width*2, imgGrass.height*2);

    imgDirt = loadImage("Objects/Dirt.png");
    imgDirt.resize(imgDirt.width*2, imgDirt.height*2);

  }

  public void draw() {
	  
    background(102, 204, 255);

    collision();
    player.run(boolUp, boolDown, boolLeft, boolRight, boolShift);
    image(player.imgPlayer, player.fltPX, player.fltPY);
  
    level();

    animate();

  }

  public void collision() {
    isOnTop();
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
      if (player.fltPY + player.imgPlayer.height >= intPlatformY.get(i) + 28 && player.fltPY + player.imgPlayer.height <= intPlatformY.get(i) + 28 + imgGrass.height && player.fltPX + player.imgPlayer.width - 5 >= intPlatformX.get(i) && player.fltPX + 5 <= intPlatformX.get(i) + 5) {
        while (player.fltPY + player.imgPlayer.height > intPlatformY.get(i) + 28){
            player.fltPY -= 1;
        }
          player.strState = "ground";
          return true;  
        }

    }

    player.strState = "air";
    return false;

  }

  public void animate() {

    if(boolLeft == true || boolRight == true && player.strState == "ground"){
      
      if(intFrame == 0){
        intFrame = 7;
        timeSince = millis();
      } 

      if(intFrame >= 7 && intFrame <= 14 && millis() >= timeSince + 100){
        intFrame++;
        timeSince = millis();
      }

      if(intFrame == 14){
        intFrame = 7;
      }

    } else if (player.strState == "ground"){

      if(intFrame != 1 && millis() >= timeSince + 500){
        intFrame = 1;
        timeSince = millis();
      } else if (intFrame == 1 && millis() >= timeSince + 500){
        intFrame = 0;
        timeSince = millis();
      }
      

    } else if (player.strState == "air"){

      if(intFrame != 2 || intFrame != 3 || intFrame != 4 || intFrame != 5){
        intFrame = 2;
        timeSince = millis();
      }
      
      if (millis() >= timeSince + 500){
        intFrame = 3;
        timeSince = millis();
      }

    }

    if(intFrame == 0){
      player.imgPlayer = loadImage("Player/Idle1.png");
    } else if (intFrame == 1){
      player.imgPlayer = loadImage("Player/Idle2.png");
    } else if (intFrame == 2){
      player.imgPlayer = loadImage("Player/Jump1.png");
    } else if (intFrame == 3){
      player.imgPlayer = loadImage("Player/Jump2.png");
    } else if (intFrame == 4){
      player.imgPlayer = loadImage("Player/Jump3.png");
    } else if (intFrame == 5){
      player.imgPlayer = loadImage("Player/JumpLand.png");
    } else if (intFrame == 7){
      player.imgPlayer = loadImage("Player/Run1.png");
    } else if (intFrame == 8){
      player.imgPlayer = loadImage("Player/Run2.png");
    } else if (intFrame == 9){
      player.imgPlayer = loadImage("Player/Run3.png");
    } else if (intFrame == 10){
      player.imgPlayer = loadImage("Player/Run4.png");
    } else if (intFrame == 11){
      player.imgPlayer = loadImage("Player/Run5.png");
    } else if (intFrame == 12){
      player.imgPlayer = loadImage("Player/Run6.png");
    } else if (intFrame == 13){
      player.imgPlayer = loadImage("Player/Run7.png");
    } else if (intFrame == 14){
      player.imgPlayer = loadImage("Player/Run8.png");
    }

    player.imgPlayer.resize(player.imgPlayer.width*2, player.imgPlayer.height*2);
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

    if (keyCode == 16){
      boolShift = true;
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

    if (keyCode == 16){
      boolShift = false;
    }
  }

}