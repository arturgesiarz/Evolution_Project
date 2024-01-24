package oop.model.genes;

import oop.model.Vector2d;
import oop.model.animal.Animal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AbstractGenesHandlerTest {

    @Test
    void createGenes() {
        // given
        GenesHandler genesHandlerA = new GenesBasic( List.of(1,2,3,4) );
        Animal leftParent = new Animal(new Vector2d(2, 2), 6, genesHandlerA);

        GenesHandler genesHandlerB = new GenesBasic( List.of(5,6,2,4) );
        Animal rightParent = new Animal(new Vector2d(2, 2), 5, genesHandlerB);

        GenesHandler genesHandlerC = new GenesBasic(leftParent, rightParent, new Vector2d(0, 0) );
        System.out.println( genesHandlerC.getGenes() );

    }
}