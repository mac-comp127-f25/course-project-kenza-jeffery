public enum MaterialType{
    WOOD(50, 3), 
    STONE(120, 6), 
    ICE(30, 1);

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