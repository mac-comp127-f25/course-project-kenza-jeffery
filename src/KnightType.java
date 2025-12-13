public enum KnightType {
    basicKnight(30, 0, 3, 1000),
    explosiveKnight(30, 20, 5, 2000),
    heavyKnight(60, 0, 8, 2500);

    private double hp;
    private double specialValue;
    private double mass;
    private double score;

    KnightType(double hp, double specialValue, double mass, double score){
        this.hp = hp;
        this.specialValue = specialValue;
        this.mass = mass;
        this.score = score;
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

    public double getScore(){
        return score;
    }
}
