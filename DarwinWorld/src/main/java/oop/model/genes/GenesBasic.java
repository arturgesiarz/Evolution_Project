package oop.model.genes;
import oop.model.Animal;
import java.util.List;

public class GenesBasic extends AbstractGenesHandler {
    private int iteration = -1;

    public GenesBasic(List <Integer> genes) {
        super(genes);
    }
    public GenesBasic(Animal animalA, Animal animalB){
        super(animalA,animalB);
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
