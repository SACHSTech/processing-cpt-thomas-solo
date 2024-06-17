import processing.core.PApplet;
import processing.core.PImage;


public class Player {
    
    // image of player, given value in main
    PImage imgPlayer;

    // all basic values
    float fltGravity = 0;
    float fltPX = 420;
    float fltPY = 300;
    int intDirection = 1;
    double fltPSpeed = 2.5;
    
    // used for animations
    String strState = "air";
    float startTime;

    // used for moving the screen and not player
    boolean canMove = true;

    /**
     * Runs through all code relating to the movement of the player as well as gravity and falling.
     * 
     * @param boolUp up-input
     * @param boolDown down-input
     * @param boolLeft left-input
     * @param boolRight right-input
     */
    public void run(boolean boolUp, boolean boolDown, boolean boolLeft, boolean boolRight) {

      // given the player can move and is within movement bounds, move
      if(boolRight == true || boolLeft == true){
        if(boolRight == true && fltPX < 550 - 60){
          fltPX += fltPSpeed*intDirection;
        } else if (boolLeft == true && fltPX > 410  - 60){
          fltPX += fltPSpeed*intDirection;
        }
      }
        // gives player momentum
        if(fltPSpeed < 5){
          fltPSpeed += 0.25;
        }
        if(fltPSpeed > 5){
          fltPSpeed = 5;
        }
        
        // if no input, go to slower speed
        if(boolLeft == false && boolRight == false){
          fltPSpeed = 2;
        }

        // if on ground and up-input, jump
        if(boolUp == true && strState == "ground"){
          fltPY -= 5;
          fltGravity = 11;
          strState = "air";
        }
    
        // always be pulled down by gravity
        fltPY -= fltGravity;
    
        // if on ground, gravity = 0
        if(strState == "ground"){
          fltGravity = 0;
        } else {
          fltGravity -= 0.5;
        }
    
      }
    
}
