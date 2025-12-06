import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Rectangle;

public interface Entity {
    double getAngle();
    double getWidth();
    double getMass();
    double getRadius();
    double getHp();

    Vector2D getPosition();
    Vector2D getVelocity();
    Rectangle getBounds();
    GraphicsObject getShape();
    boolean isDestroyed();

    void setVelocity(Vector2D v);
    void setPosition(Vector2D v);
    void update(double dt);
    
    void takeDamage(double dmg);
}
