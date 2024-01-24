package oop.model.genes;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenesBasicTest {

    @Test
    void getNextMove() {
        // given
        List<Integer> genes = List.of(2, 3, 4, 5, 6);
        GenesBasic genesHandler = new GenesBasic(genes);

        // when
        List <Integer> properMoves = List.of(2, 3, 4, 5, 6, 2, 3, 4, 5);
        List <Integer> calculatedMoves = new ArrayList<>();
        for (int i = 0 ; i < 9 ; i++) { calculatedMoves.add( genesHandler.getNextMove()); }

        // then
        assertEquals(properMoves, calculatedMoves);
    }
}