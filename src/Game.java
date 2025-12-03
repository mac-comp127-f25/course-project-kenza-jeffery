import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class Game {
   private static CanvasWindow canvas;
    private static Background background;
    private List<Level> levels = new ArrayList<>();
    private Slingshot slingshot;
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
        this.addBackground(canvas, "images/LongerBackground.png");
    }

    public void addBackground(CanvasWindow canvas, String imagePath){
        background = new Background(imagePath, CANVAS_WIDTH, CANVAS_HEIGHT);
        background.setSize(1, 1);
        background.setPosition(-50, -200);
        Image grass = new Image("images/Grass.png");
        Image anotherGrass = new Image("images/Grass.png");
        canvas.add(grass);
        canvas.add(anotherGrass);
        anotherGrass.setPosition(900, 650);
        grass.setPosition(0, 650);
        canvas.add(background.getBackground());
        canvas.add(slingshot.getShape());
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
