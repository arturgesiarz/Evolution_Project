package oop.model.util;

import oop.model.maps.WorldMap;

import java.util.List;
import java.util.Optional;


public class GlobalStats {
    //
    private WorldMap map;
    private int animalsAmount;
    private int grassAmount;
    private int emptyCells;
    private int averageEnergyAmount;
    private int averageDeadLifeSpan;
    private int averageChildAmount;


    public int findAnimalsAmount() {
        return map.getAnimals().values()
                .stream()
                .mapToInt(List::size)
                .sum();
    } // end method findAnimalsAmount()

    public int findGrassAmount() {
        return map.getFoodMap().values().size();
    }


    public double updateAverageChildAmount() {
        //
        int childAmount;

        childAmount = map.getAnimals().values()
                .stream()
                .flatMap(List::stream)
                .mapToInt( animal -> animal.getAnimalStats().getChildAmount() )
                .sum();

        return (double) childAmount / findAnimalsAmount();
    } // end method updateAverageChildAmount()



}
