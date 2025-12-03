import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

import java.awt.event.MouseEvent;
import edu.macalester.graphics.ui.Button;


public class Game {
    private static CanvasWindow canvas;
    private static Background background;
    private List<Level> levels = new ArrayList<>();
    private Slingshot slingshot;
    private Coo coo;
    private Handler handler;
    private Level level;
    private PhysicEngine engine;

    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    private boolean isWin = false;
    private boolean isLoss = false;
    private MouseEvent event;

    private boolean isDragging = false;


    private GraphicsText text;
    private double textX;
    private double textY;

    private Button nextGame = new Button("Next Game");
    private Button resetLevel = new Button("New Game");
    private Button Level1 = new Button("Level 1");
    private Button Level2 = new Button("Level 2");
    private Button Level3 = new Button("Level 3");
    private Button Level4 = new Button("Level 4");
    private Button Level5 = new Button("Level 5");

    private double levelButtonBeginX;
    private double levelButtonBeginY;

    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
        handler = new Handler(canvas);
        //this.createLevels();
        this.addBackground(canvas, "images/LongerBackground.png");
        level = new Level(DifLevel.first);
        this.drawLevel(level, canvas);
        this.runGame();

    }

    public void runGame(){
        canvas.animate(() -> {
            
            if(isLoss(level)){
                text = new GraphicsText("You Lose!", textX, textY);
                canvas.add(text);
                return;
            }

            if(isWin(level)){
                text = new GraphicsText("You Win!", textX, textY);
                canvas.add(text);
                return;
            }
            
            this.addBackground(canvas, "images/LongerBackground.png");
        });
    }

    public void addBackground(CanvasWindow canvas, String imagePath){
        background = new Background(imagePath, CANVAS_WIDTH, CANVAS_HEIGHT);
        slingshot = new Slingshot(new Color(97,70,35), Color.BLACK);
        background.setSize(1, 1);
        background.setPosition(20, 100);
        Image grass = new Image("images/Grass.png");
        Image anotherGrass = new Image("images/Grass.png");
        canvas.add(grass);
        canvas.add(anotherGrass);
        anotherGrass.setPosition(400, 640);
        grass.setPosition(0, 640);
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
