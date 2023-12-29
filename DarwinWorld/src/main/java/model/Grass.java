package model;

public class Grass implements Food{
    private static final String GRASS_SYMBOL = "*";
    private final String foodName;
    private final Vector2d position;
    public Grass(Vector2d position, String foodName){
        this.position = position;
        this.foodName = foodName;
    }
    @Override
    public String getFoodName(){
        return foodName;
    }
    @Override
    public Vector2d getFoodPosition() {
        return position;
    }
    @Override
    public String toString(){
        return GRASS_SYMBOL;
    }
}
