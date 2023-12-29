package oop.model.genes;

import oop.model.Animal;

public class GenesBasic extends AbstractGenesHandler {
    //
    private int iteration;

    public GenesBasic(Animal animal) {
        super(animal);
    }

    @Override
    public int getNextMove(Animal animal) {
        return 0;
    }
}
