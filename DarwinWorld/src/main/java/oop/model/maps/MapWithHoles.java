package oop.model.maps;
import oop.model.Vector2d;

import java.util.HashMap;
import java.util.Map;

public class MapWithHoles extends AbstractWorldMap {
    private Map <Vector2d, Hole> holes = new HashMap<>();  // trzeba tutaj podczas generowania dziur pamietac o dwoch stronach

    public MapWithHoles(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2d teleportation(Vector2d position) {  // chce rozszerzyc tą metode poniewaz moze byc tak ze jestem na dziurze
        Vector2d positionTest = super.teleportation(position);
        if(positionTest == position && holes.containsKey(positionTest)){
                return holes.get(positionTest).getExit();
        }
        return position;
    }
}

