import java.io.File;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

import java.awt.Color;

public class Game {
   // private static CanvasWindow canvas = new CanvasWindow("Game", 1000, 800);;
   private static CanvasWindow canvas;
    private static Background background;
    private static Slingshot slingshot = new Slingshot(new Color(97,70,35), Color.BLACK);
    private Coo currentCoo;
    private Level level;
    private Handler handler;
    private boolean isDragging;
    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    public static void main(String[] args) {
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
        background = new Background("images/LongerBackground.png", CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.add(background.getBackground());
        canvas.add(slingshot.getShape());
        canvas.draw();
    }
}
