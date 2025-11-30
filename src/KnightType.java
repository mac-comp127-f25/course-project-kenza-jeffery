public enum KnightType {
    basicKnight(50, 0),
    explosiveKnight(50, 20),
    heavyKnight(100, 0);

    private double hp;
    private double specialValue;

    KnightType(double hp, double specialValue){
        this.hp = hp;
        this.specialValue = specialValue;
    }

    public double getHp(){
        return hp;
    }

    public double getSpecialValue(){
        return specialValue;
    }
}
