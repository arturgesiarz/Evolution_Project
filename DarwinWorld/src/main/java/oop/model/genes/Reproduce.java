package oop.model.genes;

import oop.model.Animal;

public class Reproduce {
    //

    public static Animal reproduction(Animal animalA, Animal animalB) {
        GenesHandler childGenesHandler = new GenesBasic();

        childGenesHandler.createGenes(animalA, animalB);
        childGenesHandler.mutation();

        return new Animal(childGenesHandler);
    }

}
