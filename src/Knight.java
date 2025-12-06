import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;
import edu.macalester.graphics.Rectangle;

public class Knight implements Entity {

    private KnightType knightType;
    private Ellipse knight;
    private GraphicsGroup fullKnight;
    private Image image;
    private Vector2D velocity;

    private double knightX;
    private double knightY;
    private double radius;
    private double hp;
    private double mass;
    private double angle = 0;
    private double explosiveRadius = 0;

    private boolean isStatic = true;
    private boolean isDestroyed = false;

    private long lastHitTime = 0;
    private static final long HIT_COOLDOWN_MS = 120;
    private boolean isFlashing = false;
    private long flashEndTime = 0;
    private final Color originalColor = new Color(232, 155, 229);
    private final Color hitColor = Color.RED;
    private static final long FLASH_DURATION_MS = 120;

    public Knight(KnightType knightType, double knightX, double knightY, double radius){
        this.knightType = knightType;
        this.knightX = knightX;
        this.knightY = knightY;
        this.radius = radius;
        this.mass = knightType.getMass();
        this.hp = knightType.getHp();

        fullKnight = new GraphicsGroup(knightX - radius, knightY - radius);

        knight = new Ellipse(0, 0, radius * 2, radius * 2);
        knight.setFillColor(new Color(232, 155, 229));
        fullKnight.add(knight);

        image = new Image("images/KnightCropped.png");
        image.setPosition(0,0);
        image.setMaxHeight(radius * 2);
        image.setMaxWidth(radius * 2);
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

    public Vector2D getVelocity(){
        return velocity;
    }

    public double getAngle(){
        return angle;
    }

    public double getWidth(){
        return radius * 2;
    }

    public double getExplosiveRadius(){
        return explosiveRadius;
    }

    public double getHp(){
        return hp;
    }

    public double getRadius(){
        return radius;
    }

    public double getMass(){
        return mass;
    }

    public Rectangle getBounds(){
        return new Rectangle(knightX - radius, knightY - radius, radius * 2, radius * 2);
    }

    public KnightType getKnightType(){
        return knightType;
    }

    public GraphicsGroup getShape(){
        return fullKnight;
    }

    public boolean isDestroyed(){
        return isDestroyed;
    }

    public boolean isStatic(){
        return isStatic;
    }

    public void setPosition(Vector2D v){
        knightX = v.getX();
        knightY = v.getY();

        fullKnight.setPosition(knightX - radius, knightY - radius);
    }

    public void setVelocity(Vector2D v){
        velocity = v;
    }

    public void setStatic(boolean s){
        isStatic = s;
    }

    public void update(double dt){
        if(isDestroyed){
            return;
        }

        knightX += velocity.getX() * dt;
        knightY += velocity.getY() * dt;

        fullKnight.setPosition(knightX - radius, knightY - radius);

        if (isFlashing) {
            long now = System.currentTimeMillis();
            if (now >= flashEndTime) {
                knight.setFillColor(originalColor);
                isFlashing = false;
            }
        }
    }

    public void takeDamage(double force){
        long now = System.currentTimeMillis();
        if (now - lastHitTime < HIT_COOLDOWN_MS) {
            return;
        }
        lastHitTime = now;

        hp -= force;
        if(hp <= 0){
            isDestroyed = true;
            this.onDestroy(Game.canvas); 
        } else {
            flashRed();
        }
    }

    public void flashRed(){
        knight.setFillColor(hitColor);
        isFlashing = true;
        flashEndTime = System.currentTimeMillis() + FLASH_DURATION_MS;
    }

    public void onDestroy(CanvasWindow canvas) {
        canvas.remove(fullKnight);
    }

    public void applyImpulse(Vector2D impulse){
        Vector2D dv = impulse.mul(1 / Math.max(1e-6, mass));
        this.velocity = this.velocity.add(dv);
    }
}
