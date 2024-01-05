package oop.model.maps;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.WorldElement;

public interface WorldMap extends MoveValidator {
    /**
     * Place animal on the map.
     *
     * @param worldElement The element to place on the map.
     */
    void place(Animal animal);

    /**
     * Move an animal
     * If the move is not possible, this method has no effect.
     *
     */
    void move(Animal animal);

    /**
     * Remove dead an animal
     * If the move is not possible, this method has no effect.
     *
     */
    void removeDeadAnimal(Animal animal);

    void growNewGrass();
}
