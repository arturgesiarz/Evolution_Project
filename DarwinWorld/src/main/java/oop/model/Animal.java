package oop.model;
import oop.model.genes.GenesHandler;
import oop.model.maps.MoveValidator;
import oop.model.util.MapParameters;

import java.util.Optional;
import java.util.Random;

public class Animal implements WorldElement{
    private MapDirection directionFaced;
    private Vector2d position;
    private final GenesHandler genesHandler;
    private final AnimalStats animalStats;
    private Animal leftParent  = null;
    private Animal rightParent = null;

    public Animal(Animal leftParent, Animal rightParent, GenesHandler genesHandler, int energyLostInCopulation) {
        this( leftParent.getPosition(), 2 * energyLostInCopulation ,genesHandler );
        this.leftParent  = leftParent;
        this.rightParent = rightParent;

        leftParent.getAnimalStats().updateDescendantsAmount();
        rightParent.getAnimalStats().updateDescendantsAmount();

        leftParent.getAnimalStats().updateChildAmount();
        rightParent.getAnimalStats().updateChildAmount();
    }

    public Animal(Vector2d position, int energyAmount, GenesHandler genesHandler) {
        this.position = position;
        this.genesHandler = genesHandler;
        setDirectionFaced();
        this.animalStats = new AnimalStats(this, energyAmount);
    }
    private void setDirectionFaced(){
        int randomMapDirectionValue = new Random().nextInt(8);
        this.directionFaced = MapDirection.fromValue(randomMapDirectionValue);
    }

    public Vector2d getPosition() {
        return position;
    }

    public MapDirection getDirectionFaced(){
        return directionFaced;
    }

    public GenesHandler getGenesHandler() {
        return this.genesHandler;
    }

    public void eat(int energy) {
        this.getAnimalStats().increaseEnergyAmount(energy);
    }

    public void move(MoveValidator validator){
        // najpiew nastepuje obrot o dany nastepny gen
        int nextGene = this.genesHandler.getNextMove();
        this.directionFaced = MapDirection.values()[nextGene];

        // teraz nastepuje pojscie w danym kierunku
        Vector2d newPosition = this.position.addVector(this.directionFaced.toUnitVector());

        if(validator.isPole(newPosition)){
            // znaczy ze chcemy wejsc na biegun - czyli musimy zamienic directionFaced
            this.directionFaced = this.directionFaced.getOpositeDirection();
        }
        else{
            // znaczy ze nie chcemy wejsc na biegun - tylko albo weszlismy do dziury albo przez lewy/prawy koniec
            // albo po prostu poruszamy sie po mapie
            // teleportuje lub ide normlanie kiedy nie nastepuje podane wyzej zjawiska
            this.position = validator.teleportation(newPosition);
        }

    }

    public AnimalStats getAnimalStats() {
        return animalStats;
    }

    public Optional <Animal> getLeftParent() {
        return Optional.ofNullable( leftParent );
    }

    public Optional <Animal> getRightParent() {
        return Optional.ofNullable( rightParent );
    }

    @Override
    public String toString() {
        return switch(directionFaced) {
            case NORTH       -> "N";
            case NORTH_EAST  -> "NE";
            case EAST        -> "E";
            case SOUTH_EAST  -> "SE";
            case SOUTH       -> "S";
            case SOUTH_WEST  -> "SW";
            case WEST        -> "W";
            case NORTH_WEST  -> "NW";
        };
    }

    @Override
    public String getFileName() {
        return null;
    }
}
