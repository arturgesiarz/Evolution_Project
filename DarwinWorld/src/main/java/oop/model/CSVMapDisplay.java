package oop.model;

import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVMapDisplay implements MapChangeListener{
    //
    GlobalStats globalStats;

    public CSVMapDisplay(GlobalStats globalStats) {
        this.globalStats = globalStats;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        //
        String csvFileName = "graph/map_" + worldMap.getWorldMapID() + ".csv";

        try (FileWriter writer = new FileWriter(csvFileName, true)) {

            writer.write( globalStats.getEvolutionTime() + " " );
            writer.write( globalStats.getAnimalsAmount() + " " );
            writer.write( globalStats.getAverageEnergyAmount() + " " );
            writer.write( globalStats.getAverageChildAmount() + " " );
            writer.write( globalStats.getNumberOfDeadAnimals() + " " );
            writer.write( System.lineSeparator() );

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


