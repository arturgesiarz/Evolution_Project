package oop.model.maps;

import oop.model.Vector2d;
import oop.model.WorldElement;

public class Hole implements WorldElement {
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

    @Override
    public Vector2d getPosition() {
        return new Vector2d(0,0);
    } // TODO DOKOŃCZYĆ TO

}
