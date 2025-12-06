import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Coo implements Entity {

    private CooType cooType;
    private double cooX;
    private double cooY;
    private double radius;
    private double mass;
    private double explosiveRadius = 0;
    private int splitCount = 0;
    private double angle = 0;

    private Ellipse coo;
    private GraphicsGroup fullCoo;
    private Image image;
    private Vector2D velocity;

    private boolean isDestroyed = false;
    private boolean isDragging = false;
    private boolean isLaunch = false;
    
    public Coo(CooType cooType, double cooX, double cooY, double radius){
        this.cooType = cooType;
        this.cooX = cooX;
        this.cooY = cooY;
        this.radius = radius;
        this.velocity = new Vector2D(0, 0);
        this.mass = cooType.getMass();

        fullCoo = new GraphicsGroup(cooX - radius, cooY - radius);

        coo = new Ellipse(0, 0, radius * 2, radius * 2);
        coo.setFillColor(Color.BLUE);
        fullCoo.add(coo);

        image = new Image("images/HighlandCowCropped.png");
        image.setPosition(0, 0);
        image.setMaxHeight(radius * 2);
        image.setMaxWidth(radius * 2);
        fullCoo.add(image);

        switch(cooType){
            case explosiveCoo:
                this.explosiveRadius = cooType.getSpecialValue();
                break;
            case splitterCoo:
                this.splitCount = (int)cooType.getSpecialValue();
                break;
            default:
                break;
        }
    }

    public GraphicsGroup getShape(){
        return fullCoo;
    }

    public Vector2D getVelocity(){
        return velocity;
    }

    public Vector2D getPosition(){
        return new Vector2D(cooX, cooY);
    }

    public double getAngle(){
        return angle;
    }

    public double getWidth(){
        return radius * 2;
    }

    public Rectangle getBounds(){
        return new Rectangle(cooX - radius, cooY - radius, radius * 2, radius * 2);
    }

    public CooType getCooType(){
        return cooType;
    }

    public double getExplosiveRadius(){
        return explosiveRadius;
    }

    public double getSplitCount(){
        return splitCount;
    }

    public double getRadius(){
        return radius;
    }

    public double getHp(){
        return 0;
    }

    public double getMass(){
        return mass;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public boolean isDragging(){
        return isDragging;
    }

    public boolean isLaunch(){
        return isLaunch;
    }

    public void setVelocity(Vector2D v){
        velocity = v.mul(cooType.getSpeed() / 50);
    }

    public void setPosition(Vector2D v){
        cooX = v.getX();
        cooY = v.getY();

        fullCoo.setPosition(cooX - radius, cooY - radius);
    }

    public void setDragging(boolean s){
        isDragging = s;
    }

    public void setLaunch(boolean s){
        isLaunch = s;
    }

    public void update(double dt){
        cooX += velocity.getX() * dt;
        cooY += velocity.getY() * dt;

        fullCoo.setPosition(cooX - radius, cooY - radius);
    }

    public void moveTo(double x, double y){
        cooX = x; 
        cooY = y;

        fullCoo.setPosition(cooX - radius, cooY - radius);
    }

    public void takeDamage(double dmg){
        if(isDestroyed){
            return;
        }
        isDestroyed = true;

        this.destroy(Game.canvas);
    }

    public void destroy(CanvasWindow canvas){
        try{
            canvas.remove(fullCoo);
        } catch(Exception ignored){}
    }

    public void applyImpulse(Vector2D impulse){
        Vector2D dv = impulse.mul(1 / Math.max(1e-6, mass));
        this.velocity = this.velocity.add(dv);
    }
}
