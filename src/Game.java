import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

public class Game {
    private static CanvasWindow canvas;
    private static Background background;
    private List<Level> levels = new ArrayList<>();
    private Slingshot slingshot;
    private Coo coo;
    private Handler handler;
    private Level level;
    private PhysicEngine engine;
    private boolean isDragging;
    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    private boolean isWin = false;
    private boolean isLoss = false;

    private GraphicsText text;
    private double textX;
    private double textY;

    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
        handler = new Handler(canvas);
        this.createLevels();
        this.addBackground(canvas, "images/LongerBackground.png");
        level = new Level(DifLevel.first);
        this.drawLevel(level, canvas);
    }

    public void runGame(){
        canvas.animate(() -> {
            
            // if(isLoss(level)){
            //     text = new GraphicsText("You Lose!", textX, textY);
            //     canvas.add(text);
            //     return;
            // }

            // if(isWin(level)){
            //     text = new GraphicsText("You Win!", textX, textY);
            //     canvas.add(text);
            //     return;
            // }
            
            this.addBackground(canvas, "images/LongerBackground.png");
        });
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

    public boolean isWin(Level level){
        if(level.getKnights().isEmpty()){
            isWin = true;
        }
        return isWin;
    }

    private boolean isLoss(Level level){
        if(level.getCoos().isEmpty()){
            isLoss = true;
        }
        return isLoss;
    }
}
