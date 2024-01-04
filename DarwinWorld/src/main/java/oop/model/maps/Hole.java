package oop.model.maps;

import oop.model.Vector2d;
import oop.model.WorldElement;

public class Hole {
    private Vector2d firstPosition;
    private Vector2d secondPosition;

    public Hole(Vector2d firstPosition, Vector2d secondPosition) {
        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
    }
    public Vector2d getFirstPosition() {
        return firstPosition;
    }
    public Vector2d getSecondPosition() {
        return secondPosition;
    }

}
