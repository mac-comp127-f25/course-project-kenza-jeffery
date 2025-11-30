public enum CooType {
    regularCoo(50, 0),
    speedyCoo(100, 0),
    explosiveCoo(50, 30),
    splitterCoo(50, 3);

    private double speed;
    private double specialValue;

    CooType(double speed, double specialValue){
        this.speed = speed;
        this.specialValue = specialValue;
    }

    public double getSpeed(){
        return speed;
    }

    public double getSpecialValue(){
        return specialValue;
    }
}
