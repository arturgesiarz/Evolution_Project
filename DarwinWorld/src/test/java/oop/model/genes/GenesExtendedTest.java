package oop.model.genes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenesExtendedTest {

    @Test
    void getNextMove() {
        // given
        List <Integer> genes = List.of(2, 3, 4, 5, 6);
        GenesExtended genesHandler = new GenesExtended(genes);

        // when
        List <Integer> properMoves = List.of(2,3,4,5,6,5,4,3,2);
        List <Integer> calculatedMoves = new ArrayList<>();
        for (int i = 0 ; i < 9 ; i++) { calculatedMoves.add( genesHandler.getNextMove()); }

        // then
        assertEquals( properMoves, calculatedMoves );
    }

}