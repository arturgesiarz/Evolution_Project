package oop.model.maps;

import oop.model.Animal;
import oop.model.Vector2d;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height){
        super(width, height);
    }

    @Override
    public Vector2d teleportation(Vector2d position) {
        return null;
    }
}
