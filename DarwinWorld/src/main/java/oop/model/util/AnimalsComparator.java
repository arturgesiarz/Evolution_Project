package oop.model.util;
import oop.model.Animal;
import java.util.Comparator;

public class AnimalsComparator {
    //
    public static Comparator <Animal> comparator() {
        return Comparator
                .comparing( Animal :: getEnergyAmount )
                .thenComparing( animal -> animal.getAnimalStats().getLifeTime() )
                .thenComparing( animal -> animal.getAnimalStats().getChildAmount() );
    }

}
