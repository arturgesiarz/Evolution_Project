package oop.model.genes;

import oop.model.Animal;

import java.util.List;

public class GenesExtended extends AbstractGenesHandler {
    //
    public GenesExtended(List<Integer> genes) {
        super(genes);
    }

    @Override
    public int getNextMove(Animal animal) {
        return 0;
    }
}
