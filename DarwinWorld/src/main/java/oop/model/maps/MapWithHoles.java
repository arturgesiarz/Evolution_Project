package oop.model.maps;
import oop.model.Vector2d;
import java.util.Map;

public class MapWithHoles extends AbstractWorldMap {
    private Map <Vector2d, Hole> holes;

    public MapWithHoles(int width, int height) {
        super(width, height);
    }
}

