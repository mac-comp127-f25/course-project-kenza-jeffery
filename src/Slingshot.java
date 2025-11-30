import edu.macalester.graphics.Arc;
import edu.macalester.graphics.GraphicsGroup;
import java.awt.Color;
import edu.macalester.graphics.Rectangle;

public class Slingshot {
    private int x = 20;
    private int y = 500;
    private Vector2D stretch;
    private Vector2D anchor;
    private double maxStretch;
    private GraphicsGroup shape;

    public Slingshot(Color color){
        Rectangle base = new Rectangle(x, y, 20, 40);
        base.setStrokeColor(color);
        base.setFillColor(color);
        Arc curve = new Arc(x, y + 40, 20, 40, 0, 180);
        curve.setStrokeColor(color);

        shape.add(base);
        shape.add(curve);
    }
    
    public GraphicsGroup getShape() {
        return shape;  
    }
}
