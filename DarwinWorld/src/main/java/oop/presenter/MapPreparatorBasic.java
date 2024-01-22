package oop.presenter;

import oop.model.maps.RectangularMap;
import oop.model.util.MapParameters;

public class MapPreparatorBasic extends MapPreparatorAbstract {
    //
    private final RectangularMap map;

    public MapPreparatorBasic(MapParameters mapParameters) {
        super(mapParameters);
        map = new RectangularMap(mapParameters);
        super.putRandomGrass(map);
    } // end constructor


    public RectangularMap getMap() {
        return map;
    }

}
