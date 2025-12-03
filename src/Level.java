import java.util.ArrayList;
import java.util.List;

import edu.macalester.graphics.GraphicsGroup;

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
        Coo coo1 = new Coo(CooType.regularCoo, 200, 100, 20);
        Box box1 = new Box(MaterialType.STONE, 1000, 600, 100, 20);
        Box box2 = new Box(MaterialType.ICE, 1000, 500, 20, 100);
        Box box3 = new Box(MaterialType.ICE, 1000, 500, 100, 20);
        Box box4 = new Box(MaterialType.WOOD, 1100, 500, 20, 120);
        Knight knight1 = new Knight(KnightType.basicKnight, 1030, 530, 30);
        
        boxes.add(box1);
        boxes.add(box2);
        boxes.add(box3);
        boxes.add(box4);
        
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
