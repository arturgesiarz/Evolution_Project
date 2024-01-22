package oop.model.genes;

import oop.model.Animal;
import oop.model.Vector2d;

import java.util.List;

public class GenesExtended extends AbstractGenesHandler {
    private int iteration;
    private int operation = 1;

    public GenesExtended(List<Integer> genes) {
        super(genes);
    }
    public GenesExtended(Animal animalA, Animal animalB, Vector2d mutationRange){
        super(animalA, animalB, mutationRange);
    }

    @Override
    public int getNextMove() {
        //
        int moveValue = genes.get(iteration + operation);
        iteration++;
        operation(iteration);

        return moveValue;
    }
    @Override
    public int getActGene() {
        return iteration;
    }

    private void operation(int iteration) {
        //
        int genesSize = genes.size();

        if (iteration == genesSize) {
            this.operation = -1;
        }
        if (iteration == -1) {
            this.operation = 1;
        }
    }
}
