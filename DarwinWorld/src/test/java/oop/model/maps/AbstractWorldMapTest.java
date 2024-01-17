package oop.model.maps;

import oop.model.Animal;
import oop.model.Grass;
import oop.model.Vector2d;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.util.MapParameters;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractWorldMapTest {

    @Test
    void fightForFood() {
        GenesHandler genesHandlerA = new GenesBasic( List.of(0, 2, 3, 4) );
        Animal animalA = new Animal( new Vector2d(1,0), 11, genesHandlerA );

        GenesHandler genesHandlerB = new GenesBasic( List.of(6, 7, 0, 1) );
        Animal animalB = new Animal( new Vector2d(2, 1), 10, genesHandlerB );

        GenesHandler genesHandlerC = new GenesBasic( List.of(4, 5, 6, 7) );
        Animal animalC = new Animal( new Vector2d(1, 2), 10, genesHandlerC );

        MapParameters mapParameters = new MapParameters(5,3, 3, 0, 5, 5);
        RectangularMap animalsMap = new RectangularMap(10, 10, mapParameters );
        animalsMap.foodMap.put( new Vector2d(1,1), new Grass(new Vector2d(1,1), "Grass"));

        animalsMap.place(animalA);
        animalsMap.place(animalB);
        animalsMap.place(animalC);

        animalsMap.move( animalA );
        animalsMap.move( animalB );
        animalsMap.move( animalC );

        animalsMap.fightForFood();
        assertEquals(14, animalA.getAnimalStats().getEnergyAmount() );
    }

    @Test
    void fightForReproduction() {

    }
}