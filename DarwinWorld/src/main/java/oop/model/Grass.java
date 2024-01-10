package oop.model;

public class Grass extends Food {
    private static final String GRASS_SYMBOL = "*";
    private final String foodName;
    private final Vector2d position;
    public Grass(Vector2d position, String foodName){
        this.position = position;
        this.foodName = foodName;
    }
    @Override
    public String getName(){
        return foodName;
    }

    @Override
    public void setTheAmountOfRegeneratedEnergy(int energyRegenerated) {
        super.energyRegeneratedByEat = energyRegenerated;  // ustawiam ile regeneruje punktow jedzenie wlasnie jakies
    }
    @Override
    public Vector2d getPosition() {
        return position;
    }
    @Override
    public String toString(){
        return GRASS_SYMBOL;
    }
}
