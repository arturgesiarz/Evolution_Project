package model.Genes;
import model.Animal;

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
    public void mutation(Animal animal);


    /**
     * Sets new genes of a child after reproduction of its parents.
     * @param animalA First animal
     * @param animalB Second animal
     */
    public void createGenes(Animal animalA, Animal animalB);
}
