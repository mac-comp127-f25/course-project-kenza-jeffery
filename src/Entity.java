import edu.macalester.graphics.Rectangle;

public interface Entity {
    Vector2D getPosition();
    Vector2D getVelocity();
    double getAngle();
    double getWidth();
    void setVelocity(Vector2D v);
    void setPosition(Vector2D v);
    void update(double dt);
    Rectangle getBounds();
    double getMass();
    boolean isDestroyed();
}
