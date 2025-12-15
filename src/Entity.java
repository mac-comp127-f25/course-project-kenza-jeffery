import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

/**
 * Entity represents any interactive object in the game world
 * that participates in physics and collision handling.
 * Not all methods are used equally by every implementation.
 */

public interface Entity {
    double getAngle();
    double getWidth();
    double getMass();
    double getRadius();
    double getHp();
    double getScore();

    Vector2D getPosition();
    Vector2D getVelocity();
    Rectangle getBounds();
    GraphicsObject getShape();
    boolean isDestroyed();

    void setVelocity(Vector2D v);
    void setPosition(Vector2D v);
    void update(double dt);
    
    void takeDamage(double dmg);
    void applyImpulse(Vector2D impulse);
}
