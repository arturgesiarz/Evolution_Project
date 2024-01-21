package oop;

import oop.model.Animal;
import oop.model.ConsoleMapDisplay;
import oop.model.Vector2d;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
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
                new Vector2d(0,2));

        List <GenesHandler> animalGenes = List.of(
                new GenesBasic(List.of(0,1,2,3,4)),
                new GenesBasic(List.of(4,1,2,4,3)));

        MapParameters mapParameters = new MapParameters
                (10,
                        10,
                        1,
                        6,
                        3,
                        1,
                        4,
                        10,
                        2,
                        3,
                        1,
                        2,
                        1,
                        5);

        RectangularMap map = new RectangularMap(10, 10, mapParameters);
        map.addObserver(new ConsoleMapDisplay());

        Simulation simulation = new Simulation(animalPositions, animalGenes, map);
        simulation.run();
    }
    @Test
    void reproduce() {

    }

}