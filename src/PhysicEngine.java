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
        if(entityA instanceof Coo && entityB instanceof Box){
            resolveCircleRect((Coo)entityA, (Box)entityB);
        }

        if(entityA instanceof Box && entityB instanceof Coo){
            resolveCircleRect((Coo)entityB, (Box)entityA);
        }

        if(entityA instanceof Coo && entityB instanceof Knight){
            resolveCircleCircle((Coo)entityA, (Knight)entityB);
        }

        if(entityA instanceof Knight && entityB instanceof Coo){
            resolveCircleCircle((Coo)entityB, (Knight)entityA);
        }
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

    public void resolveCircleCircle(Coo coo, Knight knight){
        if(knight.isDestroyed()){
            return;
        }

        Vector2D cooPos = coo.getPosition();
        Vector2D knightPos = knight.getPosition();

        Vector2D cooCenter = new Vector2D(
            cooPos.getX() + coo.getRadius(), 
            cooPos.getY() + coo.getRadius());
        
        Vector2D knightCenter = new Vector2D(
            knightPos.getX() + knight.getRadius(), 
            knightPos.getY() + knight.getRadius());
        
        Vector2D normal = knightCenter.sub(cooCenter).normalize();

        Vector2D cooVel = coo.getVelocity();

        double speed = cooVel.getMagnitude();
        double force = speed * coo.getMass();
        knight.takeDamage(force);

        Vector2D newCooVelocity = this.reflectVelocity(cooVel, normal);
        coo.setVelocity(newCooVelocity);

        coo.update(0.016);
        knight.update(0.016);
        
        double distance = cooCenter.sub(knightCenter).getMagnitude();
        double overlap = coo.getRadius() + knight.getRadius() - distance;
    
        if(overlap > 0){
            Vector2D push = normal.mul(-(overlap + 0.5));
            coo.setPosition(coo.getPosition().add(push));
        }
    }

    public void resolveCircleRect(Coo coo, Box box){
        if(box.isDestroyed()){
            return;
        }

        Vector2D cooVel = coo.getVelocity();

        Vector2D cooPos = coo.getPosition();
        Vector2D cooCenter = new Vector2D(
            cooPos.getX() + coo.getRadius(), 
            cooPos.getY() + coo.getRadius());
        
        Vector2D boxPos = box.getPosition();
        Vector2D boxCenter = new Vector2D(
            boxPos.getX() + box.getWidth() / 2.0, 
            boxPos.getY() + box.getHeight() / 2.0);
        
        double angle = box.getAngle();
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);

        double localX = (cooCenter.getX() - boxCenter.getX()) * cos - (cooCenter.getY() - boxCenter.getY()) * sin;
        double localY = (cooCenter.getX() - boxCenter.getX()) * sin + (cooCenter.getY() - boxCenter.getY()) * cos;

        double halfWidth = box.getWidth() / 2.0;
        double halfHeight = box.getHeight() / 2.0;

        double closestX = Math.max(-halfWidth, Math.min(localX, halfWidth));
        double closestY = Math.max(-halfHeight, Math.min(localY, halfHeight));

        double deltaX = localX - closestX;
        double deltaY = localY - closestY;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        if(distance > coo.getRadius()){
            return;
        }

        Vector2D normal = new Vector2D(deltaX, deltaY).normalize();
        if(deltaX == 0 && deltaY == 0){
            normal = new Vector2D(0, -1);
        }

        double speed = coo.getVelocity().getMagnitude();
        double force = speed * coo.getMass();

        box.takeDamage(force);

        Vector2D newCooVelocity = reflectVelocity(cooVel, normal);
        coo.setVelocity(newCooVelocity);

        double overlap = coo.getRadius() - distance;
        if(overlap > 0){
            Vector2D push = normal.mul(overlap + 0.5);
            coo.setPosition(coo.getPosition().add(push));
        }
    }
}