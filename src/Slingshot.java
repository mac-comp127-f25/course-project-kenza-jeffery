import edu.macalester.graphics.Arc;
import edu.macalester.graphics.GraphicsGroup;

import java.awt.Color;
import edu.macalester.graphics.Rectangle;

public class Slingshot {
    private int x = 20;
    private int y = 500;
    private Vector2D stretch = new Vector2D(0, 0);
    private Vector2D anchor = new Vector2D(x + 10, y);
    private double maxStretch = 100;
    private GraphicsGroup shape;

    public Slingshot(Color color){
        shape = new GraphicsGroup();

        Rectangle base = new Rectangle(x, y, 20, 40);
        base.setStrokeColor(color);
        base.setFillColor(color);

        Arc curve = new Arc(x, y + 40, 20, 40, 0, 180);
        curve.setStrokeColor(color);

        shape.add(base);
        shape.add(curve);
    }

    public void setStretch(Vector2D dragPosition){
        Vector2D dif = dragPosition.sub(anchor);
        if(dif.getMagnitude() > maxStretch){
            dif = dif.normalize().mul(maxStretch);
        } 
        this.stretch = dif;
    }
    
    public GraphicsGroup getShape() {
        return shape;  
    }

    public Vector2D getAnchor(){
        return anchor;
    }

    public Vector2D getStretch(){
        return stretch;
    }

    public double getMaxStretch(){
        return maxStretch;
    }
}
