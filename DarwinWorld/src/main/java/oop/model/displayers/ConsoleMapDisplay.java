package oop.model.displayers;

import oop.model.maps.WorldMap;

public class ConsoleMapDisplay implements MapChangeListener{
    @Override
    synchronized public void mapChanged(WorldMap worldMap, String message) {
        System.out.println(message);
        System.out.println(worldMap);
    }
}

