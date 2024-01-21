package oop.model.maps;
import oop.model.Vector2d;
import oop.model.WorldElement;
import oop.model.util.MapParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapWithHoles extends AbstractWorldMap {
    private Map <Vector2d, Hole> holes = new HashMap<>();  // trzeba tutaj podczas generowania dziur pamietac o dwoch stronach

    public MapWithHoles(int width, int height, MapParameters mapParameters, int numberOfHoles) {
        super(width, height, mapParameters);
        this.holes = generateHoles(numberOfHoles);
    }

    public Map <Vector2d, Hole> generateHoles(int numberOfHoles){
        //todo zrobic dziury
        return new HashMap<>();
    }

    @Override
    public Vector2d teleportation(Vector2d position) {  // chce rozszerzyc tą metode poniewaz moze byc tak ze jestem na dziurze
        Vector2d positionTest = super.teleportation(position);

        // sprawdzam czy jest dziura na danym miejscu
        if(holes.containsKey(positionTest)){
                return holes.get(positionTest).getExit();
        }

        return positionTest;
    }

    @Override
    public Optional <WorldElement> objectAt(Vector2d position) {

        return Optional.ofNullable(null);
    } // TODO do dokończenia!
}

