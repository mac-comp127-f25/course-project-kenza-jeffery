import java.util.ArrayList;
import java.util.List;

public class PhysicEngine {

    private static final double GRAVITY = 170.0;
    private static final double GROUND_Y = 700.0;
    private static final double RESTITUTION = 0.35;
    private static final double FRICTION = 0.92;
    private static final double AIR_RESISTANCE = 0.998;
    private static final double MIN_VELOCITY = 2.0;
    private static final double WALL_LEFT = 0;
    private static final double WALL_RIGHT = 1280;
    
    private static final double DAMAGE_THRESHOLD = 50.0; 
    private static final double DAMAGE_MULTIPLIER = 0.8;
    private static final int COLLISION_ITERATIONS = 2;
    
    private List<Coo> coos = new ArrayList<>();
    private List<Knight> knights = new ArrayList<>();
    private List<Box> boxes = new ArrayList<>();
    
    private double dt = 1.0 / 60.0;
    
    public PhysicEngine() {
    }
    
    public void setCoos(List<Coo> coos) {
        this.coos = coos;
    }
    
    public void setKnights(List<Knight> knights) {
        this.knights = knights;
    }
    
    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
        for (Box box : boxes) {
            box.setStatic(true);
            box.setVelocity(new Vector2D(0, 0));
        }
    }
    
    public void addCoo(Coo coo) {
        if (!coos.contains(coo)) {
            coos.add(coo);
        }
    }
    
    public void removeCoo(Coo coo) {
        coos.remove(coo);
    }
    
    public void update() {

        updateAllEntities();
        checkSupport();
        
        for (int iter = 0; iter < COLLISION_ITERATIONS; iter++) {
            handleAllCollisions();
        }

        handleAllBoundaries();
        
        cleanup();
    }
    
    private void updateAllEntities() {

        for (Coo coo : coos) {
            if (coo.isLaunch() && !coo.isDestroyed()) {
                Vector2D vel = coo.getVelocity();
                vel = vel.add(new Vector2D(0, GRAVITY * dt));
                vel = vel.mul(AIR_RESISTANCE);
                coo.setVelocity(vel);
                coo.update(dt);
            }
        }
        
        for (Knight knight : knights) {
            if (!knight.isDestroyed() && !knight.isStatic()) {
                Vector2D vel = knight.getVelocity();
                vel = vel.add(new Vector2D(0, GRAVITY * dt));
                vel = vel.mul(AIR_RESISTANCE);
                knight.setVelocity(vel);
                knight.update(dt);
            }
        }
        
        for (Box box : boxes) {
            if (!box.isDestroyed() && !box.isStatic()) {
                Vector2D vel = box.getVelocity();
                vel = vel.add(new Vector2D(0, GRAVITY * dt));
                vel = vel.mul(AIR_RESISTANCE);
                box.setVelocity(vel);
                box.update(dt);
            }
        }
    }
    
    private void handleAllCollisions() {
        // Coo vs Knight
        for (Coo coo : new ArrayList<>(coos)) {
            if (!coo.isLaunch() || coo.isDestroyed()) continue;
            
            for (Knight knight : new ArrayList<>(knights)) {
                if (knight.isDestroyed()) continue;
                
                if (circleCircleCollision(coo, knight)) {
                    resolveCircleCircle(coo, knight, true);
                }
            }
        }
        
        // Coo vs Box
        for (Coo coo : new ArrayList<>(coos)) {
            if (!coo.isLaunch() || coo.isDestroyed()) continue;
            
            for (Box box : new ArrayList<>(boxes)) {
                if (box.isDestroyed()) continue;
                
                if (circleBoxCollision(coo, box)) {
                    resolveCircleBox(coo, box, true);
                }
            }
        }
        
        // Knight vs Knight
        for (int i = 0; i < knights.size(); i++) {
            Knight k1 = knights.get(i);
            if (k1.isDestroyed()){
                continue;
            }
            for (int j = i + 1; j < knights.size(); j++) {
                Knight k2 = knights.get(j);
                if (k2.isDestroyed()) continue;
                
                if (circleCircleCollision(k1, k2)) {
                    resolveCircleCircle(k1, k2, false);
                }
            }
        }
        
        // Knight vs Box
        for (Knight knight : new ArrayList<>(knights)) {
            if (knight.isDestroyed()){
                continue;
            }
            for (Box box : new ArrayList<>(boxes)) {
                if (box.isDestroyed()){
                    continue;
                }
                if (circleBoxCollision(knight, box)) {
                    resolveCircleBox(knight, box, false);
                }
            }
        }
        
        // Box vs Box
        for (int i = 0; i < boxes.size(); i++) {
            Box b1 = boxes.get(i);
            if (b1.isDestroyed()) continue;
            
            for (int j = i + 1; j < boxes.size(); j++) {
                Box b2 = boxes.get(j);
                if (b2.isDestroyed()) continue;
                
                if (boxBoxCollision(b1, b2)) {
                    resolveBoxBox(b1, b2);
                }
            }
        }
    }
    
    private boolean circleCircleCollision(Entity e1, Entity e2) {
        Vector2D pos1 = e1.getPosition();
        Vector2D pos2 = e2.getPosition();
        double r1 = e1.getRadius();
        double r2 = e2.getRadius();
        
        Vector2D diff = pos1.sub(pos2);
        double distSq = diff.getX() * diff.getX() + diff.getY() * diff.getY();
        double radiusSum = r1 + r2;
        
        return distSq < radiusSum * radiusSum;
    }
    
    private boolean circleBoxCollision(Entity circle, Box box) {
        Vector2D circlePos = circle.getPosition();
        Vector2D boxPos = box.getPosition();
        double radius = circle.getRadius();
        double halfW = box.getWidth() / 2;
        double halfH = box.getHeight() / 2;
        
        //closest point
        double closestX = Math.max(boxPos.getX() - halfW, Math.min(circlePos.getX(), boxPos.getX() + halfW));
        double closestY = Math.max(boxPos.getY() - halfH, Math.min(circlePos.getY(), boxPos.getY() + halfH));
        
        double dx = circlePos.getX() - closestX;
        double dy = circlePos.getY() - closestY;
        
        return (dx * dx + dy * dy) < (radius * radius);
    }
    
    private boolean boxBoxCollision(Box b1, Box b2) {
        Vector2D pos1 = b1.getPosition();
        Vector2D pos2 = b2.getPosition();
        
        double hw1 = b1.getWidth() / 2;
        double hh1 = b1.getHeight() / 2;
        double hw2 = b2.getWidth() / 2;
        double hh2 = b2.getHeight() / 2;
        
        return Math.abs(pos1.getX() - pos2.getX()) < (hw1 + hw2) &&
               Math.abs(pos1.getY() - pos2.getY()) < (hh1 + hh2);
    }
    
    private void resolveCircleCircle(Entity e1, Entity e2, boolean applyDamage) {
        Vector2D pos1 = e1.getPosition();
        Vector2D pos2 = e2.getPosition();
        Vector2D vel1 = e1.getVelocity();
        Vector2D vel2 = e2.getVelocity();
        
        Vector2D delta = pos1.sub(pos2);
        double dist = delta.getMagnitude();
        
        if (dist < 0.1) {
            delta = new Vector2D(1, 0);
            dist = 1;
        }
        
        Vector2D normal = delta.normalize();
        double r1 = e1.getRadius();
        double r2 = e2.getRadius();
        
        // overlap
        double overlap = (r1 + r2) - dist;
        if (overlap > 0) {
            double m1 = e1.getMass();
            double m2 = e2.getMass();

            boolean e1Static = (e1 instanceof Knight && ((Knight)e1).isStatic()) || 
                               (e1 instanceof Box && ((Box)e1).isStatic());
            boolean e2Static = (e2 instanceof Knight && ((Knight)e2).isStatic()) || 
                               (e2 instanceof Box && ((Box)e2).isStatic());
            
            if (e1Static && e2Static) {
                return; 
            } else if (e1Static) {
                e2.setPosition(pos2.sub(normal.mul(overlap)));
            } else if (e2Static) {
                e1.setPosition(pos1.add(normal.mul(overlap)));
            } else {
                double total = m1 + m2;
                e1.setPosition(pos1.add(normal.mul(overlap * m2 / total)));
                e2.setPosition(pos2.sub(normal.mul(overlap * m1 / total)));
            }
        }
        
        Vector2D relVel = vel1.sub(vel2);
        double velAlongNormal = relVel.dot(normal);
        
        if (velAlongNormal > 0) return;
        
        //impulse
        double m1 = e1.getMass();
        double m2 = e2.getMass();
        double e = RESTITUTION;
        
        boolean e1Static = (e1 instanceof Knight && ((Knight)e1).isStatic()) || 
                           (e1 instanceof Box && ((Box)e1).isStatic());
        boolean e2Static = (e2 instanceof Knight && ((Knight)e2).isStatic()) || 
                           (e2 instanceof Box && ((Box)e2).isStatic());
        
        double j;
        if (e1Static && !e2Static) {
            j = -(1 + e) * velAlongNormal;
            e2.applyImpulse(normal.mul(-j * m2));
            
            if (e1 instanceof Knight){
                ((Knight)e1).setStatic(false);
            } 

            if (e1 instanceof Box) {
                ((Box)e1).setStatic(false);
            }

        } else if (e2Static && !e1Static) {
            j = -(1 + e) * velAlongNormal;
            e1.applyImpulse(normal.mul(j * m1));
            
            if (e2 instanceof Knight) {
                ((Knight)e2).setStatic(false);
            }

            if (e2 instanceof Box) {
                ((Box)e2).setStatic(false);
            }
        } else if (!e1Static && !e2Static) {
            j = -(1 + e) * velAlongNormal / (1/m1 + 1/m2);
            e1.applyImpulse(normal.mul(j));
            e2.applyImpulse(normal.mul(-j));
        } else {
            return;
        }
        
        //damage
        if (applyDamage) {
            double impactSpeed = Math.abs(velAlongNormal);
            if (impactSpeed > DAMAGE_THRESHOLD) {
                double damage = (impactSpeed - DAMAGE_THRESHOLD) * DAMAGE_MULTIPLIER;
                e1.takeDamage(damage * 0.15);
                e2.takeDamage(damage * 1.5);
            }
        }
    }
    
    private void resolveCircleBox(Entity circle, Box box, boolean applyDamage) {
        Vector2D circlePos = circle.getPosition();
        Vector2D boxPos = box.getPosition();
        Vector2D circleVel = circle.getVelocity();
        Vector2D boxVel = box.getVelocity();
        double radius = circle.getRadius();
        double halfW = box.getWidth() / 2;
        double halfH = box.getHeight() / 2;
        
        // closest point
        double closestX = Math.max(boxPos.getX() - halfW, Math.min(circlePos.getX(), boxPos.getX() + halfW));
        double closestY = Math.max(boxPos.getY() - halfH, Math.min(circlePos.getY(), boxPos.getY() + halfH));
        
        Vector2D closest = new Vector2D(closestX, closestY);
        Vector2D delta = circlePos.sub(closest);
        double dist = delta.getMagnitude();
        
        if (dist < 0.1) {
            delta = circlePos.sub(boxPos);
            dist = delta.getMagnitude();
            if (dist < 0.1) {
                delta = new Vector2D(0, -1);
                dist = 1;
            }
        }
        
        Vector2D normal = delta.normalize();

        double overlap = radius - dist;
        if (overlap > 0) {
            if (box.isStatic()) {
                circle.setPosition(circlePos.add(normal.mul(overlap)));
            } else {
                double m1 = circle.getMass();
                double m2 = box.getMass();
                double total = m1 + m2;
                circle.setPosition(circlePos.add(normal.mul(overlap * m2 / total)));
                box.setPosition(boxPos.sub(normal.mul(overlap * m1 / total)));
            }
        }
        
        Vector2D relVel = circleVel.sub(boxVel);
        double velAlongNormal = relVel.dot(normal);
        
        if (velAlongNormal > 0) return;
        
        // impulse
        double m1 = circle.getMass();
        double m2 = box.getMass();
        double e = RESTITUTION;
        double j;
        
        if (box.isStatic()) {
            j = -(1 + e) * velAlongNormal;
            circle.applyImpulse(normal.mul(j * m1));
            
            box.setStatic(false);
        } else {
            j = -(1 + e) * velAlongNormal / (1/m1 + 1/m2);
            circle.applyImpulse(normal.mul(j));
            box.applyImpulse(normal.mul(-j));
        }
        
        if (applyDamage) {
            double impactSpeed = Math.abs(velAlongNormal);
            if (impactSpeed > DAMAGE_THRESHOLD) {
                double damage = (impactSpeed - DAMAGE_THRESHOLD) * DAMAGE_MULTIPLIER;
                circle.takeDamage(damage * 0.15);
                box.takeDamage(damage * 1.5);
            }
        }
    }
    
    private void resolveBoxBox(Box b1, Box b2) {
        if (b1.isStatic() && b2.isStatic()) return;
        
        Vector2D pos1 = b1.getPosition();
        Vector2D pos2 = b2.getPosition();
        Vector2D vel1 = b1.getVelocity();
        Vector2D vel2 = b2.getVelocity();
        
        Vector2D delta = pos1.sub(pos2);
        double absX = Math.abs(delta.getX());
        double absY = Math.abs(delta.getY());
        
        double hw1 = b1.getWidth() / 2;
        double hh1 = b1.getHeight() / 2;
        double hw2 = b2.getWidth() / 2;
        double hh2 = b2.getHeight() / 2;
        
        double overlapX = (hw1 + hw2) - absX;
        double overlapY = (hh1 + hh2) - absY;
        
        if (overlapX <= 0 || overlapY <= 0) return;
        
        Vector2D normal;
        double overlap;
        
        if (overlapX < overlapY) {
            normal = new Vector2D(delta.getX() > 0 ? 1 : -1, 0);
            overlap = overlapX;
        } else {
            normal = new Vector2D(0, delta.getY() > 0 ? 1 : -1);
            overlap = overlapY;
        }
        
        double m1 = b1.getMass();
        double m2 = b2.getMass();
        
        if (b1.isStatic() && !b2.isStatic()) {
            b2.setPosition(pos2.sub(normal.mul(overlap)));
        } else if (b2.isStatic() && !b1.isStatic()) {
            b1.setPosition(pos1.add(normal.mul(overlap)));
        } else if (!b1.isStatic() && !b2.isStatic()) {
            double total = m1 + m2;
            b1.setPosition(pos1.add(normal.mul(overlap * m2 / total)));
            b2.setPosition(pos2.sub(normal.mul(overlap * m1 / total)));
        }
        
        Vector2D relVel = vel1.sub(vel2);
        double velAlongNormal = relVel.dot(normal);
        
        if (velAlongNormal > 0) return;
        
        double e = RESTITUTION * 0.5;
        double j;
        
        if (b1.isStatic() && !b2.isStatic()) {
            j = -(1 + e) * velAlongNormal;
            b2.applyImpulse(normal.mul(-j * m2));
        } else if (b2.isStatic() && !b1.isStatic()) {
            j = -(1 + e) * velAlongNormal;
            b1.applyImpulse(normal.mul(j * m1));
        } else if (!b1.isStatic() && !b2.isStatic()) {
            j = -(1 + e) * velAlongNormal / (1/m1 + 1/m2);
            b1.applyImpulse(normal.mul(j));
            b2.applyImpulse(normal.mul(-j));
        }
    }
    
    private void handleAllBoundaries() {
        for (Coo coo : coos) {
            if (coo.isLaunch() && !coo.isDestroyed()) {
                handleEntityBoundary(coo, false);
            }
        }
        
        for (Knight knight : knights) {
            if (!knight.isDestroyed()) {
                handleEntityBoundary(knight, true);
                
                Vector2D pos = knight.getPosition();
                Vector2D vel = knight.getVelocity();
                if (pos.getY() + knight.getRadius() >= GROUND_Y - 2 && 
                    vel.getMagnitude() < MIN_VELOCITY * 2) {
                    knight.setStatic(true);
                    knight.setVelocity(new Vector2D(0, 0));
                }
            }
        }
        
        for (Box box : boxes) {
            if (!box.isDestroyed()) {
                handleBoxBoundary(box);
                
                Vector2D pos = box.getPosition();
                Vector2D vel = box.getVelocity();
                if (pos.getY() + box.getHeight() / 2 >= GROUND_Y - 2 && 
                    vel.getMagnitude() < MIN_VELOCITY * 2) {
                    box.setStatic(true);
                    box.setVelocity(new Vector2D(0, 0));
                }
            }
        }
    }
    
    private void handleEntityBoundary(Entity entity, boolean canSetStatic) {
        Vector2D pos = entity.getPosition();
        Vector2D vel = entity.getVelocity();
        double radius = entity.getRadius();
        
        if (pos.getY() + radius >= GROUND_Y) {
            entity.setPosition(new Vector2D(pos.getX(), GROUND_Y - radius));
            if (vel.getY() > 0) {
                vel = new Vector2D(vel.getX() * FRICTION, -vel.getY() * RESTITUTION);
                entity.setVelocity(vel);
            }
        }
        
        if (pos.getX() - radius <= WALL_LEFT) {
            entity.setPosition(new Vector2D(WALL_LEFT + radius, pos.getY()));
            if (vel.getX() < 0) {
                vel = new Vector2D(-vel.getX() * RESTITUTION, vel.getY());
                entity.setVelocity(vel);
            }
        }
        
        if (pos.getX() + radius >= WALL_RIGHT) {
            entity.setPosition(new Vector2D(WALL_RIGHT - radius, pos.getY()));
            if (vel.getX() > 0) {
                vel = new Vector2D(-vel.getX() * RESTITUTION, vel.getY());
                entity.setVelocity(vel);
            }
        }
    }
    
    private void handleBoxBoundary(Box box) {
        Vector2D pos = box.getPosition();
        Vector2D vel = box.getVelocity();
        double halfH = box.getHeight() / 2;
        
        if (pos.getY() + halfH >= GROUND_Y) {
            box.setPosition(new Vector2D(pos.getX(), GROUND_Y - halfH));
            if (vel.getY() > 0) {
                vel = new Vector2D(vel.getX() * FRICTION, -vel.getY() * RESTITUTION);
                box.setVelocity(vel);
            }
        }
    }
    
    private void cleanup() {
        knights.removeIf(k -> k.isDestroyed());
        boxes.removeIf(b -> b.isDestroyed());
    }
    
    private void checkSupport() {
        for (Box box : boxes) {
            if (box.isDestroyed() || !box.isStatic()) continue;
            
            if (!hasSupport(box)) {
                box.setStatic(false);
                box.setVelocity(new Vector2D(0, 10));
            }
        }
        
        for (Knight knight : knights) {
            if (knight.isDestroyed() || !knight.isStatic()) continue;
            
            if (!hasSupportKnight(knight)) {
                knight.setStatic(false);
                knight.setVelocity(new Vector2D(0, 10)); 
            }
        }
    }
    
    private boolean hasSupport(Box box) {
        Vector2D pos = box.getPosition();
        double halfH = box.getHeight() / 2;
        double halfW = box.getWidth() / 2;
        
        if (pos.getY() + halfH >= GROUND_Y - 5) {
            return true;
        }
        
        for (Box other : boxes) {
            if (other == box || other.isDestroyed()) continue;
            
            Vector2D otherPos = other.getPosition();
            double otherHalfH = other.getHeight() / 2;
            double otherHalfW = other.getWidth() / 2;

            double overlapNeeded = Math.min(halfW, otherHalfW) * 0.3;
            double horizontalDist = Math.abs(pos.getX() - otherPos.getX());
            boolean xOverlap = horizontalDist < (halfW + otherHalfW - overlapNeeded);

            double boxBottom = pos.getY() + halfH;
            double otherTop = otherPos.getY() - otherHalfH;
            double gap = boxBottom - otherTop;
            boolean isBelow = gap >= -5 && gap <= 15;
            
            if (xOverlap && isBelow) {
                return true;
            }
        }

        for (Knight knight : knights) {
            if (knight.isDestroyed()) continue;
            
            Vector2D knightPos = knight.getPosition();
            double knightRadius = knight.getRadius();
            
            double horizontalDist = Math.abs(pos.getX() - knightPos.getX());
            boolean xOverlap = horizontalDist < (halfW + knightRadius - 5);
            
            double boxBottom = pos.getY() + halfH;
            double knightTop = knightPos.getY() - knightRadius;
            double gap = boxBottom - knightTop;
            boolean isBelow = gap >= -5 && gap <= 15;
            
            if (xOverlap && isBelow) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean hasSupportKnight(Knight knight) {
        Vector2D pos = knight.getPosition();
        double radius = knight.getRadius();
        
        if (pos.getY() + radius >= GROUND_Y - 5) {
            return true;
        }
        
        for (Box box : boxes) {
            if (box.isDestroyed()) continue;
            
            Vector2D boxPos = box.getPosition();
            double boxHalfH = box.getHeight() / 2;
            double boxHalfW = box.getWidth() / 2;
            
            double horizontalDist = Math.abs(pos.getX() - boxPos.getX());
            boolean xOverlap = horizontalDist < (radius + boxHalfW - 5);
            
            double knightBottom = pos.getY() + radius;
            double boxTop = boxPos.getY() - boxHalfH;
            double gap = knightBottom - boxTop;
            boolean isBelow = gap >= -5 && gap <= 15;
            
            if (xOverlap && isBelow) {
                return true;
            }
        }
        
        for (Knight other : knights) {
            if (other == knight || other.isDestroyed()) continue;
            
            Vector2D otherPos = other.getPosition();
            double otherRadius = other.getRadius();

            double horizontalDist = Math.abs(pos.getX() - otherPos.getX());
            boolean xOverlap = horizontalDist < (radius + otherRadius - 5);
            
            double knightBottom = pos.getY() + radius;
            double otherTop = otherPos.getY() - otherRadius;
            double gap = knightBottom - otherTop;
            boolean isBelow = gap >= -5 && gap <= 15;
            
            if (xOverlap && isBelow) {
                return true;
            }
        }
        
        return false;
    }
}