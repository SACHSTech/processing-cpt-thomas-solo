import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PImage;

public class Sketch extends PApplet {
	
  // creation of all PImage stage items
  PImage imgGrass;
  PImage imgDirt;
  PImage imgPlat;
  PImage imgSpike;
  PImage imgGoal;

  // creation of player sprite arrays, timeSince which is used for timing animations, and intFrame which is used to select a frame from the sprite arrays
  PImage[] arrSprite = new PImage[17];
  PImage[] arrFlippedSprite = new PImage[arrSprite.length];
  float timeSince = 0;
  int intFrame = 0;

  // strAnim is used in deciding which sprite to show
  String strAnim = "idle";

  // these three arrays are used to place the coords of each block and what image each block holds
  ArrayList<Integer> intPlatformY = new ArrayList<Integer>();
  ArrayList<Float> intPlatformX = new ArrayList<Float>();
  ArrayList<PImage> imgPlatformType = new ArrayList<PImage>();

  // intObjectLimit is used so that the program stops adding additional items to the ArrayLists once all the necessary stage blocks have been placed
  int intObjectLimit = 75;

  // intMoveStage is a technically unnecessary constant that made it easier for me to shift from the player moving on the screen to the screen moving along with the player, it just shifts everything over
  int intMoveStage = -330;
  
  // intLevel is either 0, 1, 2, or 3, and is used for deciding what level to show
  int intLevel = 0;
  float fltGoalX;
  float fltGoalY;

  // booleans used for movement
  boolean boolUp = false;
  boolean boolDown = false;
  boolean boolLeft = false;
  boolean boolRight = false;

  // player class is used to segment off moving and gravity
  Player player = new Player();

  // fltXOffset moves the stage
  float fltXOffset = 0;

  // while intLives > 0, if you fall into the void you bounce up, otherwise if intLives <= 0, the game restarts
  int intLives = 3;
  
  public void settings() {
    size(960, 540);
  }

  public void setup() {
    background(102, 204, 255);
    loadImages();
    loadSprites();
  }

  /**
   * Loads images, such as giving imgPlayer a default idle image, and giving each platform block their image type.
   */
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

    imgGoal = loadImage("Goal.png");
    imgGoal.resize(imgGoal.width*2, imgGoal.height*2);

  }

  /**
   * Gives the sprite arrays their images, then resizes all of them.
   */
  public void loadSprites() {
    // arrFlippedSprite is used when the player faces left
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

    // arrSprite is used when the player faces right
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

    // if the game is over, stop running everything
    if(intLevel != 3){
      collision();
      player.run(boolUp, boolDown, boolLeft, boolRight);
      animate();
  
      level();

      // player is drawn last to go on top of everything else
      image(player.imgPlayer, player.fltPX, player.fltPY);
    } else {
      rect(0, 0, width, height);
    }
  }

  /**
   * Checks if the player is touching the goal, if they are touching the x-border at which movement switches from player movement to screen movement, and if the player is offscreen, while calling upon drawHealth and isOnTop.
   */
  public void collision() {
    
    // if the player is touching the goal, next level
    if(player.fltPY + arrSprite[intFrame].height >= fltGoalY + imgGoal.height / 4  && player.fltPY + arrSprite[intFrame].height <= fltGoalY + 70 + imgGoal.height && player.fltPX + arrSprite[intFrame].width - 20 >= fltGoalX && player.fltPX + 50 <= fltGoalX + imgGoal.width){
      intLevel++;
    }
    
    // if the player reaches the set x-borders, start moving the screen
    if(player.fltPX <= 410 - 60 && boolLeft == true){
      moveIt();
    } else if (player.fltPX >= 550 - 60 && boolRight == true){
      moveIt();
    }

    // if the player is under the screen, bounce them up and take away a life
    if(player.fltPY > height){
      intLives -= 1;
      player.fltPY -= 50;
      player.fltGravity = 15;
      timeSince = millis();
    }

    drawHealth();

    isOnTop();
    
  }

  /**
   * Draws the player health bar, and restarts the game to the first level if the player loses all three lives.
   */
  public void drawHealth() {
    
    strokeWeight(2);
    fill(255, 42, 0);

    // draw a health square for each life
    for(int i = 0; i < intLives; i++){
      rect(0 + 25 + i * 75, 0 + 25, 75, 75);
    }

    if(intLives <= 0){
      restart();
    }
  }

  /**
   * Checks if the player is touching any of the platform blocks. If they are, and the player is clipped into the floor, it pushes them out. 
   * It also updates strState which is used for animate().
   * @return true if the player is touching a platform, and false otherwise
   */
  public boolean isOnTop() {

    // check every block
    for(int i = 0; i < intPlatformX.size(); i++){
      // if the player and block overlap at any point, this is true
      if (player.fltPY + arrSprite[intFrame].height >= intPlatformY.get(i) + arrSprite[intFrame].height / 4 && player.fltPY + arrSprite[intFrame].height <= intPlatformY.get(i) + 28 + imgGrass.height && player.fltPX + arrSprite[intFrame].width - 30 >= intPlatformX.get(i) && player.fltPX - 10 <= intPlatformX.get(i) + 5 && imgPlatformType.get(i) != imgDirt) {
        // push the player out until fltPY + arrSprite.height is no longer in the floor
        while (player.fltPY + arrSprite[intFrame].height > intPlatformY.get(i) + arrSprite[intFrame].height / 4){
          player.fltPY -= 1;
        }

        // if they are on the ground, set strState to ground
        player.strState = "ground";
        // this if statement exists to prevent the jump animation from getting stuck if the jump button is held
        if(intFrame == 4){
          intFrame = 0;
        }
        return true;  
      
        }
        
    }

    // if they are in the air, set strState to air
    player.strState = "air";
    return false;

  }

  /** 
   * Draws the current level.
   * Does this through giving values to createPlatform().
   */
  public void level() {
    
    // if it is the first level
    if(intLevel == 0){

      // a relic of the original state of the game in which the screen was static, now present because harmless, and it would take a number of changes to adjust every block without it
      intMoveStage = -330;

      // first chunk
      for(int x = imgGrass.width * 2 * -1 + 4; x < -10; x += imgGrass.width - 2){
        
        createPlatform(x - intMoveStage, height - imgGrass.height * 12, imgGrass);

      }
      

      // second chunk
      for(int x = 0; x < imgGrass.width * 4; x += imgGrass.width - 2){
        
        createPlatform(x - intMoveStage, height - imgGrass.height * 3, imgGrass);

      }
      for(int y = height - imgDirt.height * 2 - 2; y < height; y += imgDirt.height - 2){
        for(int x = 0; x < imgGrass.width * 4; x += imgDirt.width - 2){
          
          createPlatform(x - intMoveStage, y, imgDirt);

        }
      }

      // third chunk
      for(int x = imgGrass.width * 7; x < imgGrass.width * 10; x += imgGrass.width - 2){
        
        createPlatform(x - intMoveStage, height - imgGrass.height * 4, imgGrass);

      }
      for(int y = height - imgDirt.height * 3 - 2; y < height; y += imgDirt.height - 2){
        for(int x = imgGrass.width * 7; x < imgGrass.width * 10; x += imgDirt.width - 2){
          
          createPlatform(x - intMoveStage, y, imgDirt);

        }
      }

      // fourth chunk
      for(int x = imgPlat.width * 13; x < width; x += imgPlat.width){
        createPlatform(x - intMoveStage, height - imgPlat.height * 8, imgPlat);
      }

      // fifth chunk
      for(int x = 0; x < imgPlat.width * 11; x += imgPlat.width){
        createPlatform(x - intMoveStage, height - imgPlat.height * 12, imgPlat);
      }

      // goal
      fltGoalX = imgPlat.width * 4 + fltXOffset;
      fltGoalY = 100;
      image(imgGoal, fltGoalX, fltGoalY);

      // if it is the second level
    } else if(intLevel == 1) {
      // ran once upon the level being set to second, removes all previous platforms, resets fltXOffset, and changes the object limit
      if(intObjectLimit == 75){
        for(int i = intPlatformX.size() - 1; i > 0; i--){
          intPlatformX.remove(i);
          intPlatformY.remove(i);
          imgPlatformType.remove(i);
        }

        fltXOffset = 0;
        player.fltPX = 420;
        player.fltPY = 300;

        intObjectLimit = 50;
      }

      // platform chunk 1
      for(int x = imgGrass.width; x < imgGrass.width * 3; x += imgGrass.width - 2){
        
        createPlatform(x - intMoveStage, height - imgGrass.height * 3, imgGrass);

      }
      for(int y = height - imgDirt.height * 2 - 2; y < height; y += imgDirt.height - 2){
        for(int x = imgGrass.width; x < imgGrass.width * 3; x += imgDirt.width - 2){
          
          createPlatform(x - intMoveStage, y, imgDirt);

        }
      }

      // platform chunk 2
      for(int x = imgGrass.width * 6; x < imgGrass.width * 7; x += imgGrass.width - 2){
        
        createPlatform(x - intMoveStage, height - imgGrass.height * 6, imgGrass);

      }
      for(int y = height - imgDirt.height * 5 - 2; y < height; y += imgDirt.height - 2){
        for(int x = imgGrass.width * 6; x < imgGrass.width * 7; x += imgDirt.width - 2){
          
          createPlatform(x - intMoveStage, y, imgDirt);

        }
      }

      // platform chunk 3
      createPlatform(imgGrass.width * 12 - intMoveStage, height - imgGrass.height * 6, imgGrass);

      
      for(int y = height - imgDirt.height * 5 - 2; y < height; y += imgDirt.height - 2){
        
        createPlatform(imgGrass.width * 12 - intMoveStage, y, imgDirt);

      }

      // platform chunk 4
      for(int x = imgPlat.width * 16; x < imgPlat.width * 20; x += imgPlat.width){
        createPlatform(x - intMoveStage, height - imgPlat.height * 6, imgPlat);
      }

      // platform chunk 5
      createPlatform(imgGrass.width * 23 - intMoveStage, height - imgGrass.height * 6, imgGrass);

      
      for(int y = height - imgDirt.height * 5 - 2; y < height; y += imgDirt.height - 2){
        
        createPlatform(imgGrass.width * 23 - intMoveStage, y, imgDirt);

      }

      // goal
      fltGoalX = imgPlat.width * 23 - intMoveStage + fltXOffset + 17;
      fltGoalY = 300;
      image(imgGoal, fltGoalX, fltGoalY);

    }
  
  }

  /**
   * Creates an x, y, and image value at the same point in different arrays.
   * 
   * @param x x-value of given block, added to the array intPlatformX
   * @param y y-value of given block, added to the array intPlatformY
   * @param image image of given block, added to the array imgPlatformType
   */
  public void createPlatform(int x, int y, PImage image) {
    // while the object limit hasn't been met, add more blocks
    // also !player.boolDead to avoid any jank
    if(intPlatformX.size() < intObjectLimit && !player.boolDead){
      intPlatformX.add((float)x);
      intPlatformY.add(y);
      imgPlatformType.add(image);
      
    }

    // draw out the image, modified by the fltXOffset
    image(image, x + fltXOffset, y);
  }

  /**
   * Ran to move the screen.
   */
  public void moveIt(){
    
    // as long as the player is alive, run this code
    if(!player.boolDead){
        fltXOffset += player.fltPSpeed * player.intDirection * -1;
      // move every block
      for(int i = 0; i < intPlatformX.size(); i++){
        intPlatformX.set(i, intPlatformX.get(i) + (float)(player.fltPSpeed * player.intDirection * -1));
      }
    }

  }

  /**
   * Ran to animate the player and choose which sprite is displayed by changing intFrame and then calling upon the intFrame of either arrSprite or arrFlippedSprite.
   */
  public void animate() {

    // ran to get the current strAnim
    checkAnim();

    if(strAnim == "run"){
      
      // if strAnim == run and intFrame is Idle1 or Idle2, start running
      if(intFrame <= 1){
        intFrame = 15;
        timeSince = millis();
        // shift to second run start frame
      } else if(intFrame == 15 && millis() >= timeSince + 100){
        intFrame++;
        timeSince = millis();
      }
      
      if (intFrame > 1 && intFrame != 15 && intFrame < 7 || intFrame > 14 && millis() >= timeSince + 100){
        intFrame = 7;
        timeSince = millis();
      } 

      // cycle through run loop
      if(intFrame >= 7 && intFrame <= 14 && millis() >= timeSince + 100){
        intFrame++;
        timeSince = millis();
      }

      // go back to first frame of run loop
      if(intFrame == 14){
        intFrame = 7;
      }

      // only one possible sprite is strAnim == idle
    } else if (strAnim == "idle"){

      intFrame = 0;
      
      // if strAnim == jump, then slowly go through the three jump sprites
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

    // choose either flipped or standard array depending on intDirection
    if(player.intDirection == 1){
      player.imgPlayer = arrSprite[intFrame];
    } else {
      player.imgPlayer = arrFlippedSprite[intFrame];
    }
    

  }

  /**
   * Checks what the current state of the player is and changes strAnim to reflect that.
   * The different possible states are "jump", "ground", "idle", and "run"
   */
  public void checkAnim() {
    // while in the air the only possible animation is jump
    if(player.strState == "air" && strAnim != "jump"){
      strAnim = "jump";
      timeSince = 0;
    } else if(player.strState == "ground") {
      // if ground input, then run
      if(boolRight == true || boolLeft == true && strAnim != "run"){
        strAnim = "run";
      // if no ground input, do nothing
      } else if(boolRight == false && boolLeft == false && strAnim != "idle"){
        strAnim = "idle";
        timeSince = 0;
      }
    }
  }
  
  public void keyPressed() {
    // change bools for movement
    if (key == 'w' || keyCode == 32){
      boolUp = true;
    }
    if (key == 'a'){
      boolLeft = true;
      // if player goes left, make direction negative
      player.intDirection = -1;
    }
    if (key == 's'){
      boolDown = true;
    }
    if (key == 'd'){
      boolRight = true;
      // if player goes right, make direction positive
      player.intDirection = 1;
    }
  }

  public void keyReleased() {
    // change bools for movement
    if (key == 'w' || keyCode == 32){
      boolUp = false;
    }
    if (key == 'a'){
      boolLeft = false;
      // slow down the player when they release a key
      if(player.fltPSpeed >= 2.5){
        player.fltPSpeed -= 2.5;
      }
    }
    if (key == 's'){
      boolDown = false;
    }
    if (key == 'd'){
      boolRight = false;
      // slow down the player when they release a key
      if(player.fltPSpeed >= 2.5){
        player.fltPSpeed -= 2.5;
      }
    }
  }

  /**
   * Resets the game back to the default values found in level one.
   */
  public void restart() {
    intObjectLimit = 75;
    intMoveStage = -330;
  
    intLevel = 0;
    fltGoalX = imgPlat.width * 4 + fltXOffset;
    fltGoalY = 100;

    boolUp = false;
    boolDown = false;
    boolLeft = false;
    boolRight = false;
  
    timeSince = 0;
    intFrame = 0;
    
    fltXOffset = 0;

    intLives = 3;

    for(int i = intPlatformX.size() - 1; i > 0; i--){
      intPlatformX.remove(i);
      intPlatformY.remove(i);
      imgPlatformType.remove(i);
    }
  }

}