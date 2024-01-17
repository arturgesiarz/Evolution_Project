package oop.model.util;
import oop.model.Animal;
import java.util.Comparator;
import java.util.Random;

public class AnimalsComparator {
    //
    public static Comparator <Animal> comparator() {
        return Comparator
                .comparing( (Animal animal) -> animal.getAnimalStats().getEnergyAmount())
                .thenComparing( (Animal animal) -> animal.getAnimalStats().getLifeTime() )
                .thenComparing( (Animal animal) -> animal.getAnimalStats().getChildAmount() )
                .thenComparingInt( (Animal animal) -> (new Random()).nextInt());
    }
}

