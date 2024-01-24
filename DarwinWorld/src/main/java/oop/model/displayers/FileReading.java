package oop.model.displayers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.stream.Stream;

public class FileReading {
    //
    private List < List<Integer> > splittedList = new ArrayList<>();
    private final UUID mapID;

    public FileReading(UUID mapID) {
        this.mapID = mapID;
    }

    public void convertValuesCSV() {
        //
        String csvFileName = "graph/map_" + mapID + ".csv";

        try (Scanner reader = new Scanner(new FileReader(csvFileName) ) ) {

            String line;
            while ( reader.hasNextLine() ) {
                //
                line = reader.nextLine();
                String[] splittedString = line.split(" ");
                splittedList.add( Stream.of( splittedString ).map(Integer::parseInt).toList() );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List < List <Integer> > getSplittedList() { return splittedList; }
}
