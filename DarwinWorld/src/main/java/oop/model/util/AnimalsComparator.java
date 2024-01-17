package oop.model.util;
import oop.model.Animal;
import java.util.Comparator;

public class AnimalsComparator {
    //
    public static Comparator <Animal> comparator() {
        return Comparator
                .comparing( (Animal animal) -> animal.getAnimalStats().getEnergyAmount())
                .thenComparing( (Animal animal) -> animal.getAnimalStats().getLifeTime() )
                .thenComparing( (Animal animal) -> animal.getAnimalStats().getChildAmount() );
    }

}
