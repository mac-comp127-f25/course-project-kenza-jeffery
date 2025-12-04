import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.events.MouseButtonEvent;
import edu.macalester.graphics.events.MouseMotionEvent;

public class Handler{

    private double startX, startY;
    private static final double maxPower = 60;
    private double currentX, currentY;
    private Vector2D vector = new Vector2D(0, 0);

    private Line line;

    private CanvasWindow canvas;

    private boolean isDragging = false;

    public Handler(CanvasWindow canvas){
        this.canvas = canvas;
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

            vector = draggedVector;
            
            canvas.remove(line);
            line = null;

            isDragging = false;
        }
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
}
