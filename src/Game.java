import java.io.File;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

import java.awt.Color;

public class Game {
    private static CanvasWindow canvas = new CanvasWindow("Game", 1000, 800);;
    private static Background background;
    private static Slingshot slingshot = new Slingshot(Color.BLACK);
    private Coo currentCoo;
    private Level level;
    private Handler handler;
    private boolean isDragging;

    public static void main(String[] args) {
        canvas.add(background.getBackground());
        canvas.add(slingshot.getShape());
        canvas.draw();
    }
}
