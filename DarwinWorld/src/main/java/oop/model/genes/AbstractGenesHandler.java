package oop.model.genes;

import oop.model.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractGenesHandler implements GenesHandler {
    //
    protected List <Integer> genes;
    private Animal animal;

    public AbstractGenesHandler(List <Integer> genes) {
        //
        this.genes  = genes;
    }

    public AbstractGenesHandler() {

    }

    @Override
    public void createGenes(Animal animalA, Animal animalB) {
        //
        int genomeLength = 6;
        int totalEnergy = animalA.getEnergyAmount() + animalB.getEnergyAmount();
        int whichSideStart = (int) Math.round( Math.random() ); // 0 - prawa strona, 1 - lewa strona genotypu silniejszego

        int numberAnimalA = (int) ( (double)  animalA.getEnergyAmount() / totalEnergy  * genomeLength );
        int numberAnimalB = genomeLength - numberAnimalA;

        int numberToSkip;

        GenesHandler genesHandlerA = animalA.getGenesHandler();
        GenesHandler genesHandlerB = animalB.getGenesHandler();

        List <Integer> firstGenome;
        List <Integer> secondGenome;

        if ( numberAnimalA > numberAnimalB ) {
            firstGenome  = genesHandlerA.getGenes();
            secondGenome = genesHandlerB.getGenes();
            numberToSkip = numberAnimalB;
        }
        else {
            firstGenome  = genesHandlerB.getGenes();
            secondGenome = genesHandlerA.getGenes();
            numberToSkip = numberAnimalA;
        }

        Stream <Integer> genesChildStream;

        if (whichSideStart == 0) {
            Stream <Integer> genesStreamSecond = secondGenome.stream().limit(numberToSkip);
            Stream <Integer> genesStreamFirst  = firstGenome.stream().skip(numberToSkip);
            genesChildStream = Stream.concat(genesStreamSecond, genesStreamFirst);
        }
        else {
            Stream <Integer> genesStreamSecond  = secondGenome.stream().skip(numberToSkip);
            Stream <Integer> genesStreamFirst   = firstGenome.stream().limit(numberToSkip);
            genesChildStream = Stream.concat(genesStreamFirst, genesStreamSecond);
        }

        this.genes = genesChildStream.collect( Collectors.toList() );

    } // end method createGenes()

    @Override
    public void mutation() {

    }

    public List <Integer> getGenes() {
        return this.genes;
    }

    @Override
    public Animal getAnimal() {
        return animal;
    }

}
