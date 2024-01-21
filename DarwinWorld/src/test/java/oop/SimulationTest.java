package oop;

import oop.model.*;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.maps.MapWithHoles;
import oop.model.maps.RectangularMap;
import oop.model.maps.WorldMap;
import oop.model.util.MapParameters;
import oop.model.util.MapVisualizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

        RectangularMap map = new RectangularMap(15, 15, mapParameters);
        map.addObserver(new ConsoleMapDisplay());
        map.addObserver(new FileMapDisplay());

        Simulation simulation = new Simulation(animalPositions, animalGenes, map);
        simulation.run();
    }


    @Test
    void mapWithHolesTesting() {
        //
        List<Vector2d> animalPositions = List.of(
                new Vector2d(0,0),
                new Vector2d(7,2));

        List <GenesHandler> animalGenes = List.of(
                new GenesBasic(List.of(0,1,2,3,4)),
                new GenesBasic(List.of(6,6,3,2,1)));

        MapParameters mapParameters = new MapParameters
                        (10,
                        10,
                        1,
                        1,
                        1,
                        5,
                        4,
                        10,
                        3,
                        6,
                        1,
                        2,
                        1,
                        5);

        MapWithHoles map = new MapWithHoles(15, 15, mapParameters, 3);
        map.addObserver(new ConsoleMapDisplay());
        map.addObserver(new FileMapDisplay());

        Simulation simulation = new Simulation(animalPositions, animalGenes, map);
        simulation.run();
    }




}