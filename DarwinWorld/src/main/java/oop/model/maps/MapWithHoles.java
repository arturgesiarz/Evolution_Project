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
        generateHoles(numberOfHoles);
    }

    public void generateHoles(int numberOfHoles){
        Hole holeA = new Hole( new Vector2d(2, 2), new Vector2d(3, 3) );
        Hole holeB = new Hole( new Vector2d(4, 4), new Vector2d(5, 2) );
        holes.put(holeA.getEntrance(), holeA);
        holes.put(holeA.getExit(), holeA);
        holes.put(holeB.getEntrance(), holeB);
        holes.put(holeB.getExit(), holeB);
    }

    @Override
    public Vector2d teleportation(Vector2d position) {  // chce rozszerzyc tą metode poniewaz moze byc tak ze jestem na dziurze
        Vector2d positionTest = super.teleportation(position);

        if( holes.containsKey(positionTest) ){
                return holes.get(positionTest).getExit();
        }

        return positionTest;
    }

    @Override
    public Optional <WorldElement> objectAt(Vector2d position) {
        return Optional.ofNullable( holes.get(position) );
    }
}

