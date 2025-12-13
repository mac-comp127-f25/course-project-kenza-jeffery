public enum CooType {
    regularCoo(50, 0, 2, 2000),
    speedyCoo(100, 0, 1, 2500),
    explosiveCoo(50, 30, 4, 2500),
    splitterCoo(50, 3, 3, 3000);

    private double speed;
    private double specialValue;
    private double mass;
    private double score;

    CooType(double speed, double specialValue, double mass, double score){
        this.speed = speed;
        this.specialValue = specialValue;
        this.mass = mass;
        this.score = score;
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

    public double getScore(){
        return score;
    }
}
