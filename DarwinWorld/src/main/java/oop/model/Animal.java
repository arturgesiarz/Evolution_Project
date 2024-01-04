package oop.model;
import oop.model.genes.GenesHandler;
import oop.model.maps.MoveValidator;

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
    public GenesHandler getGenesHandler() {
        return this.genesHandler;
    }
    public void setEnergyAmount(int energyAmount) {
        this.energyAmount = energyAmount;
    }

    public void eat(Food food) {
        this.energyAmount = this.energyAmount + food.getEnergyRegeneratedByEat();
    }
    public void move(MoveValidator validator){
        // najpiew nastepuje obrot o dany nastepny gen
        int nextGene = this.genesHandler.getNextMove();
        this.directionFaced = MapDirection.values()[nextGene];

        // teraz nastepuje pojscie w danym kierunku
        Vector2d newPosition = this.position.addVector(this.directionFaced.toUnitVector());  // zapisuje nowa pozycje, bo jeszcze musze ja sprawdzic

        if(validator.isPole(newPosition)){  // znaczy ze chcemy wejsc na biegun - czyli musimy zamienic directionFaced
            this.directionFaced = this.directionFaced.getOpositeDirection();
        }
        else{  // znaczy ze nie chcemy wejsc na biegun - tylko albo weszlismy do dziury albo przez lewy/prawy koniec albo po prostu poruszamy sie po mapie
            this.position = validator.teleportation(newPosition);  // teleportuje lub ide normlanie kiedy nie nastepuje podane wyzej zjawiska
        }

    }

}
