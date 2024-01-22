package oop.model.genes;
import oop.model.Animal;
import oop.model.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.min;

public abstract class AbstractGenesHandler implements GenesHandler {
    protected final int lengthOfTheAnimalGenome;  // dlugosc genomu danego zwierzaka
    protected Vector2d mutationRange;
    protected List <Integer> genes;

    public AbstractGenesHandler(List <Integer> genes) {  // mam taki rozmiar genow jakiej dlugosci wprowadze ale to bedzie kontolowane w klasie Simulation, wiec ttuaj moge to przyjac :)
        this.genes  = genes;
        this.lengthOfTheAnimalGenome = genes.size();
    }
    public AbstractGenesHandler(Animal animalA, Animal animalB, Vector2d mutationRange){  // konstruktor umozliwiajacy odrazu stworzenie genow
        this.lengthOfTheAnimalGenome = animalA.getGenesHandler().getGenes().size();  // ponieaz dlugosc genomu sie nie zmieni :)
        this.genes = createGenes(animalA, animalB);
        this.mutationRange = mutationRange;
        mutation();
    }

    @Override
    public List <Integer> createGenes(Animal animalA, Animal animalB) {
        //
        int genomeLength = this.lengthOfTheAnimalGenome;
        int totalEnergy = animalA.getAnimalStats().getEnergyAmount() + animalB.getAnimalStats().getEnergyAmount();
        int whichSideStart = (int) Math.round( Math.random() ); // 0 - prawa strona, 1 - lewa strona genotypu silniejszego


        int numberAnimalA = (int) ( (double)  animalA.getAnimalStats().getEnergyAmount() / totalEnergy  * genomeLength );
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

        return genesChildStream.collect( Collectors.toList() );
    } // end method createGenes()

    @Override
    public void mutation() {
        //
        Random random = new Random();
        for ( int key : whichGenesToChange() ) {
            genes.set( key, random.nextInt(8) );
        }
    }

    private List <Integer> whichGenesToChange() {
        List <Integer> listToPick = new ArrayList<>(IntStream.range(0, lengthOfTheAnimalGenome).boxed().toList());
        Random random = new Random();

        Collections.shuffle(listToPick);
        return listToPick
                .stream()
                .limit( min( lengthOfTheAnimalGenome, random.nextInt( mutationRange.getY() - mutationRange.getX() + 1 ) + mutationRange.getX() ) )
                .toList();
    } // end method whichGenesToChange()

    public List <Integer> getGenes() {
        return this.genes;
    }

}
