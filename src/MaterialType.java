public enum MaterialType{
    WOOD(25, 3, 500), 
    STONE(50, 6, 1000), 
    ICE(15, 1, 300);

    private int hp;
    private double mass;
    private double score;

    MaterialType(int hp, double mass, double score){
        this.hp = hp;
        this.mass = mass;
        this.score = score;
    }

    public int getHp(){
        return hp;
    }

    public double getMass(){
        return mass;
    }

    public double getScore(){
        return score;
    }
}