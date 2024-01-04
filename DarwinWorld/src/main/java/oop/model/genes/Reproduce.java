package oop.model.genes;

import oop.model.Animal;
import oop.model.Vector2d;

public class Reproduce {
    public static Animal reproduction(Animal animalA, Animal animalB) {
        GenesHandler childGenesHandler = new GenesBasic(animalA, animalB);  // tworze odrazu geny dla dziecka poprzez rodzicow
        Vector2d parentPosition = animalA.getPosition();

        return new Animal(parentPosition, childGenesHandler);
    }

}
