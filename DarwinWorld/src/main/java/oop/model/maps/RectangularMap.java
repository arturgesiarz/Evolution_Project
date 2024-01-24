package oop.model.maps;

import oop.model.Vector2d;
import oop.model.WorldElement;
import oop.model.util.MapParameters;

import java.util.Optional;

public class RectangularMap extends AbstractWorldMap {

    public RectangularMap(MapParameters mapParameters){
        super(mapParameters);
    }

    @Override
    public Optional <WorldElement> objectAt(Vector2d position) {
        //
        if ( super.isOccupied(position) ) {
            return Optional.of( animals.get( position ).get(0) );
        }

        else if ( foodMap.containsKey(position) ) {
            return Optional.of( foodMap.get( position ) );
        }

        return Optional.empty();
    }

}
