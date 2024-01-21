package oop.model.maps;
import oop.model.*;
import oop.model.util.MapParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorldMap extends MoveValidator {
    /**
     * Place animal on the map.
     *
     * @param animal The element to place on the map.
     */
    void place(Animal animal);

    /**
     * Move an animal
     * If the move is not possible, this method has no effect.
     *
     */
    void move(Animal animal);

    Optional <WorldElement> objectAt(Vector2d position);

    Map <Vector2d, List<Animal>> getAnimals();

    MapParameters getMapParameters();

    Map <Vector2d, Grass> getFoodMap();

    Vector2d getLowerLeft();

    Vector2d getUpperRight();

}
