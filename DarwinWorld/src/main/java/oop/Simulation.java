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
    private boolean isSimulationStopped;
    private boolean isSimulationEnded;
    private final Object pauseLock = new Object();
    private final WorldMap animalsMap;
    private final List<? extends GenesHandler> animalsGenes;
    private final GlobalStats globalStats;
    private int evolutionTime = 1;

    public Simulation(List <Vector2d> positions, List <? extends GenesHandler> animalsGenes, WorldMap animalsMap ){
        this.animalsMap = animalsMap;
        this.animalsGenes = animalsGenes;
        this.globalStats  = new GlobalStats(animalsMap);
        fillAnimalsList( positions );
        isSimulationStopped = false;
        isSimulationEnded = false;
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
        while ( animalsMap.countAliveAnimals() > 0 || !isSimulationEnded){
            // uwzlgednienie oczekwiania
            synchronized (pauseLock){
                if (isSimulationStopped){
                    try {
                        pauseLock.wait();
                    } catch (InterruptedException e){
                        return;
                    }
                }
            }

            removeDeadAnimals();
            moveAllAnimals();
            eatAllAnimals();
            reproductionOfAnimals();
            growNewFood();
            globalStats.updateAllStats();
            evolutionTime++;

            // wybudzamy wszystkie watki
            animalsMap.mapChanged("");

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        // wybudzamy wszystkie watki
        animalsMap.mapChanged("");
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
        synchronized (pauseLock){
            isSimulationStopped = false;
            pauseLock.notify();
        }
    }
    public void pauseSimulation() {
        isSimulationStopped = true;
    }
    public void stopSimulation(){
        isSimulationEnded = true;

    }

}
