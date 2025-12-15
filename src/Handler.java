import java.awt.Color;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.events.MouseButtonEvent;
import edu.macalester.graphics.events.MouseMotionEvent;

/**
 * Handler manages all mouse-based user interactions.
 * 
 * It translates raw mouse input into high-level gameplay actions
 * such as selecting, dragging, and launching entities.
 * 
 * This class does not update physics or rendering directly;
 * it only interprets player intent and updates game state accordingly.
 */


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

    /**
     * Called when the mouse button is pressed.
     * 
     * Records the initial mouse position and prepares for a drag action.
     * If a valid entity is available, this marks the beginning of a launch
     * interaction (e.g. pulling back a projectile).
     */

    public void mousePressed(MouseButtonEvent e){
        if (e.getPosition().getX() > 300) return;

        startX = e.getPosition().getX();
        startY = e.getPosition().getY();
        currentX = startX;
        currentY = startY;
        isDragging = true;

        line = new Line(startX, startY, startX, startY);
        line.setStrokeColor(new Color(222, 13, 13));
        canvas.add(line);
    }

    /**
     * Called continuously while the mouse is being dragged.
     * 
     * Updates the visual aiming indicator based on the current mouse position.
     * No physics is applied here; this method exists purely to provide
     * responsive visual feedback to the player.
     */

    public void mouseDragged(MouseMotionEvent e){
        if(isDragging){
            currentX = e.getPosition().getX();
            currentY = e.getPosition().getY();

            line.setEndPosition(currentX, currentY);
        }
    }

    /**
     * Called when the mouse button is released.
     * 
     * Finalizes the drag interaction and converts the drag vector
     * into an initial launch velocity for the selected entity.
     * 
     * After release, control is handed over to the physics engine.
     */

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
