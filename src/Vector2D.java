public class Vector2D {
    private double x;
    private double y;
    
    public Vector2D(double x, double y){
        this.x = x;
        this.y = y;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double newX){
        x = newX;
    }

    public void setY(double newY){
        y = newY;
    }

    public Vector2D add(Vector2D vector){
        Vector2D newVector = new Vector2D(0, 0);
        newVector.setX(x + vector.getX());
        newVector.setY(y + vector.getY());
        return newVector;
    }

    public Vector2D sub(Vector2D vector){
        Vector2D newVector = new Vector2D(0, 0);
        newVector.setX(x - vector.getX());
        newVector.setY(y - vector.getY());
        return newVector;
    }

    public Vector2D mul(double c){
        Vector2D newVector = new Vector2D(c * x, c * y);
        return newVector;
    }

    public double dot(Vector2D vector){
        return x * vector.getX() + y * vector.getY();
    }

    public double getMagnitude(){
        return Math.sqrt(x * x + y * y);
    }

    public Vector2D normalize(){
        double length = this.getMagnitude();
        if(length == 0){
            return new Vector2D(0, 0);
        } else{
            return new Vector2D(x / length, y / length);
        }
    }
}
