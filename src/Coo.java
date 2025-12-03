import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Coo implements Entity {

    private CooType cooType;
    private double cooX;
    private double cooY;
    private double angle = 0;
    private double radius;
    private Vector2D velocity;
    private Ellipse coo;
    private double mass;
    private GraphicsGroup fullCoo;
    private Image image;
    private boolean isDestroyed = false;

    private double explosiveRadius = 0;
    private int splitCount = 0;
    
    public Coo(CooType cooType, double cooX, double cooY, double radius){
        this.cooType = cooType;
        this.cooX = cooX;
        this.cooY = cooY;
        this.radius = radius;
        this.velocity = new Vector2D(0, 0);
        this.mass = cooType.getMass();

        fullCoo = new GraphicsGroup(cooX, cooY);
        coo = new Ellipse(cooX, cooY, radius * 3, radius * 3);
        image = new Image("images/HighlandCowCropped.png");
        image.setPosition(cooX, cooY);
        image.setMaxHeight(radius * 3);
        image.setMaxWidth(radius * 3);
        fullCoo.add(coo);
        fullCoo.add(image);
        coo.setFillColor(Color.BLUE);

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

    public void setVelocity(Vector2D v){
        velocity = v.mul(cooType.getSpeed() / 50);
    }

    public void setPosition(Vector2D v){
        cooX = v.getX();
        cooY = v.getY();

        coo.setPosition(cooX, cooY);
    }

    public void update(double dt){
        cooX += velocity.getX() * dt;
        cooY += velocity.getY() * dt;

        coo.setPosition(cooX, cooY);
    }

    public Rectangle getBounds(){
        return new Rectangle(cooX, cooY, radius * 2, radius * 2);
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

    public double getMass(){
        return mass;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }
}
