package oop.model;

import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlotGraph {
    //
    private final List < List <Integer> > splittedFile;
    private final List <Integer> arguments = new ArrayList<>();
    private final List <Integer> values    = new ArrayList<>();

    public PlotGraph(UUID mapID) {
        //
        FileReading fileReading = new FileReading(mapID);
        fileReading.convertValuesCSV();
        splittedFile = fileReading.getSplittedList();
    }

    public void plotAnimalsAmount() throws PythonExecutionException, IOException {
        //
        clearAndFill(1);

        Plot plt = Plot.create();

        plt.plot().add(arguments, values);
        plt.plot().add(arguments, values, ".").color("red");

        plt.xlabel("Czas trwania symulacji");
        plt.ylabel("Liczba zwierzat");
        plt.show();
    }

    public void plotAverageEnergyAmount() throws PythonExecutionException, IOException {
        clearAndFill(2);

        Plot plt = Plot.create();

        plt.plot().add(arguments, values);
        plt.plot().add(arguments, values, ".").color("red");

        plt.xlabel("Czas trwania symulacji");
        plt.ylabel("Srednia ilosc energi");
        plt.show();
    }

    public void plotAverageChildAmount() throws PythonExecutionException, IOException {
        clearAndFill(3);

        Plot plt = Plot.create();

        plt.plot().add(arguments, values);
        plt.plot().add(arguments, values, ".").color("red");

        plt.xlabel("Czas trwania symulacji");
        plt.ylabel("Srednia ilosc dzieci");
        plt.show();
    }

    public void plotNumberOfDeadAnimals() throws PythonExecutionException, IOException {
        clearAndFill(4);

        Plot plt = Plot.create();

        plt.plot().add(arguments, values);
        plt.plot().add(arguments, values, ".").color("red");

        plt.xlabel("Czas trwania symulacji");
        plt.ylabel("Liczba martwych zwierzat");
        plt.show();
    }

    private void clearAndFill(int index) {
        arguments.clear();
        values.clear();
        splittedFile.forEach( list -> { arguments.add( list.get(index) ) ; values.add( list.get(index) ); } );
    }
}
