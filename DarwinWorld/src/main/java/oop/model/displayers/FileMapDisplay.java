package oop.model.displayers;
import oop.model.maps.WorldMap;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileMapDisplay implements MapChangeListener{
    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        String logFileName = "logs/map_" + worldMap.getWorldMapID() + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFileName, true))) {
            // wypisuje infomracje o ruchu
            writer.write(message);
            writer.newLine();

            // wypisuje moja mape
            writer.write(worldMap.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        } // plik automatycznie sie zamknie ponieaz BufferedWriter implementuje Writer, a Writer interfejs Autocloseable :)

    }
}