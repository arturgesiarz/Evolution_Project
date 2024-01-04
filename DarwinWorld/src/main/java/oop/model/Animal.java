package oop.model;
import oop.model.genes.GenesHandler;

public class Animal implements WorldElement{
    private MapDirection directionFaced;
    private Vector2d position;
    private int energyAmount;
    private GenesHandler genesHandler;

    public Animal(Vector2d position, GenesHandler genesHandler) {
        this.position = position;
        this.genesHandler = genesHandler;
        this.directionFaced = MapDirection.NORTH;  // zakladam ze kazde zwierze poczatkowo patrzy na polnoc
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

    public void eat(Food food) {
        this.energyAmount = this.energyAmount + food.getEnergyRegeneratedByEat();
    }
    public void move(){
        // najpiew nastepuje obrot o dany nastepny gen
        int nextGene = this.genesHandler.getNextMove();
        this.directionFaced = MapDirection.values()[nextGene];

        // teraz nastepuje pojscie w danym kierunku
        Vector2d newPosition = this.position.addVector(this.directionFaced.toUnitVector());  // zapisuje nowa pozycje, bo jeszcze musze ja sprawdzic

    }

    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public GenesHandler getGenesHandler() {
        return this.genesHandler;
    }



}
