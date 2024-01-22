package oop.model.genes;
import oop.model.Animal;
import oop.model.Vector2d;

import java.util.List;

public class GenesBasic extends AbstractGenesHandler {
    private int iteration = -1;

    public GenesBasic(List <Integer> genes) {
        super(genes);
    }
    public GenesBasic(Animal animalA, Animal animalB, Vector2d mutationRange){
        super(animalA,animalB, mutationRange);
    }

    @Override
    public int getNextMove() {
        iteration++;
        return genes.get( iteration % genes.size() );
    }
    @Override
    public int getActGene() {
        return iteration;
    }
}
