package oop.model.maps;
import oop.model.*;
import oop.model.util.MapParameters;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WorldMap extends MoveValidator {
    void place(Animal animal);

    void move(Animal animal);

    Optional <WorldElement> objectAt(Vector2d position);

    Map <Vector2d, List<Animal>> getAnimals();

    MapParameters getMapParameters();

    Map <Vector2d, Grass> getFoodMap();

    Vector2d getLowerLeft();

    Vector2d getUpperRight();

    int countAliveAnimals();
}
