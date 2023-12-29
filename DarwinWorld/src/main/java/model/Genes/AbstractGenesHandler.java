package model.Genes;

import model.Animal;

public abstract class AbstractGenesHandler implements GenesHandler {
    //
    private final Animal animal;

    public AbstractGenesHandler(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void createGenes(Animal animalA, Animal animalB) {

    }

    @Override
    public void mutation(Animal animal) {

    }





}
