import java.awt.Color;
import edu.macalester.graphics.Rectangle;

public class Box implements Entity {

    private double hp;
    private double angle = 0;
    private Vector2D velocity;
    private double width;
    private double height;
    private double boxX;
    private double boxY;
    private double mass;
    private Rectangle box;

    private boolean isStatic = true;

    private boolean isDestroyed = false;

    public Box(MaterialType materialType, double boxX, double boxY, double width, double height){
        this.width = width;
        this.height = height;
        this.boxX = boxX;
        this.boxY = boxY;
        this.mass = materialType.getMass();
        hp = materialType.getHp();
        box = new Rectangle(boxX, boxY, width, height);
        if (materialType == MaterialType.WOOD) {
            box.setFillColor(new Color(121, 96, 37));
        } else if (materialType == MaterialType.STONE) {
            box.setFillColor(new Color(129, 128, 125));
        } else {
            box.setFillColor(new Color(161, 237, 255));
        }

        this.velocity = new Vector2D(0, 0);
    }

    public Rectangle getShape(){
        return box;
    }

    public double getX(){
        return boxX;
    }

    public double getY(){
        return boxY;
    }

    public double getWidth(){
        return width;
    }

    public double getHeight(){
        return height;
    }

    public void update(double dt){
        if(isDestroyed){
            return;
        }
        
        boxX += velocity.getX() * dt;
        boxY += velocity.getY() * dt;
        box.setPosition(boxX, boxY);
    }

    public double getAngle(){
        return angle;
    }

    public Rectangle getBounds(){
        return box;
    }

    public Vector2D getPosition(){
        return new Vector2D(boxX, boxY);
    }

    public void setPosition(Vector2D v){
        boxX = v.getX();
        boxY = v.getY();
        
        if(!isDestroyed){
            box.setPosition(boxX, boxY);
        }
    }
    
    public void setVelocity(Vector2D v){
        this.velocity = v;
    }

    public void takeDamage(double force){
        hp -= force;
        if(hp <= 0){
            isDestroyed = true;
        }
    }
    
    public Vector2D getVelocity(){
        return velocity;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public double getMass(){
        return mass;
    }

    public void setStatic(boolean s){
        isStatic = s;
    }

    public boolean isStatic(){
        return isStatic;
    }
}
