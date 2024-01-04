package oop.model.maps;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected Map <Vector2d, Animal> wordMap = new HashMap<>();
    protected Vector2d lowerLeft;  // lewy dolny rog mapy
    protected Vector2d upperRight;  // prawy gorny rog mapy

    public AbstractWorldMap(int width, int height){
        lowerLeft = new Vector2d(0,0);
        upperRight = new Vector2d(width - 1,height - 1);
    }

    @Override
    public void place(Animal animal) { //TODO
        wordMap.put( animal.getPosition(), animal );
    }
    @Override
    public void move(Animal animal, MapDirection direction) { //TODO

    }
    @Override
    public void removeDeadAnimal(Animal animal) { //TODO
    }
}
