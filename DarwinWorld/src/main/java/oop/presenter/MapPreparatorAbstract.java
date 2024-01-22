package oop.presenter;

import oop.model.Grass;
import oop.model.Vector2d;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesExtended;
import oop.model.maps.WorldMap;
import oop.model.util.MapParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class MapPreparatorAbstract {
    //
    MapParameters mapParameters;

    public MapPreparatorAbstract(MapParameters mapParameters) {
        this.mapParameters = mapParameters;
    }

    public List<GenesBasic> getBasicGenesList() {
        return randomGenesList().stream().map( GenesBasic :: new ).toList();
    }

    public List <GenesExtended> getExtendedGenesList() {
        return randomGenesList().stream().map( GenesExtended :: new ).toList();
    }

    private List < List <Integer> > randomGenesList() {
        List < List <Integer> > genesList = Collections.nCopies( mapParameters.initialNumberOfAnimals(), null);
        return genesList.stream().map( number -> randomGenes() ).toList();
    }

    private List <Integer> randomGenes() {
        List <Integer> genes = Collections.nCopies( mapParameters.genomeLength(), 0 );
        return genes.stream()
                .map( number -> new Random().nextInt(8) )
                .toList();
    }

    protected void putRandomGrass(WorldMap map) {
        Random random = new Random();
        int mapWidth  = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();
        int counter = 0;

        while ( counter < mapParameters.amountOfPlantsBeginning() ) {
            //
            int x = random.nextInt(mapWidth + 1);
            int y = random.nextInt(mapHeight + 1);
            Vector2d position = new Vector2d(x, y);

            if (map.getFoodMap().containsKey(position)) { continue; }

            map.getFoodMap().put(position, new Grass( position, "Trawka") );
            counter++;
        }
    }

    public List <Vector2d> generateRandomPositions(WorldMap map) {
        List <Vector2d> randomPositions = new ArrayList<>();
        int mapWidth  = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();
        Random random = new Random();

        for (int i = 0 ; i < mapParameters.initialNumberOfAnimals() ; i++) {
            int x = random.nextInt(mapWidth + 1);
            int y = random.nextInt(mapHeight + 1);
            randomPositions.add( new Vector2d(x, y) );
        }

        return randomPositions;
    }
}
