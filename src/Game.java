import java.io.File;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class Game {
    private static CanvasWindow CANVASWINDOW;
    private static Background background;
    private Slingshot slingshot;
    private Coo currentCoo;
    private Level level;
    private Handler handler;
    private boolean isDragging;

    public static void main(String[] args) {
        CanvasWindow CANVASWINDOW = new CanvasWindow("Game", 0, 0);
        CANVASWINDOW.add(background.getBackground());
    }
}
