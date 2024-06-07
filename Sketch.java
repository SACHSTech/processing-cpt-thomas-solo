import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  PImage imgGrass;
  PImage imgDirt;
  PImage imgPlat;
  PImage imgSpike;

  ArrayList<Integer> intPlatformY = new ArrayList<Integer>();
  ArrayList<Float> intPlatformX = new ArrayList<Float>();
  int intObjectLimit = 0;
  int intMoveStage = 0;

  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolLeft = false;
  boolean boolRight = false;
  boolean boolShift = false;
  
  float timeSince = 0;
  int intFrame = 0;
  PImage[] arrSprite = new PImage[17];
  PImage[] arrFlippedSprite = new PImage[arrSprite.length];

  Player player = new Player();
  String strAnim = "idle";

  float fltXOffset = 0;
  float fltYOffset = 0;
  
  public void settings() {
    size(960, 540);
  }

  public void setup() {
    background(102, 204, 255);
    loadImages();
    loadSprites();
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

    imgPlat = loadImage("Objects/Platform.png");
    imgPlat.resize(imgPlat.width*2, imgPlat.height*2);

    imgSpike = loadImage("Objects/Spikes.png");
    imgSpike.resize(imgSpike.width*2, imgSpike.height*2);

  }

  public void loadSprites() {
    arrFlippedSprite[0] = loadImage("FlipPlayer/FlipIdle1.png");
    arrFlippedSprite[1] = loadImage("FlipPlayer/FlipIdle2.png");
    arrFlippedSprite[2] = loadImage("FlipPlayer/FlipJump1.png");
    arrFlippedSprite[3] = loadImage("FlipPlayer/FlipJump2.png");
    arrFlippedSprite[4] = loadImage("FlipPlayer/FlipJump3.png");
    arrFlippedSprite[5] = loadImage("FlipPlayer/FlipJumpLand.png");
    arrFlippedSprite[6] = loadImage("FlipPlayer/FlipRun1.png");   //id 6
    arrFlippedSprite[7] = loadImage("FlipPlayer/FlipRun2.png");
    arrFlippedSprite[8] = loadImage("FlipPlayer/FlipRun3.png");
    arrFlippedSprite[9] = loadImage("FlipPlayer/FlipRun4.png");
    arrFlippedSprite[10] = loadImage("FlipPlayer/FlipRun5.png");
    arrFlippedSprite[11] = loadImage("FlipPlayer/FlipRun6.png");
    arrFlippedSprite[12] = loadImage("FlipPlayer/FlipRun7.png");
    arrFlippedSprite[13] = loadImage("FlipPlayer/FlipRun8.png");
    arrFlippedSprite[14] = loadImage("FlipPlayer/FlipRunStart.png");
    arrFlippedSprite[15] = loadImage("FlipPlayer/FlipRunStart2.png");
    arrFlippedSprite[16] = loadImage("FlipPlayer/FlipRunStop.png");

    for (int i = 0; i < arrFlippedSprite.length; i++){
      arrFlippedSprite[i].resize(arrFlippedSprite[i].width*2, arrFlippedSprite[i].height*2);
    }

    arrSprite[0] = loadImage("Player/Idle1.png");
    arrSprite[1] = loadImage("Player/Idle2.png");
    arrSprite[2] = loadImage("Player/Jump1.png");
    arrSprite[3] = loadImage("Player/Jump2.png");
    arrSprite[4] = loadImage("Player/Jump3.png");
    arrSprite[5] = loadImage("Player/JumpLand.png");
    arrSprite[6] = loadImage("Player/Run1.png");
    arrSprite[7] = loadImage("Player/Run2.png");
    arrSprite[8] = loadImage("Player/Run3.png");
    arrSprite[9] = loadImage("Player/Run4.png");
    arrSprite[10] = loadImage("Player/Run5.png");
    arrSprite[11] = loadImage("Player/Run6.png");
    arrSprite[12] = loadImage("Player/Run7.png");
    arrSprite[13] = loadImage("Player/Run8.png");
    arrSprite[14] = loadImage("Player/RunStart.png");
    arrSprite[15] = loadImage("Player/RunStart2.png");
    arrSprite[16] = loadImage("Player/RunStop.png");

    for (int i = 0; i < arrSprite.length; i++){
      arrSprite[i].resize(arrSprite[i].width*2, arrSprite[i].height*2);
    }
  }

  public void draw() {
	  
    background(102, 204, 255);

    if(!player.boolDead){
      collision();
      player.run(boolUp, boolDown, boolLeft, boolRight, boolShift);
      animate();
    } else {
      if(timeSince + 500 <= millis()){
        player.boolDead = false;
        player.fltGravity = 0;
        player.fltPX = 480 - 60;
        player.fltPY = 300;

        for(int i = 0; i < intPlatformX.size(); i++){
          intPlatformX.remove(i);
          intPlatformY.remove(i);
        }

        fltXOffset = 0;
      }
    }
  
    image(player.imgPlayer, player.fltPX, player.fltPY);
  
    level();

  }

  public void collision() {
    if(player.fltPY > height){
      player.boolDead = true;
      timeSince = millis();
    }

    isOnTop();
    
    for(int i = 0; i < intPlatformX.size(); i++){
      if(player.fltPX + player.imgPlayer.width >= intPlatformX.get(i) && player.fltPX <= intPlatformX.get(i) + imgPlat.width && player.fltPY + player.imgPlayer.height > intPlatformY.get(i) && player.fltPY <= intPlatformY.get(i) + imgPlat.height){
        
        if (boolRight == true && player.fltPY + player.imgPlayer.height < intPlatformY.get(i)){
          player.fltPX -= 5;
        } else if(boolLeft == true && player.fltPY + player.imgPlayer.height < intPlatformY.get(i)){
          player.fltPX += 5;
        }
  
      }
    }
  }

  public void level() {
    
    intObjectLimit = 58;
    intMoveStage = -330;

    // 0th chunk
    for(int x = imgGrass.width * 4 * -1 + 8; x < -10; x += imgGrass.width - 2){
      
      createPlatform(x - intMoveStage, height - imgGrass.height * 12, imgGrass);

    }

    // first chunk
    for(int x = 0; x < imgGrass.width * 4; x += imgGrass.width - 2){
      
      createPlatform(x - intMoveStage, height - imgGrass.height * 3, imgGrass);

    }
    for(int y = height - imgDirt.height * 2 - 2; y < height; y += imgDirt.height - 2){
      for(int x = 0; x < imgGrass.width * 4; x += imgDirt.width - 2){
        
        createPlatform(x - intMoveStage, y, imgDirt);

      }
    }

    // second chunk
    for(int x = imgGrass.width * 7; x < imgGrass.width * 10; x += imgGrass.width - 2){
      
      createPlatform(x - intMoveStage, height - imgGrass.height * 4, imgGrass);

    }
    for(int y = height - imgDirt.height * 3 - 2; y < height; y += imgDirt.height - 2){
      for(int x = imgGrass.width * 7; x < imgGrass.width * 10; x += imgDirt.width - 2){
        
        createPlatform(x - intMoveStage, y, imgDirt);

      }
    }

    // platform chunk 1
    for(int x = imgPlat.width * 13; x < width; x += imgPlat.width){
      createPlatform(x - intMoveStage, height - imgPlat.height * 8, imgPlat);
    }

    // platform chunk 2
    for(int x = 0; x < imgPlat.width * 11; x += imgPlat.width){
      createPlatform(x - intMoveStage, height - imgPlat.height * 12, imgPlat);
    }

    // spike
    createPlatform(imgSpike.width * 6, height - imgSpike.height * 13, imgSpike);

    
  }

  public void createPlatform(int x, int y, PImage image) {
    if(intPlatformX.size() < intObjectLimit){
      intPlatformX.add((float)x);
      intPlatformY.add(y);
    }

    if(player.fltPX <= 410 - 60 && boolLeft == true){
      moveIt(0);
    } else if (player.fltPX >= 550 - 60 && boolRight == true){
      moveIt(0);
    }

    image(image, x + fltXOffset, y);
  }

  public void moveIt(int burst){
    
    fltXOffset += player.fltPSpeed * player.intDirection * -1 / 40;
      for(int i = 0; i < intPlatformX.size(); i++){
        intPlatformX.set(i, intPlatformX.get(i) + (float)(player.fltPSpeed * player.intDirection * -1 / 40) + burst);
      }
  }

  public boolean isOnTop() {

    for(int i = 0; i < intPlatformX.size(); i++){
      if (player.fltPY + arrSprite[intFrame].height >= intPlatformY.get(i) + arrSprite[intFrame].height / 4 && player.fltPY + arrSprite[intFrame].height <= intPlatformY.get(i) + 28 + imgGrass.height && player.fltPX + arrSprite[intFrame].width - 30 >= intPlatformX.get(i) && player.fltPX - 10 <= intPlatformX.get(i) + 5) {
        while (player.fltPY + arrSprite[intFrame].height > intPlatformY.get(i) + arrSprite[intFrame].height / 4){
            player.fltPY -= 1;
        }

          player.strState = "ground";
          if(intFrame == 4){
            intFrame = 2;
          }
          return true;  
      
        }
        
    }

    player.strState = "air";
    return false;

  }

  public void animate() {

    checkAnim();

    if(strAnim == "run"){
      
      
      if(intFrame <= 1){
        intFrame = 15;
        timeSince = millis();
      } else if(intFrame == 15 && millis() >= timeSince + 100){
        intFrame++;
        timeSince = millis();
      }
      

      if (intFrame > 1 && intFrame != 15 && intFrame < 7 || intFrame > 14 && millis() >= timeSince + 100){
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

    } else if (strAnim == "idle"){

      intFrame = 0;
      

    } else if (strAnim == "jump"){

      if(intFrame < 2 || intFrame > 5){
        intFrame = 2;
        timeSince = millis();
      }
      
      if (millis() >= timeSince + 250 && intFrame < 4){
        intFrame++;
        timeSince = millis();
      }

    }

    if(player.intDirection == 1){
      player.imgPlayer = arrSprite[intFrame];
    } else {
      player.imgPlayer = arrFlippedSprite[intFrame];
    }
    

  }

  public void checkAnim() {
    if(player.strState == "air" && strAnim != "jump"){
      strAnim = "jump";
      timeSince = 0;
    } else if(player.strState == "ground") {
      if(boolRight == true || boolLeft == true && strAnim != "run"){
        strAnim = "run";
        //timeSince = 0;
      } else if(boolRight == false && boolLeft == false && strAnim != "idle"){
        strAnim = "idle";
        timeSince = 0;
      }
    }
  }
  
  public void keyPressed() {
    // change bools
    if (key == 'w' || keyCode == 32){
      boolUp = true;
    }
    if (key == 'a'){
      boolLeft = true;
      player.intDirection = -1;
    }
    if (key == 's'){
      boolDown = true;
    }
    if (key == 'd'){
      boolRight = true;
      player.intDirection = 1;
    }

    if (keyCode == 16){
      boolShift = true;
    }
  }

  public void keyReleased() {
    // change bools for movement
    if (key == 'w' || keyCode == 32){
      boolUp = false;
    }
    if (key == 'a'){
      boolLeft = false;
      if(player.fltPSpeed >= 2.5){
        player.fltPSpeed -= 2.5;
      }
    }
    if (key == 's'){
      boolDown = false;
    }
    if (key == 'd'){
      boolRight = false;
      if(player.fltPSpeed >= 2.5){
        player.fltPSpeed -= 2.5;
      }
    }

    if (keyCode == 16){
      boolShift = false;
    }
  }

}