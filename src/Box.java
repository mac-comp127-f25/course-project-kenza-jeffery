import java.awt.Color;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.Rectangle;

public class Box implements Entity {
    private MaterialType materialType;
    private double boxX;
    private double boxY;
    private double width;
    private double height;
    private double hp;
    private double mass;
    private double angle = 0;
    
    private Rectangle box;
    private GraphicsGroup fullBox;
    private Vector2D velocity;
    
    private boolean isDestroyed = false;
    private boolean isStatic = false;
    
    private long lastHitTime = 0;
    private static final long HIT_COOLDOWN_MS = 100;
    
    private boolean isFlashing = false;
    private long flashEndTime = 0;
    private Color originalColor;
    
    public Box(MaterialType materialType, double boxX, double boxY, double width, double height) {
        this.materialType = materialType;
        this.boxX = boxX;
        this.boxY = boxY;
        this.width = width;
        this.height = height;
        this.hp = materialType.getHp();
        this.mass = materialType.getMass() * (width * height / 2000.0);
        this.velocity = new Vector2D(0, 0);
        
        fullBox = new GraphicsGroup(boxX, boxY);
        
        box = new Rectangle(0, 0, width, height);
        
        switch(materialType) {
            case WOOD:
                originalColor = new Color(139, 90, 43);
                break;
            case STONE:
                originalColor = new Color(128, 128, 128);
                break;
            case ICE:
                originalColor = new Color(173, 216, 230);
                break;
            default:
                originalColor = new Color(139, 90, 43);
        }
        
        box.setFillColor(originalColor);
        box.setStroked(true);
        box.setStrokeColor(Color.BLACK);
        box.setStrokeWidth(2);
        
        fullBox.add(box);
    }
    
    public Vector2D getPosition() {
        return new Vector2D(boxX + width / 2, boxY + height / 2);
    }
    
    public Vector2D getVelocity() {
        return velocity;
    }
    
    public double getAngle() {
        return angle;
    }
    
    public double getWidth() {
        return width;
    }
    
    public double getHeight() {
        return height;
    }
    
    public double getRadius() {
        return Math.sqrt(width * width + height * height) / 2;
    }
    
    public double getMass() {
        return mass;
    }
    
    public double getHp() {
        return hp;
    }
    
    public Rectangle getBounds() {
        return new Rectangle(boxX, boxY, width, height);
    }
    
    public GraphicsGroup getShape() {
        return fullBox;
    }
    
    public boolean isDestroyed() {
        return isDestroyed;
    }
    
    public boolean isStatic() {
        return isStatic;
    }
    
    public MaterialType getMaterialType() {
        return materialType;
    }
    
    public void setPosition(Vector2D v) {
        boxX = v.getX() - width / 2;
        boxY = v.getY() - height / 2;
        fullBox.setPosition(boxX, boxY);
    }
    
    public void setVelocity(Vector2D v) {
        velocity = v;
    }
    
    public void setStatic(boolean s) {
        isStatic = s;
    }
    
    public void setAngle(double angle) {
        this.angle = angle;
        box.setRotation(Math.toDegrees(angle));
    }
    
    @Override
    public void update(double dt) {
        if (isDestroyed || isStatic) {
            return;
        }
        
        boxX += velocity.getX() * dt;
        boxY += velocity.getY() * dt;
        
        fullBox.setPosition(boxX, boxY);
        
        if (isFlashing) {
            long now = System.currentTimeMillis();
            if (now >= flashEndTime) {
                box.setFillColor(originalColor);
                isFlashing = false;
            }
        }
    }
    
    public void takeDamage(double damage) {
        long now = System.currentTimeMillis();
        
        if (now - lastHitTime < HIT_COOLDOWN_MS) {
            return;
        }
        lastHitTime = now;
        
        hp -= damage;
        
        triggerFlash();
        
        if (hp <= 0) {
            isDestroyed = true;
            box.setFillColor(new Color(0, 0, 0, 0));
            box.setStrokeColor(new Color(0, 0, 0, 0));
        }
    }
    
    public void applyImpulse(Vector2D impulse) {
        if (isStatic) {
            return;
        }
        Vector2D dv = impulse.mul(1.0 / Math.max(1e-6, mass));
        this.velocity = this.velocity.add(dv);
    }
    
    private void triggerFlash() {
        isFlashing = true;
        flashEndTime = System.currentTimeMillis() + 150;
        box.setFillColor(Color.RED);
    }
    
    public Vector2D[] getCorners() {
        Vector2D center = getPosition();
        double halfW = width / 2;
        double halfH = height / 2;
        
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        
        Vector2D[] corners = new Vector2D[4];

        double[][] localCorners = {
            {-halfW, -halfH},
            {halfW, -halfH},
            {halfW, halfH},
            {-halfW, halfH}
        };
        
        for (int i = 0; i < 4; i++) {
            double lx = localCorners[i][0];
            double ly = localCorners[i][1];
            
            double rx = lx * cos - ly * sin;
            double ry = lx * sin + ly * cos;
            
            corners[i] = new Vector2D(center.getX() + rx, center.getY() + ry);
        }
        
        return corners;
    }
}