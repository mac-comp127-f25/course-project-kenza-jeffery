import java.awt.Color;
import edu.macalester.graphics.Rectangle;

public class Box implements Entity {

    private double width;
    private double height;
    private double boxX;
    private double boxY;
    private double mass;
    private double hp;
    private double angle = 0;

    private Rectangle box;
    private Vector2D velocity;

    private boolean isStatic = true;
    private boolean isDestroyed = false;

    public Box(MaterialType materialType, double boxX, double boxY, double width, double height){
        this.width = width;
        this.height = height;
        this.boxX = boxX;
        this.boxY = boxY;
        this.mass = materialType.getMass();
        this.hp = materialType.getHp();
        
        box = new Rectangle(boxX, boxY, width, height);

        if (materialType == MaterialType.WOOD) {
            box.setFillColor(new Color(121, 96, 37));
        } else if (materialType == MaterialType.STONE) {
            box.setFillColor(new Color(129, 128, 125));
        } else {
            box.setFillColor(new Color(161, 237, 255));
        }
        
        velocity = new Vector2D(0, 0);
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

    public double getAngle(){
        return angle;
    }

    public double getHp(){
        return hp;
    }

    public double getRadius(){
        return Math.max(width, height) / 2;
    }

    public double getMass(){
        return mass;
    }

    public Rectangle getShape(){
        return box;
    }

    public Rectangle getBounds(){
        return box;
    }

    public Vector2D getVelocity(){
        return velocity;
    }

    public Vector2D getPosition(){
        return new Vector2D(
            boxX + width / 2,
            boxY + height / 2
        );
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public boolean isStatic(){
        return isStatic;
    }

    public void setPosition(Vector2D v){
        boxX = v.getX() - width / 2;
        boxY = v.getY() - height / 2;
        
        if(!isDestroyed){
            box.setPosition(boxX, boxY);
        }
    }
    
    public void setVelocity(Vector2D v){
        this.velocity = v;
    }

    public void setStatic(boolean s){
        isStatic = s;
    }

    public void update(double dt){
        if(isDestroyed || isStatic){
            return;
        }
        
        boxX += velocity.getX() * dt;
        boxY += velocity.getY() * dt;
        box.setPosition(boxX, boxY);
    }

    public void takeDamage(double dmg){
        hp -= dmg;
        if(hp <= 0){
            isDestroyed = true;
        }
    }
}
