import edu.macalester.graphics.Arc;
import edu.macalester.graphics.GraphicsGroup;
import java.awt.Color;
import java.awt.Rectangle;

public class Slingshot {
    private double x = 20;
     private double y = 500;
    private Vector2D stretch;
    private Vector2D anchor;
    private double maxStretch;
    private GraphicsGroup shape;

    public Slingshot(double radius, Color color){
        Rectangle base = new Rectangle();
        Arc curve = new Arc(radius, radius, radius, radius, radius, radius);
    }
    
    public GraphicsGroup getShape() {
        
    }
}
