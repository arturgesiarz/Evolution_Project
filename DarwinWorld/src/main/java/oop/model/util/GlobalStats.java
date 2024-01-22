package oop.model.util;

import oop.model.maps.WorldMap;

import java.util.Optional;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;

public class GlobalStats {
    //
    private WorldMap map;
    private int animalsAmount;
    private int grassAmount;
    private int emptyCells;
    private int averageEnergyAmount;
    private int averageDeadLifeSpan;
    private int averageChildAmount;


    public void updateAverageChildAmount() {
        //
        int childAmount;
      map.getAnimals().values()
                .stream()
                .flatMap(List::stream)
              .map()




    }
}
