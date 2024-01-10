package oop.model;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.genes.Reproduce;

import java.util.List;
import java.util.Random;

public class Main {
    //
    public static void main(String[] args) {
        List<Integer> genomeA = List.of(1,4,3,0,7,6);
        GenesHandler genesHandlerA = new GenesBasic(genomeA);

        List <Integer> genomeB = List.of(0,2,4,1,1,7);
        GenesHandler genesHandlerB = new GenesBasic(genomeB);

        Animal animalA = new Animal(new Vector2d(0,0),genesHandlerA);
        animalA.setEnergyAmount(5);

        Animal animalB = new Animal(new Vector2d(5,3),genesHandlerB);
        animalB.setEnergyAmount(10);

        GenesHandler childGenesHandler = new GenesBasic(animalA, animalB);
        Animal childAnimal = new Animal( animalB.getPosition(), childGenesHandler );

        System.out.println( childAnimal.getGenesHandler().getGenes() );


    }
}
