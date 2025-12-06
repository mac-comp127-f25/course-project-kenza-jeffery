import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class PhysicEngine {

    private static final double DAMAGE_K = 12.0;
    private static final double MIN_HIT_SPEED = 30.0;
    private static final double RESTITUTION = 0.5;
    private static final long HIT_COOLDOWN_MS = 120;

    private List<Box> boxes = new ArrayList<>();
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private final Map<Entity, Long> lastHitTimes = new WeakHashMap<>();

    private Vector2D gravity = new Vector2D(0, 170);
    private double timeStep = 0.016;

    public void setCoos(List<Coo> cooList){
        coos = cooList;
    }

    public void setBoxes(List<Box> boxList){
        boxes = boxList;
    }

    public void setKnights(List<Knight> knightList){
        knights = knightList;
    }

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

        for (Coo coo : new ArrayList<>(coos)) {
            integrate(coo);
            checkGroundCollision(coo);
        }

        for (Knight knight : new ArrayList<>(knights)) {
            if (!knight.isStatic()) {
                integrate(knight);
                checkGroundCollision(knight);
            }
        }

        for (Box box : new ArrayList<>(boxes)) {
            if (!box.isStatic()) {
                integrate(box);
                checkGroundCollision(box);
            }
        }

        this.checkCollision();

        coos.removeIf(Coo::isDestroyed);
        knights.removeIf(Knight::isDestroyed);
        boxes.removeIf(Box::isDestroyed);
    }

    private void integrate(Entity entity){

        if (entity instanceof Coo && ((Coo) entity).isDragging()) {
            return;
        }

        if (entity instanceof Box && ((Box) entity).isDestroyed()){
            return;
        }

        if (entity instanceof Knight && ((Knight) entity).isDestroyed()){
            return;
        }

        Vector2D velocity = entity.getVelocity();
        Vector2D position = entity.getPosition();

        if (velocity == null) {
            velocity = new Vector2D(0, 0);
        }

        if (position == null) {
            return;
        }

        boolean applyGravity = true;

        if (entity instanceof Knight && ((Knight) entity).isStatic()){
            applyGravity = false;
        }

        if (entity instanceof Box && ((Box) entity).isStatic()){
            applyGravity = false;
        }

        Vector2D newVelocity = velocity;

        if (applyGravity) {
            newVelocity = velocity.add(gravity.mul(timeStep));
        }

        Vector2D newPosition = position.add(newVelocity.mul(timeStep));

        entity.setPosition(newPosition);
        entity.setVelocity(newVelocity);
    }

    private void resolveCollision(Entity entityA, Entity entityB){
        
        if(entityA instanceof Coo && entityB instanceof Box){
            resolveCircleRect((Coo)entityA, (Box)entityB);
        } else if(entityA instanceof Box && entityB instanceof Coo){
            resolveCircleRect((Coo)entityB, (Box)entityA);
        } else if(entityA instanceof Coo && entityB instanceof Knight){
            resolveCircleCircle((Coo)entityA, (Knight)entityB);
        } else if(entityA instanceof Knight && entityB instanceof Coo){
            resolveCircleCircle((Coo)entityB, (Knight)entityA);
        }
    }

    private boolean circleCircleCollision(Coo coo, Knight knight){

        Vector2D c = coo.getPosition(); 
        Vector2D k = knight.getPosition(); 
        double dx = c.getX() - k.getX();
        double dy = c.getY() - k.getY();
        double distSq = dx*dx + dy*dy;
        double rsum = coo.getRadius() + knight.getRadius();
        return distSq <= rsum * rsum;
    }

    private boolean circleRectangleCollision(Coo coo, Box box) {

        Vector2D c = coo.getPosition();
        double circleX = c.getX();
        double circleY = c.getY();

        Vector2D boxCenter = box.getPosition();
        double rectCenterX = boxCenter.getX();
        double rectCenterY = boxCenter.getY();

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
        double distanceSq = distanceX * distanceX + distanceY * distanceY;

        return distanceSq <= coo.getRadius() * coo.getRadius();
    }


    public void checkCollision(){

        for (Coo coo : new ArrayList<>(coos)) {
            for (Knight knight : new ArrayList<>(knights)) {
                if (circleCircleCollision(coo, knight)) {
                    resolveCollision(coo, knight);
                }
            }
            for (Box box : new ArrayList<>(boxes)) {
                if (circleRectangleCollision(coo, box)) {
                    resolveCollision(coo, box);
                }
            }
        }
    }

    public void resolveCircleCircle(Coo coo, Knight knight){
        if(knight.isDestroyed() || coo.isDestroyed()){
            return;
        }

        Vector2D c = coo.getPosition();
        Vector2D k = knight.getPosition();

        Vector2D normal = k.sub(c);
        double dist = normal.getMagnitude();
        if (dist == 0) {
            normal = new Vector2D(0, -1);
            dist = 1;
        } else {
            normal = normal.mul(1 / dist);
        }

        Vector2D vCo = coo.getVelocity() == null ? new Vector2D(0,0) : coo.getVelocity();
        Vector2D vKn = knight.getVelocity() == null ? new Vector2D(0,0) : knight.getVelocity();
        Vector2D relVel = vCo.sub(vKn);

        double relSpeed = relVel.dot(normal);

        if (relSpeed <= 0) {
            separateOverlapCircleCircle(coo, knight, normal, dist);
            return;
        }

        // Damage
        if (relSpeed >= MIN_HIT_SPEED) {
            double dmg = DAMAGE_K * relSpeed * (coo.getMass() / Math.max(0.1, knight.getMass()));
            boolean applied = applyHitWithCooldown(knight, dmg);
            if (applied) {
                // try flash if exists
                try { java.lang.reflect.Method m = knight.getClass().getMethod("flashRed"); m.invoke(knight); } catch (Exception ignored) {}
            }
        }

        double m1 = coo.getMass();
        double m2 = knight.getMass();
        boolean knightStatic = knight.isStatic();

        double invM1 = 1.0 / Math.max(1e-6, m1);
        double invM2 = knightStatic ? 0.0 : (1.0 / Math.max(1e-6, m2));

        double impulseMag = -(1 + RESTITUTION) * relSpeed / (invM1 + invM2);
        Vector2D impulse = normal.mul(impulseMag);

        coo.setVelocity(coo.getVelocity().add(impulse.mul(invM1)));

        if (!knightStatic) {
            knight.setVelocity(knight.getVelocity().sub(impulse.mul(invM2)));
        } else {
            double pushThreshold = 200;
            if (Math.abs(impulseMag) > pushThreshold) {
                knight.setStatic(false);
                knight.setVelocity(knight.getVelocity().sub(impulse.mul(invM2)));
            }
        }

        separateOverlapCircleCircle(coo, knight, normal, dist);
    }

    private void separateOverlapCircleCircle(Coo coo, Knight knight, Vector2D normal, double dist){
        double overlap = coo.getRadius() + knight.getRadius() - dist;
        if (overlap > 0) {
            Vector2D push = normal.mul(- (overlap + 0.5));
            coo.setPosition(coo.getPosition().add(push));
        }
    }

    public void resolveCircleRect(Coo coo, Box box){
        if(box.isDestroyed() || coo.isDestroyed()){
            return;
        }

        Vector2D c = coo.getPosition();
        Vector2D boxCenter = box.getPosition(); 

        double angle = box.getAngle();
        double cos = Math.cos(-angle);
        double sin = Math.sin(-angle);

        double relX = c.getX() - boxCenter.getX();
        double relY = c.getY() - boxCenter.getY();

        double localX = relX * cos - relY * sin;
        double localY = relX * sin + relY * cos;

        double halfW = box.getWidth() / 2.0;
        double halfH = box.getHeight() / 2.0;

        double closestX = Math.max(-halfW, Math.min(localX, halfW));
        double closestY = Math.max(-halfH, Math.min(localY, halfH));

        double dx = localX - closestX;
        double dy = localY - closestY;
        double dist = Math.sqrt(dx*dx + dy*dy);
        if (dist == 0) {
            dx = 0; dy = -1; dist = 1;
        }

        if (dist > coo.getRadius()){
            return; // no penetration
        }

        Vector2D localNormal = new Vector2D(dx/dist, dy/dist);
        Vector2D normal = new Vector2D(
            localNormal.getX() * cos + localNormal.getY() * -sin,
            localNormal.getX() * sin + localNormal.getY() * cos
        ).normalize();

        Vector2D vCo = coo.getVelocity() == null ? new Vector2D(0,0) : coo.getVelocity();
        Vector2D vBox = box.getVelocity() == null ? new Vector2D(0,0) : box.getVelocity();
        Vector2D relVel = vCo.sub(vBox);

        double relSpeed = relVel.dot(normal);

        if (relSpeed <= 0) {
            Vector2D push = normal.mul(coo.getRadius() - dist + 0.5);
            coo.setPosition(coo.getPosition().add(push));
            return;
        }

        if (relSpeed >= MIN_HIT_SPEED) {
            double dmg = DAMAGE_K * relSpeed * (coo.getMass() / Math.max(0.1, box.getMass()));
            boolean applied = applyHitWithCooldown(box, dmg);
            if (applied) {
                try { java.lang.reflect.Method m = box.getClass().getMethod("flashRed"); m.invoke(box); } catch (Exception ignored) {}
            }
        }

        double m1 = coo.getMass();
        double m2 = box.getMass();
        boolean boxStatic = box.isStatic();

        double invM1 = 1.0/Math.max(1e-6, m1);
        double invM2 = boxStatic ? 0.0 : (1.0/Math.max(1e-6, m2));

        double effectiveRel = Math.max(0, relVel.dot(normal));
        double impulseMag = -(1 + RESTITUTION) * effectiveRel / (invM1 + invM2);
        Vector2D impulse = normal.mul(impulseMag);

        coo.setVelocity(coo.getVelocity().add(impulse.mul(invM1)));
        if (!boxStatic) {
            box.setVelocity(box.getVelocity().sub(impulse.mul(invM2)));
        } else {
            double pushThreshold = 200;
            if (Math.abs(impulseMag) > pushThreshold) {
                box.setStatic(false);
            }
        }

        // separate overlap
        double overlap = coo.getRadius() - dist;
        if (overlap > 0){
            Vector2D push = normal.mul(overlap + 0.5);
            coo.setPosition(coo.getPosition().add(push));
        }
    }

    private boolean applyHitWithCooldown(Entity target, double dmg){
        long now = System.currentTimeMillis();
        Long last = lastHitTimes.get(target);
        if (last != null && (now - last) < HIT_COOLDOWN_MS) {
            return false;
        }
        lastHitTimes.put(target, now);
        target.takeDamage(dmg);
        return true;
    }

    public void checkGroundCollision(Entity entity){
        double groundY = 700;
        Vector2D pos = entity.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double height = 0;
        double friction = 0.9;

        if(entity instanceof Coo){
            Coo coo = (Coo)entity;
            height = coo.getRadius() * 2;
        } else if(entity instanceof Knight){
            Knight knight = (Knight)entity;
            height = knight.getRadius() * 2;
        } else if(entity instanceof Box){
            Box box = (Box)entity;
            height = box.getHeight();
        }

        double bottomY = y + height/2.0; 

        if(bottomY >= groundY){
            double newY = groundY - height/2.0;
            entity.setPosition(new Vector2D(x, newY));

            Vector2D v = entity.getVelocity();
            entity.setVelocity(new Vector2D(v.getX() * friction, -v.getY() * 0.3));
        }
    }
}
