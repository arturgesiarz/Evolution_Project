package oop;
import oop.model.Animal;

public class Simulation{
    private final List<Animal> animalsList;
    

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
