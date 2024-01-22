package oop.model.maps;

import oop.model.*;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesExtended;
import oop.model.genes.GenesHandler;
import oop.model.util.AnimalsComparator;
import oop.model.util.GlobalStats;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.Math.min;
import static java.lang.Math.max;

public class MapUtil {

    // Sortuje listę zwierzaków obecnych na danej pozycji, według kryteriów. Po posortowaniu ostatni zwierzak na liście
    // to ten, który wygrał walkę-on je trawę.
    public static void fightForFood(WorldMap map) {

        for( List<Animal> animalsOnCell : map.getAnimals().values() ) {
            if (animalsOnCell.isEmpty()) { continue; }
            if ( !map.getFoodMap().containsKey( animalsOnCell.get(0).getPosition() ) ) { continue; }

            animalsOnCell.sort( AnimalsComparator.comparator() );
            Animal animal = animalsOnCell.get( animalsOnCell.size() - 1);
            animal.eat(map.getMapParameters().grassEnergy());
            removeEatenGrass( animal.getPosition(), map.getFoodMap() );
        }
    }

    private static void removeEatenGrass(Vector2d grassPosition, Map <Vector2d, Grass> foodMap) {
        foodMap.remove(grassPosition);
    }

    public static void removeDeadAnimals(WorldMap map, GlobalStats globalStats, int time) {
        //
        List<Animal> toRemove = map.getAnimals()
                .values()
                .stream()
                .flatMap(List::stream)
                .filter(animal -> animal.getAnimalStats().getEnergyAmount() <= 0)
                .toList();

        toRemove.forEach( animal -> {
            map.getAnimals().get( animal.getPosition() ).remove(animal);
            animal.getAnimalStats().setDeathTime(time);
            globalStats.animalDiedStats(animal);

            // if (map.getAnimals().get( animal.getPosition() ).size() == 0) {
            if ( map.getAnimals().get(animal.getPosition()).isEmpty() ) {
                map.getAnimals().remove(animal.getPosition());
            }
        });
    } // end procedure removeDeadAnimals()

    public static void fightForReproduction(WorldMap map) {
        // przegladanie listy zwierzat, ktore sa na danym polu
        for( List <Animal> animalsOnCell : map.getAnimals().values() ) {

            if ( animalsOnCell.size() < 2 ) { continue; }

            // sortowanie zwierzat oraz odfiltrowanie tych, ktore nie spelniaja warunkow rozmnazania
            List <Animal> animalsCompeting = animalsOnCell.stream()
                    .filter( animal -> animal.getAnimalStats().getEnergyAmount() >= map.getMapParameters().minimumEnergyRequiredForCopulation() )
                    .sorted( AnimalsComparator.comparator() )
                    .toList();

            // jesli jest mniej niz 2 zwierzat to znaczy ze nie mo kto zkim sie rozmnazac
            if (animalsCompeting.size() < 2) { continue; }

            // wybieramy dwa zwierzeta

            Animal leftParent  = animalsCompeting.get( animalsCompeting.size() - 2 );
            Animal rightParent = animalsCompeting.get( animalsCompeting.size() - 1 );
            Animal childAnimal = null;
            Vector2d mutationRange = new Vector2d( map.getMapParameters().minimumNumberOfMutation(), map.getMapParameters().maximumNumberOfMutation() );

            // tryb: GenesBasic
            if(map.getMapParameters().genesMode() == 1){
                GenesHandler childGenesHandler = new GenesBasic(leftParent, rightParent, mutationRange);
                childAnimal = new Animal( leftParent, rightParent, childGenesHandler, map.getMapParameters().energyLostInCopulation() );
            }
            // tryb: GenesExtended
            else{
                GenesHandler childGenesHandler = new GenesExtended(leftParent, rightParent, mutationRange);
                childAnimal = new Animal( leftParent, rightParent, childGenesHandler, map.getMapParameters().energyLostInCopulation() );
            }

            // dodaje nowe zwierze na mape
            map.place(childAnimal);

            // zmienjaszam energie rodzicow
            leftParent.getAnimalStats().decreaseEnergyAmount( map.getMapParameters().energyLostInCopulation() );
            rightParent.getAnimalStats().decreaseEnergyAmount( map.getMapParameters().energyLostInCopulation() );
        }
    }

    public static void growNewGrass(WorldMap map, int plantsToSeed) {
        int numberOfCellsAvailable =  (int) ( (double) 0.2 * map.getUpperRight().getX() * map.getUpperRight().getY() );
        List <Integer> probability = new ArrayList<>( Collections.nCopies( plantsToSeed, -1) );

        Random random = new Random();
        long howManyPutOnEquator = probability.stream()
                .map(number -> random.nextInt(10))
                .filter(number -> number <= 7)
                .count();

        // Tworzymy listę o długości takiej, ile możemy mieć nowej trawy. Ona może wyrosnąć albo na równiku, albo gdzie indziej.
        // Wyrośnie na równiku z pp. 80% - tj. 4/5. Zatem stwórzmy randomowo tablicę wypełnioną liczbami 0-4.
        // Tyle ile w tablicy jest 0, 1, 2 lub 3-ójek to liczba traw na równiku.

        int mapHeight = map.getMapParameters().height();
        int mapWidth  = map.getMapParameters().width();
        int rowsAmountEquator = (int) Math.ceil( (double) numberOfCellsAvailable / mapWidth );
        rowsAmountEquator = min(rowsAmountEquator, map.getUpperRight().getY() );

        howManyPutOnEquator = min( howManyPutOnEquator, numberOfCellsAvailable ); // w przypadku, gdy dziennie może rosnąć więcej trawy niż dostępnych pól


        int heightEquator = rowsAmountEquator / 2;


        Vector2d leftBorder  = new Vector2d
                ( 0, max(0, mapHeight / 2 - heightEquator ) ) ;

        Vector2d rightBorder = new Vector2d
                ( mapWidth, min( mapHeight, mapHeight / 2 + (rowsAmountEquator - heightEquator) ) );

        putRandomGrass(map, plantsToSeed, howManyPutOnEquator, leftBorder, rightBorder);
    }

    private static void putRandomGrass(WorldMap map, int plantsToSeed, long howManyPutOnEquator, Vector2d leftBorder, Vector2d rightBorder) {
        //
        RandomPositionGenerator generatorGrassOnEquator = new RandomPositionGenerator(howManyPutOnEquator, leftBorder, rightBorder, map);
        List<Vector2d> generatedPointsOnEquator = generatorGrassOnEquator.getRandomPoints();
        putGrass(map, generatedPointsOnEquator);


        if(generatorGrassOnEquator.getSucceedGrassPlaced() < plantsToSeed){
            long restGrassToGenerate = plantsToSeed - generatorGrassOnEquator.getSucceedGrassPlaced();

            RandomPositionGenerator generatorPointsOutsideEquator = new RandomPositionGenerator(restGrassToGenerate, map.getLowerLeft(), map.getUpperRight(), map);
            List<Vector2d> generatedPointsOutsideEquator = generatorPointsOutsideEquator.getRandomPoints();

            putGrass(map, generatedPointsOutsideEquator);
        }
    }

    private static void putGrass(WorldMap map, List<Vector2d>  newGrassPositions) {
        newGrassPositions
                .forEach(newGrassPosition -> map.getFoodMap().put(newGrassPosition, new Grass(newGrassPosition, "Lolium grass")));
    }

    public static List <Animal> createListAnimalFromSet(WorldMap map){
        //
        return map.getAnimals().values().stream()
                .flatMap(List::stream)
                .toList();
    }

}
