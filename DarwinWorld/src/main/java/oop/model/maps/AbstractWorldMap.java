package oop.model.maps;
import oop.model.*;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesExtended;
import oop.model.genes.GenesHandler;
import oop.model.util.AnimalsComparator;
import oop.model.util.MapParameters;

import java.util.*;

public abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final MapParameters mapParameters;
    protected Map <Vector2d, Grass> foodMap = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;

    public AbstractWorldMap(int width, int height, MapParameters mapParameters){
        lowerLeft = new Vector2d(0,0);
        upperRight = new Vector2d(width - 1,height - 1);
        this.mapParameters = mapParameters;

    }
    @Override
    public boolean isPole(Vector2d position){
        int northPole = this.upperRight.getY();
        int southPole = this.lowerLeft.getY();

        return position.getY() > northPole || position.getY() < southPole;
    }

    @Override
    public Vector2d teleportation(Vector2d position) {
        // najpiew musimy sprawdzic czy nie chcemy wyjsc poza lewy/prawy koniec
        int leftCorner = this.lowerLeft.getX();
        int rightCorner = this.upperRight.getX();

        if(position.getX() < leftCorner){
            return new Vector2d(rightCorner, position.getY());
        }
        if(position.getX() > rightCorner){
            return new Vector2d(leftCorner, position.getY());
        }
        return position;
    }

    @Override
    public void place(Animal animal){  // moge polozyc dane zwierze na mapie - inne obiekty bede osobno dodawal w innych metodach
        Vector2d animalPosition = animal.getPosition();

        if(animals.containsKey(animalPosition)){
            List<Animal> preAnimalsList = animals.get(animalPosition);
            preAnimalsList.add(animal);
        }
        else{
            List<Animal> newAnimalsList = new ArrayList<>();
            newAnimalsList.add(animal);
            animals.put(animalPosition, newAnimalsList);
        }
    }
    @Override
    public void move(Animal animal) {
        Vector2d oldPosition = animal.getPosition();
        List<Animal> oldAnimalsList = animals.get(oldPosition);  // pozyskuje liste zwierzat bedacych na tej samej pozycji

        if(oldAnimalsList.size() == 1){  // to znaczy ze tylko jeden element jest pod tym indeksem ktory zostanie zmieniony i tak, a wiec nastapi usuowanie calosci

            animals.remove(oldPosition);
            animal.move(this);  // ruch zwierzaka

            Vector2d newPosition = animal.getPosition();
            updateNewPositionList(animal, newPosition);

        }
        else{  // znaczy to ze jest wiecej indeksow pod tym
            oldAnimalsList.remove(animal); // usuwam aktualne zwierze z starej listy
            animal.move(this);  // ruch zwierzaka

            Vector2d newPosition = animal.getPosition();
            updateNewPositionList(animal, newPosition);
        }
    }

    private void updateNewPositionList(Animal animal, Vector2d newPosition) {
        if(animals.containsKey(newPosition)){  // znaczy ze juz sa jakies zwierzeta na tym polu to po prostu dodaje zwierze do aktualnej listy
            List<Animal> preAnimalList = animals.get(newPosition);
            preAnimalList.add(animal);
        }
        else{  // znaczy ze nie ma zadnych zwierzat,a wiec tworze nowa liste jedno-elementowa
            List<Animal> newAnimalList = new ArrayList<>();
            newAnimalList.add(animal);
            animals.put(animal.getPosition(), newAnimalList);
        }
    }

    @Override
    public void removeDeadAnimals(int time) {
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

    @Override
    public void growNewGrass() {
        //
        int numberOfCellsAvailable =  (int) ( (double) 0.8 * upperRight.getX() * upperRight.getY() );
        List <Integer> probability = new ArrayList<>( Collections.nCopies( mapParameters.amountOfPlantsDaily(), -1) );
        // tworzy listę długości n, wypełnionych daną liczbą

        Random random = new Random();
        long howManyPutOnEquator = probability.stream()
                .map(number -> random.nextInt(5))
                .filter(number -> number <= 3)
                .count();

        // Tworzymy listę o długości takiej, ile możemy mieć nowej trawy. Ona może wyrosnąć albo na równiku, albo gdzie indziej.
        // Wyrośnie na równiku z pp. 80% - tj. 4/5. Zatem stwórzmy randomowo tablicę wypełnioną liczbami 0-4.
        // Tyle ile w tablicy jest 0, 1, 2 lub 3-ójek to liczba traw na równiku.

        howManyPutOnEquator = Math.min( howManyPutOnEquator, numberOfCellsAvailable ); // w przypadku, gdy dziennie może rosnąć więcej trawy niż dostępnych pól
        int rowsAmount = (int) Math.ceil( (double) howManyPutOnEquator / upperRight.getX() );
        rowsAmount = Math.min( rowsAmount, upperRight.getY() );

        // TODO DOKOŃCZYĆ GENEROWANIE TRAWY

    }


    // Sortuje listę zwierzaków obecnych na danej pozycji, według kryteriów. Po posortowaniu ostatni zwierzak na liście
    // to ten, który wygrał walkę-on je trawę.
    private void fightForFood() {
        for( List <Animal> animalsOnCell : animals.values() ) {
            animalsOnCell.sort( AnimalsComparator.comparator() );
            Animal animal = animalsOnCell.get( animalsOnCell.size() - 1 );

            animal.getAnimalStats().increaseEnergyAmount( mapParameters.grassEnergy() );
            removeEatenGrass( animal.getPosition() );
        }
    }

    private void removeEatenGrass(Vector2d grassPosition) {
        Grass eatenGrass = foodMap.remove(grassPosition);
    }

    public void fightForReproduction() {
        // przegladanie listy zwierzat, ktore sa na danym polu
        for( List <Animal> animalsOnCell : animals.values() ) {

            if ( animalsOnCell.size() < 2 ) { continue; }

            // sortowanie zwierzat oraz odfiltrowanie tych, ktore nie spelniaja warunkow rozmnazania
            List <Animal> animalsCompeting = animalsOnCell.stream()
                    .filter( animal -> animal.getAnimalStats().getEnergyAmount() >= mapParameters.minimumEnergyRequiredForCopulation() )
                    .sorted( AnimalsComparator.comparator() )
                    .toList();

            // jesli jest mniej niz 2 zwierzat to znaczy ze nie mo kto zkim sie rozmnazac
            if (animalsCompeting.size() < 2) { continue; }

            // wybieramy dwa zwierzeta
            Animal leftParent  = animalsCompeting.get( animalsOnCell.size() - 2 );
            Animal rightParent = animalsCompeting.get( animalsOnCell.size() - 1 );
            Animal childAnimal = null;

            // tryb: GenesBasic
            if(mapParameters.genesMode() == 1){
                GenesHandler childGenesHandler = new GenesBasic(leftParent, rightParent);
                childAnimal = new Animal(leftParent, rightParent, childGenesHandler);
            }
            // tryb: GenesExtended
            else{
                GenesHandler childGenesHandler = new GenesExtended(leftParent, rightParent);
                childAnimal = new Animal(leftParent, rightParent, childGenesHandler);
            }

            // dodaje nowe zwierze na mape
            this.place(childAnimal);

            // zmienjaszam energie rodzicow
            leftParent.getAnimalStats().decreaseEnergyAmount( mapParameters.energyLostInCopulation() );
            rightParent.getAnimalStats().decreaseEnergyAmount( mapParameters.energyLostInCopulation() );
        }
    }

    public Map <Vector2d, List <Animal>> getAnimals() {
        return this.animals;
    }
}
