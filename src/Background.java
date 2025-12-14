import edu.macalester.graphics.Image;

/**
 * Background manages the static visual elements of the game.
 * 
 * It loads and positions background imagery and decorative objects
 * without participating in gameplay logic or physics simulation.
 */

public class Background {
    private Image background;

    public Background(String imagePath, double CANVAS_WIDTH, double CANVAS_HEIGHT){
        this.background = new Image(imagePath);
        background.setPosition(0, 0);
        background.setScale(CANVAS_WIDTH / background.getWidth(), CANVAS_HEIGHT / background.getHeight());
    }

    public Image getBackground() {
        return background;
    }

    public void setPosition(double x, double y){
        background.setPosition(x, y);
    }

    public void setSize(double widthScale, double heightScale){
        background.setScale(widthScale, heightScale);
    }
}
