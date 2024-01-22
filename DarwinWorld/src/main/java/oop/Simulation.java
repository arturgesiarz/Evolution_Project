package oop;
import oop.model.Animal;
import oop.model.Vector2d;
import oop.model.genes.GenesHandler;
import oop.model.maps.MapUtil;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;

import java.util.List;

public class Simulation implements Runnable {
    //
    private final WorldMap animalsMap;
    private final List<? extends GenesHandler> animalsGenes;
    private final GlobalStats globalStats;
    private int evolutionTime = 1;

    public Simulation(List <Vector2d> positions, List <? extends GenesHandler> animalsGenes, WorldMap animalsMap ){
        this.animalsMap = animalsMap;
        this.animalsGenes = animalsGenes;
        this.globalStats  = new GlobalStats(animalsMap);
        fillAnimalsList( positions );
    } // end constructor

    private void fillAnimalsList( List <Vector2d> positions ) {
        int counter = 0;
        for( Vector2d position : positions ) {
            Animal newAnimal = new Animal( position,  animalsMap.getMapParameters().startEnergy(), animalsGenes.get(counter) );
            animalsMap.place( newAnimal );
            counter++;
        }
    }

    @Override
    public void run() {
        while ( animalsMap.countAliveAnimals() > 0 ){
            removeDeadAnimals();
            moveAllAnimals();
            eatAllAnimals();
            reproductionOfAnimals();
            growNewFood();
            globalStats.updateAllStats();
            evolutionTime++;
        }
    } // end method run()

    private void removeDeadAnimals() { MapUtil.removeDeadAnimals( animalsMap, globalStats, evolutionTime ); }

    private void moveAllAnimals() {
        MapUtil.createListAnimalFromSet(animalsMap).forEach(animalsMap::move);
    }

    private void eatAllAnimals(){
        MapUtil.fightForFood(animalsMap);
    }

    private void reproductionOfAnimals() {
        MapUtil.fightForReproduction( animalsMap );
    }

    private void growNewFood() { MapUtil.growNewGrass( animalsMap, animalsMap.getMapParameters().amountOfPlantsDaily() );
    }

    public GlobalStats getGlobalStats() { return globalStats; }

    public void unpauseSimulation() {
    }

    public void pauseSimulation() {
    }
}
