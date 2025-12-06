import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.events.MouseButtonEvent;
import edu.macalester.graphics.events.MouseMotionEvent;

public class Handler{

    private static final double maxPower = 6000;

    private double startX;
    private double startY;
    private double currentX;
    private double currentY;
    
    private Vector2D vector = new Vector2D(0, 0);

    private CanvasWindow canvas;
    private Line line;

    private boolean isDragging = false;

    public Handler(CanvasWindow canvas){
        this.canvas = canvas;
    }

    public Vector2D getVector(){
        return vector;
    }

    public double getStartX(){
        return startX;
    }

    public double getStartY(){
        return startY;
    }

    public double getCurrentX(){
        return currentX;
    }

    public double getCurrentY(){
        return currentY;
    }
    
    public void mousePressed(MouseButtonEvent e){
        startX = e.getPosition().getX();
        startY = e.getPosition().getY();
        currentX = startX;
        currentY = startY;
        isDragging = true;

        line = new Line(startX, startY, startX, startY);
        line.setStrokeColor(new Color(222, 13, 13));
        canvas.add(line);
    }

    public void mouseDragged(MouseMotionEvent e){
        if(isDragging){
            currentX = e.getPosition().getX();
            currentY = e.getPosition().getY();

            line.setEndPosition(currentX, currentY);
        }
    }

    public void mouseReleased(MouseButtonEvent e){
        if(isDragging){
            double releaseX = e.getPosition().getX();
            double releaseY = e.getPosition().getY();

            Vector2D draggedVector = new Vector2D(startX - releaseX, startY - releaseY);

            if(draggedVector.getMagnitude() >= maxPower){
                draggedVector = draggedVector.normalize().mul(maxPower);
            }

            vector = draggedVector.mul(2.5);
            
            canvas.remove(line);
            line = null;

            isDragging = false;
        }
    }
}
