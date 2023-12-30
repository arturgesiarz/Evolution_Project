package oop.model.genes;
import oop.model.Animal;

import java.util.ArrayList;
import java.util.List;

public interface GenesHandler {
    //

    /**
     * Get the next move of the animal according to its gene.
     * @param animal The animal
     * @return int Next move number
     */
    public int getNextMove(Animal animal);

    /**
     * Changes one random gene to different one
     * @param animal The animal
     */
    public void mutation();


    /**
     * Sets new genes of a child after reproduction of its parents.
     * @param genesA Genes of an animalA
     * @param genesB Genes of an animalB
     */
    public void createGenes(Animal animalA, Animal animalB);


    public Animal getAnimal();

    public List<Integer> getGenes();
}
