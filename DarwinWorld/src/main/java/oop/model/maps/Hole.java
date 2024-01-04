package oop.model.maps;

import oop.model.Vector2d;

public class Hole {
    private Vector2d entrance;
    private Vector2d exit;

    public Hole(Vector2d entrance, Vector2d exit) {
        this.entrance = entrance;
        this.exit = exit;
    }
    public Vector2d getEntrance() {
        return entrance;
    }
    public Vector2d getExit() {
        return exit;
    }

}
