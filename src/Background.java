import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Image;

public class Background {

    // private Image background;
    private Image background;
    // private Image image;
    // private Image grassImage;
    //new Image("images/Gemini_Generated_Image_asm55qasm55qasm5.png");

    public Background(String imagePath, double CANVAS_WIDTH, double CANVAS_HEIGHT){
        // image = new Image(imagePath);
        // grassImage = new Image(grassPath);
        // background.add(image);
        // background.add(grassImage);

        this.background = new Image(imagePath);
        background.setPosition(0, 65);
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
