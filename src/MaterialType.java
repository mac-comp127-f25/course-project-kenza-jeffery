public enum MaterialType{
    WOOD(25, 3), 
    STONE(50, 6), 
    ICE(15, 1);

    private int hp;
    private double mass;

    MaterialType(int hp, double mass){
        this.hp = hp;
        this.mass = mass;
    }

    public int getHp(){
        return hp;
    }

    public double getMass(){
        return mass;
    }
}