import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Image;

public class Game {
   private static CanvasWindow canvas;
    private static Background background;
    private List<Level> levels = new ArrayList<>();
    private Slingshot slingshot;
    private Coo coo;
    private CooType cooType;
    private Handler handler;
    private PhysicEngine engine;
    private boolean isDragging;
    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    public static void main(String[] args) {
        Game game = new Game();
        game.runGame();
    }

    public Game(){
        coo = new Coo(CooType.regularCoo, 100, 100, 20);
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
    }

    public void runGame(){
        this.addBackground(canvas, "images/LongerBackground.png");
    }

    public void addBackground(CanvasWindow canvas, String imagePath){
        background = new Background(imagePath, CANVAS_WIDTH, CANVAS_HEIGHT);
        background.setSize(1, 1);
        background.setPosition(-120, 90);
        Image grass = new Image("images/Grass.png");
        Image anotherGrass = new Image("images/Grass.png");
        canvas.add(grass);
        canvas.add(anotherGrass);
        anotherGrass.setPosition(900, 90);
        grass.setPosition(0, 650);
        canvas.add(background.getBackground());
        canvas.add(slingshot.getShape());
        canvas.add(coo.getShape());
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
