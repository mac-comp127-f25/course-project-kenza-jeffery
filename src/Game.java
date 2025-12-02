import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;

public class Game {
   private static CanvasWindow canvas;
    private static Background background;
    private List<Level> levels = new ArrayList<>();
    private Handler handler;
    private PhysicEngine engine;
    private boolean isDragging;
    public static final int CANVAS_WIDTH = 940;
    public static final int CANVAS_HEIGHT = 700;

    public static void main(String[] args) {
        Game game = new Game();
        game.runGame();
    }

    public Game(){
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void runGame(){
        this.addBackground(canvas, "images/Gemini_Generated_Image_asm55qasm55qasm5.png");
    }

    public void addBackground(CanvasWindow canvas, String imagePath){
        background = new Background(imagePath, CANVAS_WIDTH, CANVAS_HEIGHT);
        background.setPosition(0, 0);
        background.setSize(1, 1);
        background = new Background("images/Gemini_Generated_Image_asm55qasm55qasm5.png", CANVAS_WIDTH, CANVAS_HEIGHT);
        background.setPosition(-50, -200);
        background.setSize(1, 1.1);
        canvas.add(background.getBackground());
        canvas.draw();
    }

    public void createLevels(){
        levels.add(new Level(DifLevel.first));
        levels.add(new Level(DifLevel.second));
        levels.add(new Level(DifLevel.third));
        levels.add(new Level(DifLevel.forth));
        levels.add(new Level(DifLevel.fifth));
    }

    public void drawLevel(Level level, CanvasWindow canvas){
        for(Entity entity : level.getEntities()){
            canvas.add(entity.getShape());
        }
    }
}
