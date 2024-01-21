package oop;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.maps.WorldMap;

import java.util.List;

public class Simulation{
    //
    private final List<Animal> animalsList;
    private final WorldMap animalsMap;
    private final List <MapDirection> directions;

    private int evolutionTime = 1;

    public Simulation(List <Vector2d> positions ){

    }
    public void run() {
        removeDeadAnimals();
        moveAllAnimals();
        reproductionOfAnimals();
        growNewFood();
        evolutionTime++;
    }

    private void removeDeadAnimals() {

    }

    private void moveAllAnimals() {

    }

    private void reproductionOfAnimals() {

    }

    private void growNewFood() {

    }


}
