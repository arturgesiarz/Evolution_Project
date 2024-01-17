package oop;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.genes.AbstractGenesHandler;
import oop.model.genes.GenesHandler;
import oop.model.maps.WorldMap;
import java.util.ArrayList;
import java.util.List;

public class Simulation{
    private int evolutionTime = 1;

    public Simulation(){
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
