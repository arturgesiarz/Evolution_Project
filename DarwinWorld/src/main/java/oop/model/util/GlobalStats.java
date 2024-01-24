package oop.model.util;

import oop.model.animal.Animal;
import oop.model.maps.WorldMap;

import java.util.List;
import java.util.Map;


public class GlobalStats {
    //
    private final WorldMap map;
    private final int numberOfCells;

    private Map < List <Integer>, Integer > genotypes;

    private int animalsAmount;
    private int grassAmount;
    private int emptyCells;
    private double averageEnergyAmount;
    private double averageDeadLifeSpan;
    private double averageChildAmount;
    private int evolutionTime;
    private int numberOfDeadAnimals;
    private int sumLifeSpanDeadAnimals;


    public GlobalStats(WorldMap map) {
        this.map = map;
        this.numberOfCells = map.getUpperRight().getX() * map.getUpperRight().getY();
    }

    private int findAnimalsAmount() {
        return map.getAnimals().values()
                .stream()
                .mapToInt(List::size)
                .sum();
    } // end method findAnimalsAmount()

    private int findGrassAmount() {
        return map.getFoodMap().values().size();
    }

    private int findEmptyCells() { return numberOfCells - map.getAnimals().size(); }

    private int findSumOfAnimalsEnergy() {
        return map.getAnimals().values()
                .stream()
                .flatMap(List::stream)
                .mapToInt( animal -> animal.getAnimalStats().getEnergyAmount() )
                .sum();
    } // end method findSumOfAnimalsEnergy()

    private double findAverageChildAmount() {
        //
        int childAmount;

        childAmount = map.getAnimals().values()
                .stream()
                .flatMap(List::stream)
                .mapToInt( animal -> animal.getAnimalStats().getChildAmount() )
                .sum();

        return (double) childAmount / findAnimalsAmount();
    } // end method updateAverageChildAmount()

    private double findAverageEnergyAmount() {
        return (double) findSumOfAnimalsEnergy() / findAnimalsAmount();
    }

    private double findAverageDeadLifeSpan() {
        return (double) sumLifeSpanDeadAnimals / numberOfDeadAnimals;
    }

    public List <Integer> findMostPopularGenotype() {
        //
        genotypes.clear();
        map.getAnimals().values()
                .stream()
                .flatMap(List::stream)
                .map( animal -> animal.getGenesHandler().getGenes() )
                .forEach( genes -> genotypes.merge(genes, 1, Integer :: sum) );
        //

        List <Integer> mostPopular = null;
        int maxValue = 0;

        for ( List <Integer> key : genotypes.keySet() ) {
            if ( genotypes.get(key) < maxValue ) { continue; }
            mostPopular = key;
            maxValue    = genotypes.get(key);
        }

        return mostPopular;
    }

    public void animalDiedStats(Animal animal) {
        numberOfDeadAnimals++;
        sumLifeSpanDeadAnimals += animal.getAnimalStats().getDeathTime();
    }

    public void updateAllStats() {
        //
        averageEnergyAmount = findAverageEnergyAmount();
        averageDeadLifeSpan = findAverageDeadLifeSpan();
        averageChildAmount  = findAverageChildAmount();
        emptyCells          = findEmptyCells();
        grassAmount         = findGrassAmount();
        animalsAmount       = findAnimalsAmount();
    } // end method updateAllStats()


    public int getNumberOfCells() {
        return numberOfCells;
    }

    public int getAnimalsAmount() {
        return animalsAmount;
    }

    public int getGrassAmount() {
        return grassAmount;
    }

    public int getEmptyCells() {
        return emptyCells;
    }

    public double getAverageEnergyAmount() {
        return averageEnergyAmount;
    }

    public double getAverageDeadLifeSpan() {
        return averageDeadLifeSpan;
    }

    public double getAverageChildAmount() {
        return averageChildAmount;
    }

    public int getNumberOfDeadAnimals() {
        return numberOfDeadAnimals;
    }

    public int getSumLifeSpanDeadAnimals() {
        return sumLifeSpanDeadAnimals;
    }

    public int getEvolutionTime() {
        return evolutionTime;
    }

    public void increaseEvolutionTime() {
        evolutionTime++;
    }
}
