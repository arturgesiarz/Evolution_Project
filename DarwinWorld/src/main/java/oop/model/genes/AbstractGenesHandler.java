package oop.model.genes;
import oop.model.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractGenesHandler implements GenesHandler {
    protected final int lengthOfTheAnimalGenome;  // dlugosc geomu danego zwierzaka
    protected List <Integer> genes;

    public AbstractGenesHandler(List <Integer> genes) {  // mam taki rozmiar genow jakiej dlugosci wprowadze ale to bedzie kontolowane w klasie Simulation, wiec ttuaj moge to przyjac :)
        this.genes  = genes;
        this.lengthOfTheAnimalGenome = genes.size();
    }
    public AbstractGenesHandler(Animal animalA, Animal animalB){  // konstruktor umozliwiajacy odrazu stworzenie genow
        this.genes = createGenes(animalA, animalB);
        this.lengthOfTheAnimalGenome = animalA.getGenesHandler().getGenes().size();  // ponieaz dlugosc genomu sie nie zmieni :)
    }

    @Override
    public List <Integer> createGenes(Animal animalA, Animal animalB) {
        //
        int genomeLength = this.lengthOfTheAnimalGenome;
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


        return genesChildStream.collect( Collectors.toList() );

    } // end method createGenes()

    @Override
    public void mutation() { //TODO

    }

    public List <Integer> getGenes() {
        return this.genes;
    }


}
