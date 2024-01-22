package oop.presenter;

import oop.model.RandomPositionGenerator;
import oop.model.Vector2d;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesExtended;
import oop.model.genes.GenesHandler;
import oop.model.maps.WorldMap;
import oop.model.util.MapParameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MapPreparator {
    private final WorldMap map;
    private final MapParameters mapParameters;
    private final List<? extends GenesHandler> genes;
    private final List<Vector2d> animalPositions;

    public MapPreparator(WorldMap map, MapParameters mapParameters) {
        this.map = map;
        this.mapParameters = mapParameters;

        if(mapParameters.genesMode() == 1){ // geny basic
            this.genes = calculateBasicGenesList();
        }
        else{ // geny rozszerzone
            this.genes = calculateExtendedGenesList();
        }

        this.animalPositions = generateRandomPositions();
    }

    public List<? extends GenesHandler> getGenes() {
        return genes;
    }

    public List<Vector2d> getAnimalPositions() {
        return animalPositions;
    }

    private List<GenesBasic> calculateBasicGenesList() {
        return randomGenesList().stream().map( GenesBasic :: new ).toList();
    }

    private List <GenesExtended> calculateExtendedGenesList() {
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

    private List <Vector2d> generateRandomPositions() {

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
