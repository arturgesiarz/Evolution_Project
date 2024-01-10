package oop.model.genes;

import oop.model.Animal;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AbstractGenesHandlerTest {

    @Test
    void doesCreationOfGenesWorkProperly() {
        // given
        List <Integer> genomeA = List.of(1,4,3,0,7,6);
        GenesHandler genesHandlerA = new GenesBasic(genomeA);

        List <Integer> genomeB = List.of(0,2,4,1,1,7);
        GenesHandler genesHandlerB = new GenesBasic(genomeB);

        Animal animalA = new Animal(genesHandlerA);
        animalA.setEnergyAmount(5);

        Animal animalB = new Animal(genesHandlerB);
        animalB.setEnergyAmount(10);

        // when
        Animal animalChild = Reproduce.reproduction(animalA, animalB);

        // then





    }



}