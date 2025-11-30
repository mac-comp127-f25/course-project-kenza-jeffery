import edu.macalester.graphics.CanvasWindow;
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

    private CanvasWindow canvas;

    private boolean isDestroyed = false;

    public Box(MaterialType materialType, double boxX, double boxY, double width, double height, CanvasWindow canvas){
        this.width = width;
        this.height = height;
        this.boxX = boxX;
        this.boxY = boxY;
        this.canvas = canvas;
        this.mass = materialType.getMass();
        hp = materialType.getHp();
        box = new Rectangle(boxX, boxY, width, height);
        this.velocity = new Vector2D(0, 0);

        canvas.add(box);
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
            canvas.remove(box);
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
}
