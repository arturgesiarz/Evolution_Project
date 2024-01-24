package oop;

import oop.model.*;
import oop.model.displayers.ConsoleMapDisplay;
import oop.model.displayers.FileMapDisplay;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.maps.MapWithHoles;
import oop.model.maps.RectangularMap;
import oop.model.simulation.Simulation;
import oop.model.util.MapParameters;
import org.junit.jupiter.api.Test;

import java.util.List;

class SimulationTest {

    @Test
    void run() {
        List<Vector2d> animalPositions = List.of(
                new Vector2d(0,0),
                new Vector2d(7,2));

        List <GenesHandler> animalGenes = List.of(
                new GenesBasic(List.of(0,1,2,3,4)),
                new GenesBasic(List.of(6,6,3,2,1)));

        MapParameters mapParameters = new MapParameters
                        (20,
                        20,
                        1,
                        1,
                        3,
                        5,
                        4,
                        15,
                        5,
                        5,
                        1,
                        2,
                        1,
                        5);

        RectangularMap map = new RectangularMap(mapParameters);
        map.addObserver(new ConsoleMapDisplay());
        map.addObserver(new FileMapDisplay());

        Simulation simulation = new Simulation(animalPositions, animalGenes, map);
        simulation.run();
    }


    @Test
    void mapWithHolesTesting() {
        //
        List<Vector2d> animalPositions = List.of(
                new Vector2d(0,0));

        List <GenesHandler> animalGenes = List.of(
                new GenesBasic(List.of(0,1,3,5,2)));

        MapParameters mapParameters = new MapParameters
                        (5,
                        5,
                        0,
                        10,
                        0,
                        0,
                        4,
                        5,
                        3,
                        3,
                        1,
                        2,
                        0,
                        5);

        MapWithHoles map = new MapWithHoles(mapParameters);
        map.addObserver(new ConsoleMapDisplay());
        map.addObserver(new FileMapDisplay());

        Simulation simulation = new Simulation(animalPositions, animalGenes, map);
        simulation.run();
    }




}