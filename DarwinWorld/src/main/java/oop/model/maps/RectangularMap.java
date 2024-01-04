package oop.model.maps;

import oop.model.Animal;
import oop.model.Vector2d;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(int width, int height){
        super(width, height);
    }

    @Override
    public Vector2d teleportation(Vector2d position) {
        // najpiew musimy sprawdzic czy nie chcemy wyjsc poza lewy/prawy koniec
        int leftCorner = super.lowerLeft.getX();
        int rightCorner = super.upperRight.getX();

        if(position.getX() < leftCorner){  // znaczy ze chce wyjsc poza mape z lewej strony
            return new Vector2d(rightCorner, position.getY());
        }
        if(position.getX() > rightCorner){  // znaczy ze chce wyjsc poza mape z prawej strony
            return new Vector2d(leftCorner, position.getY());
        }
        return position;  // nie chce wyjsc poza mape wiec jest ok
    }
}
