package model.Genes;

import model.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractGenesHandler implements GenesHandler {
    //
    private final Animal animal;

    private List <Integer> genes;

    public AbstractGenesHandler(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void createGenes(GenesHandler genesA, GenesHandler genesB, int genomeLength) {
        //
        Animal animalA = genesA.getAnimal();
        Animal animalB = genesB.getAnimal();

        int totalEnergy = animalA.getEnergyAmount() + animalB.getEnergyAmount();
        int whichSideStart = (int) Math.round( Math.random() ); // 0 - prawa strona, 1 - lewa strona genotypu silniejszego

        int numberAnimalA = (int) animalA.getEnergyAmount() / totalEnergy * genomeLength;
        int numberAnimalB = genomeLength - numberAnimalA;

        Stream <Integer> genesChildStream;

        if (numberAnimalA > numberAnimalB) {
            //
            if (whichSideStart == 0) {
                Stream <Integer> genesStreamB = genesB.getGenes().stream().limit(numberAnimalB);
                Stream <Integer> genesStreamA = genesA.getGenes().stream().skip(numberAnimalB);
                genesChildStream = Stream.concat(genesStreamB, genesStreamA);
            }
            else {
                Stream <Integer> genesStreamB = genesB.getGenes().stream().skip(numberAnimalA);
                Stream <Integer> genesStreamA = genesA.getGenes().stream().limit(numberAnimalA);
                genesChildStream = Stream.concat(genesStreamA, genesStreamB);
            }
        }

        else {
            //
            if (whichSideStart == 0) {
                Stream <Integer> genesStreamA = genesA.getGenes().stream().limit(numberAnimalA);
                Stream <Integer> genesStreamB = genesB.getGenes().stream().skip(numberAnimalA);
                genesChildStream = Stream.concat(genesStreamA, genesStreamB);
            }
            else {
                Stream <Integer> genesStreamB = genesB.getGenes().stream().limit(numberAnimalA);
                Stream <Integer> genesStreamA = genesA.getGenes().stream().skip(numberAnimalA);
                genesChildStream = Stream.concat(genesStreamB, genesStreamA);
            }
        } // end 'if' clauses

        this.genes = genesChildStream.collect( Collectors.toList() );
    } // end method createGenes()

    @Override
    public void mutation(Animal animal) {

    }

    public Animal getAnimal() {
        return this.animal;
    }

    public List<Integer> getGenes() {
        return this.genes;
    }


}
