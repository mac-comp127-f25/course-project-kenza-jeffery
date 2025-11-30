import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
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

    private boolean isDestroyed = false;
    private CanvasWindow canvas;

    private double explosiveRadius;

    public Knight(KnightType knightType, double knightX, double knightY, double radius, CanvasWindow canvas){
        this.canvas = canvas;
        this.knightType = knightType;
        this.knightX = knightX;
        this.knightY = knightY;
        this.radius = radius;
        this.hp = knightType.getHp();

        knight = new Ellipse(knightX, knightY, radius * 2, radius * 2);

        velocity = new Vector2D(0, 0);

        switch(knightType){
            case explosiveKnight:
                explosiveRadius = knightType.getSpecialValue();
                break;
            default:
                break;
        }

        canvas.add(knight);
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
            canvas.remove(knight);
        }
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public double getRadius(){
        return radius;
    }
}
