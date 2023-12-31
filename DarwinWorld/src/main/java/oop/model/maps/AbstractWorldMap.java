package oop.model.maps;

import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;

import java.util.Map;

public class AbstractWorldMap implements WorldMap {
    //
    protected Map <Vector2d, Animal> animals;



    @Override
    public void place(Animal animal) {
        animals.put( animal.getPosition(), animal );

    }

    @Override
    public void move(Animal animal, MapDirection direction) {

    }

    @Override
    public void removeDeadAnimal(Animal animal) {

    }
}
