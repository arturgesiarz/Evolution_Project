package oop.model.maps;

import oop.model.Animal;
import oop.model.MapDirection;

public interface WorldMap {

    void place(Animal animal);
    void move(Animal animal, MapDirection direction);
    void removeDeadAnimal(Animal animal);
}
