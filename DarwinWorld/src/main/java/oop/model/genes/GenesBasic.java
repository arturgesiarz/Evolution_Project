package oop.model.genes;

import oop.model.Animal;

import java.util.List;

public class GenesBasic extends AbstractGenesHandler {
    //
    private int iteration;

    public GenesBasic(List <Integer> genes) {
        super(genes);
    }

    public GenesBasic() {

    }

    @Override
    public int getNextMove(Animal animal) {
        return 0;
    }



}
