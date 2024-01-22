package oop.model.maps;

import oop.model.Vector2d;
import oop.model.WorldElement;

public class Hole implements WorldElement {
    //
    private final Vector2d entrance;
    private final Vector2d exit;

    public Hole(Vector2d entrance, Vector2d exit) {
        this.entrance = entrance;
        this.exit = exit;
    }

    public Vector2d getEntrance() {
        return entrance;
    }

    public Vector2d getExit() { return exit; }

    @Override
    public Vector2d getPosition() {
        return entrance;
    }

    @Override
    public String toString() {
        return "O";
    }

    @Override
    public String getFileName() {
        return null;
    }

}
