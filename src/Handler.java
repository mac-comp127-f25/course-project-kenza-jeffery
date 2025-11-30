import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Line;

public class Handler extends MouseAdapter{

    private int startX, startY;
    private static final double maxPower = 60;
    private int currentX, currentY;
    private Vector2D vector = new Vector2D(0, 0);

    private Line line;

    private CanvasWindow canvas;

    private boolean isDragging = false;

    public Handler(CanvasWindow canvas){
        this.canvas = canvas;
    }
    
    @Override
    public void mousePressed(MouseEvent e){
        startX = e.getX();
        startY = e.getY();
        currentX = startX;
        currentY = startY;
        isDragging = true;

        line = new Line(startX, startY, startX, startY);
        line.setStrokeColor(new Color(222, 13, 13));
        canvas.add(line);
    }

    @Override
    public void mouseDragged(MouseEvent e){
        if(isDragging){
            currentX = e.getX();
            currentY = e.getY();

            line.setEndPosition(currentX, currentY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        if(isDragging){
            int releaseX = e.getX();
            int releaseY = e.getY();

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
