import java.awt.image.BufferedImage;

// A spritesheet that can show sub-frames for varying lengths
public class Spritesheet {

    private int frameIndex = 0;
    private int subIndex = -1;
    private int[] frameCounts;
    private int width, height;
    private BufferedImage img;

    // initialize with an image and an array of frame durations
    public Spritesheet(BufferedImage img, int[] frameCounts) {
        this.img = img;
        this.frameCounts = frameCounts;
        width = img.getWidth() / frameCounts.length;  // calculate pixel width of each sub-frame
        height = img.getHeight();
    }

    // return the next frame from the sequence
    public BufferedImage next() {
        subIndex++;
        if (frameCounts[frameIndex] == subIndex) {
            subIndex = 0;
            frameIndex = (frameIndex + 1) % frameCounts.length;
        }
        return img.getSubimage(frameIndex * width, 0, width, height);  // return ensuing subimage
    }

    // return width of each frame in the spritesheet (px)
    public int getFrameWidth() {
        return width;
    }

    // return height of each frame in the spritesheet (px)
    public int getFrameHeight() {
        return height;
    }
}