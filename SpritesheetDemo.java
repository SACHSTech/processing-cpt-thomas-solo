import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Shows JFrame window where spritesheet animation plays at 30FPS
public class SpritesheetDemo extends JFrame {

    private static final int DELAY = 33;  // number of ms delay between frames
    private Spritesheet spritesheet;     // spritesheet to display
    private int windowWidth, windowHeight;

    public SpritesheetDemo(Spritesheet spritesheet) {
        this.spritesheet = spritesheet;
        windowWidth = spritesheet.getFrameWidth();
        windowHeight = spritesheet.getFrameHeight() + 30;
        // set window size to spritesheet frame size
        setSize(new Dimension(windowWidth, windowHeight));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Spritesheet Display");
        setVisible(true);

        // set up an ActionListener to invalidate and redraw the window every DELAY milliseconds
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();  // repaint the screen
            }
        };
        new Timer(DELAY, taskPerformer).start();
    }

    // Clear the window and draw the spritesheet's next frame
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);  // set color to white
        g.fillRect(0, 0, windowWidth, windowHeight); // reset the window by drawing over it with white
        ((Graphics2D) g).drawImage(spritesheet.next(), null, 0, 30);
    }
}