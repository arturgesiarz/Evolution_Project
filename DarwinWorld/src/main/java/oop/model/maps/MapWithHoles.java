package oop.model.maps;
import oop.model.RandomHolesGenerator;
import oop.model.RandomPositionGenerator;
import oop.model.Vector2d;
import oop.model.WorldElement;
import oop.model.util.MapParameters;

import java.util.*;

public class MapWithHoles extends AbstractWorldMap {
    private final Map <Vector2d, Hole> holes = new HashMap<>();

    public MapWithHoles(MapParameters mapParameters) {
        super(mapParameters);
        int holesToGenerate = (int) (mapParameters.width() / 2 * 0.4);
        generateHoles(holesToGenerate);
    }

    public synchronized void generateHoles(int numberOfHoles) {
        RandomHolesGenerator random = new RandomHolesGenerator(numberOfHoles, this.getLowerLeft(), this.getUpperRight(), this);
        List <Hole> holesList = random.getRandomHoles();
        holesList.forEach( hole -> holes.put(hole.getEntrance(), hole) );
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
        //
        if ( super.isOccupied(position) ) {
            return Optional.of( animals.get(position).get(0) );
        }

        else if( foodMap.containsKey(position) ){
            return Optional.of( foodMap.get(position) );
        }

        return Optional.ofNullable( holes.get(position) );
    }

    @Override
    synchronized public Map<Vector2d,List<WorldElement>> createElements(){
        Map<Vector2d, List<WorldElement>> elements = super.createElements();

        for(Vector2d position : holes.keySet()){
            if(!elements.containsKey(position)){
                elements.put(position, new ArrayList<>());
                elements.get(position).add(holes.get(position));
            }
            else{
                elements.get(position).add(holes.get(position));
            }

        }
        return elements;

    }
}

