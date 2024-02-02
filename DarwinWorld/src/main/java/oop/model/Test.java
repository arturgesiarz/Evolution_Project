package oop.model;

import oop.model.animal.Animal;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;

import java.util.List;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        //
        GenesHandler genesHandlerA = new GenesBasic( List.of(1, 2, 3) );
        Animal animalA = new Animal(new Vector2d(2, 2), 5, genesHandlerA);

        GenesHandler genesHandlerB = new GenesBasic( List.of(3, 4, 5) );
        Animal animalB = new Animal(new Vector2d(3, 3), 4, genesHandlerB);

        List < List <Animal> > animalList = List.of( List.of(animalA), List.of(animalB) );
//        animalList.stream().reduce( Stream.empty(), Stream.concat() ).toList();
    }
}
