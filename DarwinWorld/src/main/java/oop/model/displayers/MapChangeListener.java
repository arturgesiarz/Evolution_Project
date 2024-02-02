package oop.model.displayers;

import oop.model.maps.WorldMap;

@FunctionalInterface
public interface MapChangeListener {
    void mapChanged(WorldMap worldMap, String message);
}
