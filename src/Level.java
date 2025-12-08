import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;

public class Level {
    private List<Box> boxes = new ArrayList<>();
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private GraphicsGroup entityGroup = new GraphicsGroup();
    private final double waitingCooY = 680;
    private final double cooRadius = 20;
    DifLevel difLevel;

    public Level(DifLevel difLevel){
        this.difLevel = difLevel;
        this.setLevels();
    }

    public void setLevels(){
        boxes.clear();
        knights.clear();
        entities.clear();
        coos.clear();
        switch(difLevel){
            case first:
                this.setupFirstLevel();
                break;
            case second:
                this.setupSecondLevel();
                break;
            case third:
                this.setupThirdLevel();
                break;
            case forth:
                this.setupForthLevel();
                break;
            case fifth:
                this.setupFifthLevel();
                break;
        }

        entities.addAll(coos);
        entities.addAll(knights);
        entities.addAll(boxes);

        this.addToEntityGroup();
    }

    public List<Box> getBoxes(){
        return boxes;
    }

    public List<Coo> getCoos(){
        coos.sort((c1, c2) -> Double.compare(c1.getPosition().getX(), c2.getPosition().getX()));
        return coos;
    }

    public List<Knight> getKnights(){
        return knights;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    private void setupFirstLevel(){
        Coo coo1 = new Coo(CooType.regularCoo, 280, waitingCooY, cooRadius);
        Coo coo2 = new Coo(CooType.regularCoo, 340, waitingCooY, cooRadius);
        Coo coo3 = new Coo(CooType.regularCoo, 400, waitingCooY, cooRadius);
        Coo coo4 = new Coo(CooType.regularCoo, 460, waitingCooY, cooRadius);

        Box box1 = new Box(MaterialType.WOOD, 1000, 680, 120, 20);
        Box box2 = new Box(MaterialType.STONE, 1000, 580, 20, 100);
        Box box3 = new Box(MaterialType.WOOD, 1000, 560, 120, 20);
        Box box4 = new Box(MaterialType.STONE, 1100, 580, 20, 100);

        Box box5 = new Box(MaterialType.WOOD, 950, 480, 20, 205);
        Box box6 = new Box(MaterialType.WOOD, 1150, 480, 20, 205);
        Box box7 = new Box(MaterialType.WOOD, 950, 480, 220, 20);

        Box box8 = new Box(MaterialType.WOOD, 1000, 250, 20, 230);
        Box box9 = new Box(MaterialType.WOOD, 1100, 250, 20, 230);
        Box box10 = new Box(MaterialType.WOOD, 1000, 235, 120, 20);
        Box box11 = new Box(MaterialType.WOOD, 1050, 205, 20, 30);

        Knight knight1 = new Knight(KnightType.basicKnight, 1060, 660, 20);
        Knight knight2 = new Knight(KnightType.basicKnight, 1060, 185, 20);
        // Knight knight3 = new Knight(KnightType.basicKnight, 1060, 460, 20);
        
        boxes.add(box3);
        boxes.add(box1);
        boxes.add(box4);
        boxes.add(box2);
       
        boxes.add(box5);
        boxes.add(box6);
        boxes.add(box7);

        boxes.add(box8);
        boxes.add(box9);
        boxes.add(box10);
        boxes.add(box11);
        
        knights.add(knight1);
        knights.add(knight2);
        // knights.add(knight3);

        coos.add(coo1);
        coos.add(coo2);
        coos.add(coo3);
        coos.add(coo4);
    }

    private void setupSecondLevel(){
        Coo coo1 = new Coo(CooType.regularCoo, 280, waitingCooY, cooRadius);
        Coo coo2 = new Coo(CooType.regularCoo, 340, waitingCooY, cooRadius);
        Coo coo3 = new Coo(CooType.regularCoo, 400, waitingCooY, cooRadius);
        Coo coo4 = new Coo(CooType.regularCoo, 460, waitingCooY, cooRadius);

        Knight knight1 = new Knight(KnightType.basicKnight, 1080, 640, 20);

        Box box1 = new Box(MaterialType.WOOD, 970, 680, 50, 20);
        Box box2 = new Box(MaterialType.WOOD, 980, 640, 50, 20);
        Box box3 = new Box(MaterialType.WOOD, 990, 600, 50, 20);
        Box box4 = new Box(MaterialType.WOOD, 1000, 560, 50, 20);

        Box box5 = new Box(MaterialType.WOOD, 1140, 680, 50, 20);
        Box box6 = new Box(MaterialType.WOOD, 1130, 640, 50, 20);
        Box box7 = new Box(MaterialType.WOOD, 1120, 600, 50, 20);
        Box box8 = new Box(MaterialType.WOOD, 1110, 560, 50, 20);
        
        Box box9 = new Box(MaterialType.STONE, 1040, 540, 80, 20);

        Box box10 = new Box(MaterialType.ICE, 1050, 520, 20, 50);
        Box box11 = new Box(MaterialType.ICE, 1090, 520, 20, 50);

        Box box12 = new Box(MaterialType.WOOD, 1055, 480, 50, 20);

        Box box13 = new Box(MaterialType.WOOD, 1055, 660, 50, 20);
        Box box14 = new Box(MaterialType.STONE, 950, 500, 20, 200);
        Box box15 = new Box(MaterialType.STONE, 1190, 500, 20, 200);

        Box box16 = new Box(MaterialType.WOOD, 970, 600, 300, 20);
        
       
        

        // Box box5 = new Box(MaterialType.WOOD, 1015, 400, 100, 20);
        

        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);

        boxes.add(box5);
        boxes.add(box6);
        boxes.add(box7);
        boxes.add(box8);
        
        boxes.add(box9);
        boxes.add(box10);
        boxes.add(box11);
        boxes.add(box12);
        boxes.add(box13);
        boxes.add(box14);
        boxes.add(box15);
        boxes.add(box16);
       
        // boxes.add(box5);

        coos.add(coo1);
        coos.add(coo2);
        coos.add(coo3);
        coos.add(coo4);

        knights.add(knight1);
    }

    private void setupThirdLevel(){
        
    }

    private void setupForthLevel(){
        
    }

    private void setupFifthLevel(){
        
    }

    public void addToEntityGroup(){
        entityGroup.removeAll();
        for(Entity entity : entities){
            entityGroup.add(entity.getShape());
        }
    }

    public GraphicsGroup getEntityGroup(){
        return entityGroup;
    }

    public void removeEntity(Entity e){
        Game.canvas.remove(e.getShape());
    }
}
