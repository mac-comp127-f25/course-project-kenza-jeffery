import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private List<Box> boxes = new ArrayList<>();
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private Slingshot slingshot = new Slingshot(new Color(97,70,35), Color.BLACK);
    DifLevel difLevel;

    public Level(DifLevel difLevel){
        this.difLevel = difLevel;
        this.setLevels();
    }

    public void setLevels(){
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

        entities.addAll(boxes);
        entities.addAll(knights);
        entities.addAll(boxes);
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

    public Slingshot getSlingshot(){
        return slingshot;
    }

    private void setupFirstLevel(){

    }

    private void setupSecondLevel(){
        
    }

    private void setupThirdLevel(){
        
    }

    private void setupForthLevel(){
        
    }

    private void setupFifthLevel(){
        
    }
}
