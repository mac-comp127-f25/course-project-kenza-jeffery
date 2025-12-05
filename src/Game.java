import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Image;

import edu.macalester.graphics.ui.Button;


public class Game {
    private static CanvasWindow canvas;
    private static Background background;
    private Slingshot slingshot;
    private Level level;

    private Coo currentCoo;

    private Handler handler;
    private PhysicEngine engine = new PhysicEngine();

    public static final int CANVAS_WIDTH = 1280;
    public static final int CANVAS_HEIGHT = 720;

    private GraphicsGroup backgroundItems = new GraphicsGroup();

    private long lastMovingTime = System.currentTimeMillis();

    private GraphicsText text;
    private double textX = 630;
    private double textY = 350;

    private Button resetLevel = new Button("New Game");
    private Button Level1 = new Button("Level 1");
    private Button Level2 = new Button("Level 2");
    private Button Level3 = new Button("Level 3");
    private Button Level4 = new Button("Level 4");
    private Button Level5 = new Button("Level 5");

    private double levelButtonBeginX = 1150;
    private double levelButtonBeginY = 50;

    private int currentLevelIndex = 0;
    private double startCooX = 185;
    private double startCooY = 470;

    private List<DifLevel> levelConfigs = List.of(
        DifLevel.first,
        DifLevel.second,
        DifLevel.third,
        DifLevel.forth,
        DifLevel.fifth
    );

    public static void main(String[] args) {
        new Game();
    }

    public Game(){
        canvas = new CanvasWindow("Angry Coo!", CANVAS_WIDTH, CANVAS_HEIGHT);
        this.addBackground(canvas, "images/LongerBackground.png");
        handler = new Handler(canvas);
        this.addButtons(canvas);

        canvas.onMouseDown(e -> {handler.mousePressed(e);
                if (currentCoo != null && e.getPosition().getX() < 200 && !currentCoo.isLaunch()) {
                        currentCoo.setDragging(true);
                    }
                });

        canvas.onDrag(e -> {
            handler.mouseDragged(e);

            if (currentCoo != null && currentCoo.isDragging() && !currentCoo.isLaunch()) {
                double mouseX = e.getPosition().getX();
                double mouseY = e.getPosition().getY();

                currentCoo.setPosition(new Vector2D(mouseX - currentCoo.getRadius(), mouseY - currentCoo.getRadius()));
                currentCoo.setVelocity(new Vector2D(0, 0));
            }
        });

        canvas.onMouseUp(e -> {
            handler.mouseReleased(e);

            if (currentCoo != null && currentCoo.isDragging() && !currentCoo.isLaunch()) {
                currentCoo.setDragging(false);
                currentCoo.setLaunch(true);
                currentCoo.setVelocity(handler.getVector());
                engine.addCoo(currentCoo);
            }
        });

    
        resetLevel.onClick(() -> {
            this.loadLevel(currentLevelIndex);
        });

        Level1.onClick(() -> {
            this.loadLevel(0);
        });

        Level2.onClick(() -> {
            this.loadLevel(1);
        });

        Level3.onClick(() -> {
            this.loadLevel(2);
        });

        Level4.onClick(() -> {
            this.loadLevel(3);
        });

        Level5.onClick(() -> {
            this.loadLevel(4);
        });

        this.runGame();
    }

    public void runGame(){
        canvas.animate(() -> {

            if(level == null){
                return;
            }

            engine.update();
            this.checkCooStop();

            if(level.getKnights().isEmpty()){
                if(text == null){
                    text = new GraphicsText("You Win!", textX, textY);
                    canvas.add(text);
                }
                return;
            } else if(level.getCoos().isEmpty()){
                if(text == null){
                    text = new GraphicsText("You Lose!", textX, textY);
                    canvas.add(text);
                }
                return;
            }
        });
    }

    public void addBackground(CanvasWindow canvas, String imagePath){
        background = new Background(imagePath, CANVAS_WIDTH, CANVAS_HEIGHT);
        slingshot = new Slingshot(new Color(97,70,35), Color.BLACK);
        background.setSize(1, 1);
        background.setPosition(20, 100);
        Image grass = new Image("images/Grass.png");
        Image anotherGrass = new Image("images/Grass.png");
        backgroundItems.add(grass);
        backgroundItems.add(anotherGrass);
        anotherGrass.setPosition(400, 640);
        grass.setPosition(-50, 640);
        backgroundItems.add(background.getBackground());
        backgroundItems.add(slingshot.getShape());
        canvas.add(backgroundItems);
        canvas.draw();
    }

    public void drawLevel(Level level, CanvasWindow canvas){
        for (Entity entity : level.getEntities()){
            canvas.add(entity.getShape());
        }
    }

    private void addButtons(CanvasWindow canvas){
        resetLevel.setPosition(50, 50);
        canvas.add(resetLevel);
        Level1.setPosition(levelButtonBeginX, levelButtonBeginY);
        Level2.setPosition(levelButtonBeginX, levelButtonBeginY + 50);
        Level3.setPosition(levelButtonBeginX, levelButtonBeginY + 100);
        Level4.setPosition(levelButtonBeginX, levelButtonBeginY + 150);
        Level5.setPosition(levelButtonBeginX, levelButtonBeginY + 200);
        canvas.add(Level1);
        canvas.add(Level2);
        canvas.add(Level3);
        canvas.add(Level4);
        canvas.add(Level5);
    }

    private void loadLevel(int index){
        currentLevelIndex = index;

        if (level != null) {
            canvas.remove(level.getEntityGroup());
        }

        level = new Level(levelConfigs.get(index));

        canvas.add(level.getEntityGroup());

        engine.setBoxes(level.getBoxes());
        engine.setKnights(level.getKnights());
        engine.setCoos(new ArrayList<>());

        List<Coo> coos = level.getCoos();
        if (!coos.isEmpty()) {
            currentCoo = coos.get(0);
            currentCoo.setPosition(new Vector2D(startCooX, startCooY));
            currentCoo.setDragging(false);
            currentCoo.setLaunch(false);
            currentCoo.setVelocity(new Vector2D(0,0));
        } else {
            currentCoo = null;
        }

        if (text != null) {
            canvas.remove(text);
            text = null;
        }

        lastMovingTime = System.currentTimeMillis();
    }


    private void checkCooStop(){
        if(currentCoo == null){
            return;
        }
        if(!currentCoo.isLaunch()){
            return;
        }

        Vector2D v = currentCoo.getVelocity();

        if(Math.abs(v.getX()) < 2 && Math.abs(v.getY()) < 2){
            long now = System.currentTimeMillis();
            if (now - lastMovingTime > 1000) {  
                switchToNextCoo();
            }
        }else {
            lastMovingTime = System.currentTimeMillis();
        }
    }

    private void switchToNextCoo() {
        if (currentCoo != null) {
            engine.removeCoo(currentCoo);
            level.getCoos().remove(currentCoo);
        }

        if (!level.getCoos().isEmpty()) {
            currentCoo = level.getCoos().get(0);
            currentCoo.setPosition(new Vector2D(startCooX, startCooY));
            currentCoo.setDragging(false);
            currentCoo.setLaunch(false);
            currentCoo.setVelocity(new Vector2D(0, 0));
        } else {
            currentCoo = null;
        }

        lastMovingTime = System.currentTimeMillis();
    }
}
