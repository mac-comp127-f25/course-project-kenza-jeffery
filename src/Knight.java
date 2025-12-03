import java.awt.Color;

import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Knight implements Entity {

    private KnightType knightType;
    private double knightX;
    private double knightY;
    private double radius;
    private Ellipse knight;
    private double hp;
    private double angle = 0;
    private Vector2D velocity;
    private double mass;
    private GraphicsGroup fullKnight;
    private Image image;

    private boolean isDestroyed = false;

    private double explosiveRadius = 0;

    public Knight(KnightType knightType, double knightX, double knightY, double radius){
        this.knightType = knightType;
        this.knightX = knightX;
        this.knightY = knightY;
        this.radius = radius;
        this.mass = knightType.getMass();
        this.hp = knightType.getHp();

        fullKnight = new GraphicsGroup(knightX, knightY);
        knight = new Ellipse(knightX, knightY, radius * 3, radius * 3);
        image = new Image("images/KnightCropped.png");
        image.setPosition(knightX, knightY);
        image.setMaxHeight(radius * 3);
        image.setMaxWidth(radius * 3);
        knight.setFillColor(new Color(232, 155, 229));
        fullKnight.add(knight);
        fullKnight.add(image);

        velocity = new Vector2D(0, 0);

        switch(knightType){
            case explosiveKnight:
                explosiveRadius = knightType.getSpecialValue();
                break;
            default:
                break;
        }
    }

    public Vector2D getPosition(){
        return new Vector2D(knightX, knightY);
    }

    public void setPosition(Vector2D v){
        knightX = v.getX();
        knightY = v.getY();

        knight.setPosition(knightX, knightY);
    }

    public Vector2D getVelocity(){
        return velocity;
    }

    public void setVelocity(Vector2D v){
        velocity = v;
    }

    public double getAngle(){
        return angle;
    }

    public double getWidth(){
        return radius * 2;
    }

    public void update(double dt){
        if(isDestroyed){
            return;
        }

        knightX += velocity.getX() * dt;
        knightY += velocity.getY() * dt;

        knight.setPosition(knightX, knightY);
    }

    public Rectangle getBounds(){
        return new Rectangle(knightX, knightY, radius * 2, radius * 2);
    }

    public KnightType getKnightType(){
        return knightType;
    }

    public double getExplosiveRadius(){
        return explosiveRadius;
    }

    public double getHp(){
        return hp;
    }

    public void takeDamage(double force){
        hp -= force;
        if(hp <= 0){
            isDestroyed = true;
        }
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public double getRadius(){
        return radius;
    }

    public double getMass(){
        return mass;
    }

    public GraphicsGroup getShape(){
        return fullKnight;
    }
}
