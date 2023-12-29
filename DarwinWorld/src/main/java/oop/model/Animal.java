package oop.model;
import oop.model.genes.GenesHandler;

public class Animal implements WorldElement{
    private MapDirection directionFaced;
    private Vector2d position;
    private int energyAmount; //todo dodac paramtery startowe
    private GenesHandler genes;
    public Animal(Vector2d position){
        this.position = position;
    }
    public Vector2d getPosition() {
        return position;
    }
    public int getEnergyAmount(){
        return energyAmount;
    }
    public MapDirection getDirectionFaced(){
        return directionFaced;
    }
    void eat(Food food) {
        //todo uzupelnic ile zwierzak regeneruje energii po zjedzeniu
    }


}
