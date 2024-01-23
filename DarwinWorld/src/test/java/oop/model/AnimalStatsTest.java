package oop.model;

import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.maps.RectangularMap;
import oop.model.util.MapParameters;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnimalStatsTest {

    @Test
    void updateAncestorsAmount() {
        //
        GenesHandler genesHandlerA = new GenesBasic( List.of(0, 2, 3, 4) );
        Animal animalA = new Animal( new Vector2d(1,0), 11, genesHandlerA );

        GenesHandler genesHandlerB = new GenesBasic( List.of(6, 7, 0, 1) );
        Animal animalB = new Animal( new Vector2d(2, 1), 10, genesHandlerB );

        GenesHandler genesHandlerC = new GenesBasic( List.of(4, 5, 6, 7) );
        Animal animalC = new Animal( new Vector2d(2, 1), 10, genesHandlerC );

        GenesHandler genesHandlerD = new GenesBasic(animalB, animalC, new Vector2d(1, 3));
        Animal animalD = new Animal(animalB, animalC, genesHandlerD, 6);

        assertEquals(1, animalB.getAnimalStats().getDescendantsAmount() );
        assertEquals(1, animalC.getAnimalStats().getDescendantsAmount() );
        assertEquals(0, animalA.getAnimalStats().getDescendantsAmount() );

    }
}