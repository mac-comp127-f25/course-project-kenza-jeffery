import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Level {
    private List<Box> boxes = new ArrayList<>();
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private GraphicsGroup entityGroup = new GraphicsGroup();
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
        return coos;
    }

    public List<Knight> getKnights(){
        return knights;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    private void setupFirstLevel(){
        Coo coo1 = new Coo(CooType.regularCoo, 45, 320, 20);
        Box box1 = new Box(MaterialType.WOOD, 1000, 680, 100, 20);
        Box box2 = new Box(MaterialType.STONE, 1000, 580, 20, 120);
        Box box3 = new Box(MaterialType.WOOD, 1000, 580, 100, 20);
        Box box4 = new Box(MaterialType.STONE, 1100, 580, 20, 120);

        Box box5 = new Box(MaterialType.WOOD, 950, 480, 20, 220);
        Box box6 = new Box(MaterialType.WOOD, 1150, 480, 20, 220);
        Box box7 = new Box(MaterialType.WOOD, 950, 480, 220, 20);

        Box box8 = new Box(MaterialType.WOOD, 1000, 250, 20, 230);
        Box box9 = new Box(MaterialType.WOOD, 1100, 250, 20, 230);
        Box box10 = new Box(MaterialType.WOOD, 1000, 235, 120, 20);
        Box box11 = new Box(MaterialType.WOOD, 1050, 205, 20, 30);

        Knight knight1 = new Knight(KnightType.basicKnight, 1030, 520, 30);
        
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
        
        knights.add(knight1);

        coos.add(coo1);
    }

    private void setupSecondLevel(){
        
    }

    private void setupThirdLevel(){
        
    }

    private void setupForthLevel(){
        
    }

    private void setupFifthLevel(){
        
    }

    public void addToEntityGroup(){
        for(Entity entity : entities){
            entityGroup.add(entity.getShape());
        }
    }

    public GraphicsGroup getEntityGroup(){
        return entityGroup;
    }
}
