import processing.core.PApplet;
import processing.core.PImage;


public class Player {
    
    PImage imgPlayer;

    float fltGravity = 0;
    float fltPX = 100;
    float fltPY = 300;
    
    String strState = "air";
    boolean canDash = true;
    float startTime;

    public void run(boolean boolUp, boolean boolDown, boolean boolLeft, boolean boolRight, boolean boolShift) {

        /*
        if(boolShift == true){
            dash();
        }
        */

        if(boolRight == true){
          fltPX += 5;
        }
        if(boolLeft == true){
          fltPX -= 5;
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
