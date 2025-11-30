import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    private Vector2D gravity = new Vector2D(0, 200);
    private List<Box> boxes = new ArrayList<>();
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private double timeStep = 0.016;
    

    public void addCoo(Coo coo){
        coos.add(coo);
    }

    public void addBox(Box box){
        boxes.add(box);
    }

    public void addKnight(Knight knight){
        knights.add(knight);
    }

    public void removeCoo(Coo coo){
        coos.remove(coo);
    }

    public void removeBox(Box box){
        boxes.remove(box);
    }

    public void removeKnight(Knight knight){
        knights.remove(knight);
    }

    public void update(){
        for(Coo coo : coos){
            this.integrate(coo);
        }

        for(Knight knight : knights){
            this.integrate(knight);
        }

        for(Box box : boxes){
            this.integrate(box);
        }

        this.checkCollision();
    }

    public void integrate(Entity entity){

        if (entity instanceof Box && ((Box) entity).isDestroyed()){
            return;
        }

        if (entity instanceof Knight && ((Knight) entity).isDestroyed()){
            return;
        }

        Vector2D velocity = entity.getVelocity();
        Vector2D position = entity.getPosition();

        if(velocity == null){
            velocity = new Vector2D(0, 0);
        }

        if(position == null){
            return;
        }

        Vector2D newVelocity = velocity.add(gravity.mul(timeStep));
        Vector2D newPosition = position.add(newVelocity.mul(timeStep));

        entity.setPosition(newPosition);
        entity.setVelocity(newVelocity);
    }

    public void resolveCollision(Entity entityA, Entity entityB){

    }

    public Vector2D reflectVelocity(Vector2D vector, Vector2D normalVector){
        Vector2D n = normalVector.normalize();
        return vector.sub(n.mul(2 * vector.dot(n)));
    }

    private boolean circleCircleCollision(Coo coo, Knight knight){
        double cooCenterX = coo.getPosition().getX() + coo.getRadius();
        double cooCenterY = coo.getPosition().getY() + coo.getRadius();

        double knightCenterX = knight.getPosition().getX() + knight.getRadius();
        double knightCenterY = knight.getPosition().getY() + knight.getRadius();

        double distanceX = cooCenterX - knightCenterX;
        double distanceY = cooCenterY - knightCenterY;

        double length = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if(length <= coo.getRadius() + knight.getRadius()){
            return true;
        } else{
            return false;
        }
    }

    private boolean circleRectangleCollision(Coo coo, Box box) {

        double circleX = coo.getPosition().getX() + coo.getRadius();
        double circleY = coo.getPosition().getY() + coo.getRadius();

        double rectCenterX = box.getX() + box.getWidth() / 2.0;
        double rectCenterY = box.getY() + box.getHeight() / 2.0;

        double relX = circleX - rectCenterX;
        double relY = circleY - rectCenterY;

        double angle = box.getAngle();
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);
        double localX = relX * cos - relY * sin;
        double localY = relX * sin + relY * cos;

        double hw = box.getWidth() / 2.0;
        double hh = box.getHeight() / 2.0;

        double closestX = Math.max(-hw, Math.min(localX, hw));
        double closestY = Math.max(-hh, Math.min(localY, hh));

        double distanceX = localX - closestX;
        double distanceY = localY - closestY;
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

        if(distance <= coo.getRadius()){
            return true;
        } else{
            return false;
        }
    }


    public void checkCollision(){
        for(Coo coo : coos){
            for(Knight knight : knights){
                if(circleCircleCollision(coo, knight)){
                    resolveCollision(coo, knight);
                }
            }

            for(Box box : boxes){
                if(circleRectangleCollision(coo, box)){
                    resolveCollision(coo, box);
                }
            }
        }
    }
}