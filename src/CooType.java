public enum CooType {
    regularCoo(50, 0, 2),
    speedyCoo(100, 0, 1),
    explosiveCoo(50, 30, 4),
    splitterCoo(50, 3, 3);

    private double speed;
    private double specialValue;
    private double mass;

    CooType(double speed, double specialValue, double mass){
        this.speed = speed;
        this.specialValue = specialValue;
        this.mass = mass;
    }

    public double getSpeed(){
        return speed;
    }

    public double getSpecialValue(){
        return specialValue;
    }

    public double getMass(){
        return mass;
    }
}
