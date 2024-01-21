package oop.model.maps;

import oop.model.Animal;
import oop.model.Grass;
import oop.model.Vector2d;
import oop.model.util.AnimalsComparator;
import oop.model.util.MapParameters;

import java.util.List;
import java.util.Map;

public class MapUtil {

    // Sortuje listę zwierzaków obecnych na danej pozycji, według kryteriów. Po posortowaniu ostatni zwierzak na liście
    // to ten, który wygrał walkę-on je trawę.
    public static void fightForFood(Map<Vector2d, List<Animal>> animals,
                                    MapParameters mapParameters,
                                    Map <Vector2d, Grass> foodMap) {
        for( List<Animal> animalsOnCell : animals.values() ) {
            animalsOnCell.sort( AnimalsComparator.comparator() );
            Animal animal = animalsOnCell.get( animalsOnCell.size() - 1 );

            animal.getAnimalStats().increaseEnergyAmount( mapParameters.grassEnergy() );
            removeEatenGrass( animal.getPosition(), foodMap);
        }
    }

    private static void removeEatenGrass(Vector2d grassPosition, Map <Vector2d, Grass> foodMap) {
        Grass eatenGrass = foodMap.remove(grassPosition);
    }

    public void removeDeadAnimals(Map<Vector2d, List<Animal>> animals, int time) {
        animals.forEach((key, value) -> {
            // wybieram zwierzeta do usuniecia
            List<Animal> toRemove = value.stream()
                    .filter(animal -> animal.getAnimalStats().getEnergyAmount() <= 0)
                    .toList();

            // usuwam z danych pol zwierzeta
            toRemove.forEach(animal -> {
                value.remove(animal);
                animal.getAnimalStats().setDeathTime(time);
            });

            // usuwam cale pole, jesli nie ma na nim juz zandych zwierzat
            if (value.size() == 0) {
                animals.remove(key);
            }
        });
    }
}
