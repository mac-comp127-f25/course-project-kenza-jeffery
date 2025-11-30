public enum MaterialType{
    WOOD(50), 
    STONE(120), 
    ICE(30);

    private int hp;

    MaterialType(int hp){
        this.hp = hp;
    }

    public int getHp(){
        return hp;
    }
}