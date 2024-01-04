package oop.model.maps;
import oop.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int minimumEnergyRequiredForCopulation;   // minimalna liczba energi potrzebna do tego aby zwierzaki mogly ze soba kopulowac
    protected final int energyLostInCopulation;  // energia tracona podczas kopulacji
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();  // mapa zwierzat na mapie, ale w postaci listy
    protected Map <Vector2d, Grass> foodMap = new HashMap<>();  // mapa trawy na mapie
    protected Vector2d lowerLeft;  // lewy dolny rog mapy
    protected Vector2d upperRight;  // prawy gorny rog mapy

    public AbstractWorldMap(int width, int height){
        this(width,height, 10, 5);  // ustawiam domysla energie, kiedy uzytkownik jej nie poda
    }
    public AbstractWorldMap(int width, int height, int minimumEnergyRequiredForCopulation, int energyLostInCopulation){
        lowerLeft = new Vector2d(0,0);
        upperRight = new Vector2d(width - 1,height - 1);
        this.minimumEnergyRequiredForCopulation = minimumEnergyRequiredForCopulation;
        this.energyLostInCopulation = energyLostInCopulation;
    }
    public int getMinimumEnergyRequiredForCopulation() {
        return minimumEnergyRequiredForCopulation;
    }
    public int getEnergyLostInCopulation(){
        return energyLostInCopulation;
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

        if(position.getX() < leftCorner){  // znaczy ze chce wyjsc poza mape z lewej strony
            return new Vector2d(rightCorner, position.getY());
        }
        if(position.getX() > rightCorner){  // znaczy ze chce wyjsc poza mape z prawej strony
            return new Vector2d(leftCorner, position.getY());
        }
        return position;  // nie chce wyjsc poza mape wiec jest ok
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
    public void removeDeadAnimal(Animal animal) { //TODO

    }
}
