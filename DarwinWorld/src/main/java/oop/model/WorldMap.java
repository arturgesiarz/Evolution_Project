package oop.model;

public interface WorldMap {
    void place(Animal animal);
    void move(Animal animal, MapDirection direction);
    void removeDeadAnimal(Animal animal);
}
