package oop.model;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;

import java.util.List;

public class Animal implements WorldElement{
    private MapDirection directionFaced;
    private Vector2d position;
    private int energyAmount;
    private GenesHandler genesHandler;

    public Animal(Vector2d position, GenesHandler genesHandler) {
        this.position = position;
        this.genesHandler = genesHandler;
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
        this.energyAmount = this.energyAmount + food.getEnergyRegeneratedByEat();
    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public GenesHandler getGenesHandler() {
        return this.genesHandler;
    }



}
