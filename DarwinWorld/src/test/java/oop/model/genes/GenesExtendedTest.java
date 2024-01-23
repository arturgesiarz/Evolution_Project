package oop.model.genes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenesExtendedTest {

    @Test
    void getNextMove() {
        List <Integer> genes = List.of(2, 3, 4, 5, 6);
        GenesExtended genesHandler = new GenesExtended(genes);

        for (int i = 0 ; i < 10 ; i++) {
            System.out.println(genesHandler.getNextMove());
        }
    }

    @Test
    void getActGene() {
    }
}