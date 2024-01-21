package oop;
import oop.model.Animal;
import oop.model.Vector2d;
import oop.model.genes.GenesHandler;
import oop.model.maps.MapUtil;
import oop.model.maps.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation{
    //
    private final List<Animal> animalsList = new ArrayList<>();
    private final WorldMap animalsMap;
    private final List<GenesHandler> animalsGenes;
    private int evolutionTime = 1;

    public Simulation(List <Vector2d> positions, List <GenesHandler> animalsGenes, WorldMap animalsMap ){
        //
        this.animalsMap = animalsMap;
        this.animalsGenes = animalsGenes;
        fillAnimalsList( positions );
    }

    private void fillAnimalsList( List <Vector2d> positions ) {
        //
        int counter = 0;
        for( Vector2d position : positions ) {
            Animal newAnimal = new Animal( position,  animalsMap.getMapParameters().startEnergy(), animalsGenes.get(counter) );
            animalsMap.place( newAnimal );
            animalsList.add(  newAnimal );
            counter++;
        }

    }

    public void run() {
        removeDeadAnimals();
        moveAllAnimals();
        eatAllAnimals();
        reproductionOfAnimals();
        growNewFood();
        evolutionTime++;
    }

    private void removeDeadAnimals() {
        MapUtil.removeDeadAnimals( animalsMap, evolutionTime );
    }

    private void moveAllAnimals() {
        animalsList.forEach(animalsMap::move);
    }

    private void eatAllAnimals(){
        MapUtil.fightForFood(animalsMap);
    }

    private void reproductionOfAnimals() {
        MapUtil.fightForReproduction( animalsMap );
    }

    private void growNewFood() {
        MapUtil.growNewGrass( animalsMap );
    }


}
