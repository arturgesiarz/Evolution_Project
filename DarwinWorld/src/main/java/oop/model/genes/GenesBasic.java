package oop.model.genes;

import oop.model.Animal;

import java.util.List;

public class GenesBasic extends AbstractGenesHandler {
    //
    private int iteration = -1;

    public GenesBasic(List <Integer> genes) {
        super(genes);
    }

    public GenesBasic() {

    }

    @Override
    public int getNextMove(Animal animal) {
        //
        iteration++;
        return genes.get( iteration % genes.size() );
    }



}
