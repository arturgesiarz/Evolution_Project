package oop.model.genes;

import oop.model.Animal;

public class GenesExtended extends AbstractGenesHandler {
    //
    public GenesExtended(Animal animal) {
        super(animal);
    }

    @Override
    public int getNextMove(Animal animal) {
        return 0;
    }
}
