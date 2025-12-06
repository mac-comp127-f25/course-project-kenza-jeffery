import edu.macalester.graphics.Arc;
import edu.macalester.graphics.GraphicsGroup;

import java.awt.Color;
import edu.macalester.graphics.Rectangle;

public class Slingshot {
    private int x = 185;
    private int y = 550;
    private Vector2D stretch = new Vector2D(0, 0);
    private Vector2D anchor = new Vector2D(x + 10, y);
    private double maxStretch = 100;
    private GraphicsGroup shape;

    public Slingshot(Color color, Color secondColor){
        shape = new GraphicsGroup();

        Rectangle base = new Rectangle(x, y, 37.5, 150);
        base.setStrokeColor(color);
        base.setFillColor(color);

        Arc curve = new Arc(x + 1.5, y - 38, 35, 75, 0, 180);
        curve.setStrokeWidth(22);
        curve.setRotation(180);
        curve.setStrokeColor(color);

        Rectangle band = new Rectangle(x + 15, y - 55, 5, 20);
        band.setStrokeColor(secondColor);
        band.setFillColor(secondColor);
        band.setRotation(90);

        shape.add(base);
        shape.add(curve);
        shape.add(band);
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
