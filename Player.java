import processing.core.PApplet;
import processing.core.PImage;


public class Player {
    
    PImage imgPlayer;

    float fltGravity = 0;
    float fltPX = 480 - 60;
    float fltPY = 300;
    int intDirection = 1;
    double fltPSpeed = 2.5;
    
    String strState = "air";
    boolean canDash = true;
    float startTime;

    boolean boolDead = false;

    public void run(boolean boolUp, boolean boolDown, boolean boolLeft, boolean boolRight, boolean boolShift) {

        if(boolRight == true || boolLeft == true){
          if(boolRight == true && fltPX < 550 - 60){
            fltPX += fltPSpeed*intDirection;
          } else if (boolLeft == true && fltPX > 410  - 60){
            fltPX += fltPSpeed*intDirection;
          }

        
          if(fltPSpeed < 5){
            fltPSpeed += 0.25;
          }
        }
        
        if(boolLeft == false && boolRight == false){
          fltPSpeed = 2;
        }

        if(boolUp == true && strState == "ground"){
          fltPY -= 5;
          fltGravity = 11;
          strState = "air";
        }
    
        fltPY -= fltGravity;
    
        if(strState == "ground"){
          fltGravity = 0;
        } else {
          fltGravity -= 0.5;
        }
    
      }
    
}
