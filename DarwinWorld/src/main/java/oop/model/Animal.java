package oop.model;
import oop.model.genes.GenesHandler;
import oop.model.maps.MoveValidator;

import java.util.Optional;

public class Animal implements WorldElement{
    private MapDirection directionFaced;
    private Vector2d position;
    private int energyAmount;
    private final GenesHandler genesHandler;

    private final AnimalStats animalStats;

    private Animal leftParent  = null;
    private Animal rightParent = null;

    // Gdy dziecko się rodzi, wykorzystujemy ten konstruktor. Musimy znać rodziców, by później móc aktualizować
    // ilość potomków (nie tylko dzieci) danego zwierzaka
    public Animal(Animal leftParent, Animal rightParent, GenesHandler genesHandler) {
        this( leftParent.getPosition(), genesHandler );
        this.leftParent  = leftParent;
        this.rightParent = rightParent;

        leftParent.getAnimalStats().updateAncestorsAmount();
        rightParent.getAnimalStats().updateAncestorsAmount();


        // TODO nie wiem jeszcze, gdzie będziemy aktualizować te informacje.
        // albo w miejscu, gdzie tworzymy nowego zwierzaka, albo tutaj.
        // trzeba jeszcze dodać aktualizacje ilości dzieci rodziców, wywołując np. leftParent.getAnimalStats().updateChildAmount();
        // dodatkowo ZMNIEJSZ ENERGIĘ RODZICÓW, np. leftParent.getAnimalsStats().decreaseEnergyAmount(amount);
        // TODO !!!!!!!!!!!!!!!

    }

    public Animal(Vector2d position, GenesHandler genesHandler) {
        this.position = position;
        this.genesHandler = genesHandler;
        this.directionFaced = MapDirection.NORTH;  // zakladam ze kazde zwierze poczatkowo patrzy na polnoc
        this.animalStats = new AnimalStats(this);
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

    public AnimalStats getAnimalStats() {
        return animalStats;
    }

    public Optional <Animal> getLeftParent() {
        return Optional.ofNullable( leftParent );
    }

    public Optional <Animal> getRightParent() {
        return Optional.ofNullable( rightParent );
    }
}
