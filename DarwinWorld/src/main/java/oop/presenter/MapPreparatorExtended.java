package oop.presenter;

import oop.model.maps.MapWithHoles;
import oop.model.util.MapParameters;

public class MapPreparatorExtended extends MapPreparatorAbstract {
    //
    private final MapWithHoles map;

    public MapPreparatorExtended(MapParameters mapParameters) {
        super(mapParameters);
        map = new MapWithHoles(mapParameters);
        super.putRandomGrass(map);
    } // end constructor



    public MapWithHoles getMap() {
        return map;
    }

}
