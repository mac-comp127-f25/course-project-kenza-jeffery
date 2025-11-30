public enum KnightType {
    basicKnight(50, 0, 3),
    explosiveKnight(50, 20, 5),
    heavyKnight(100, 0, 8);

    private double hp;
    private double specialValue;
    private double mass;

    KnightType(double hp, double specialValue, double mass){
        this.hp = hp;
        this.specialValue = specialValue;
        this.mass = mass;
    }

    public double getHp(){
        return hp;
    }

    public double getSpecialValue(){
        return specialValue;
    }

    public double getMass(){
        return mass;
    }
}
