public enum DifLevel {
    first(1),
    second(2),
    third(3),
    forth(4),
    fifth(5);

    private int order;

    DifLevel(int order){
        this.order = order;
    }

    public int getOrder(){
        return order;
    }
}
