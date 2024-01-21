package oop.model.maps;
import oop.model.*;
import oop.model.util.MapParameters;
import oop.model.util.MapVisualizer;

import java.util.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public abstract class AbstractWorldMap implements WorldMap {
    //
    protected final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    protected final MapParameters mapParameters;
    protected Map <Vector2d, Grass> foodMap = new HashMap<>();
    protected Vector2d lowerLeft;
    protected Vector2d upperRight;
    protected final List<MapChangeListener> observers = new ArrayList<>();
    protected final UUID worldMapID;

    public AbstractWorldMap(MapParameters mapParameters){
        lowerLeft = new Vector2d(0,0);
        upperRight = new Vector2d(mapParameters.width() - 1,mapParameters.height() - 1);
        this.mapParameters = mapParameters;
        MapUtil.growNewGrass(this, mapParameters.amountOfPlantsBeginning());
        worldMapID = UUID.randomUUID();
    }
    @Override
    public UUID getWorldMapID() {
        return worldMapID;
    }
    public void addObserver(MapChangeListener observer) {
        observers.add(observer);
    }
    public void removeObserver(MapChangeListener observer) {
        observers.remove(observer);
    }
    void mapChanged(String message){
        observers.forEach((observer) -> observer.mapChanged(this,message));
    }
    @Override
    public boolean isPole(Vector2d position){
        int northPole = max(this.upperRight.getY(),this.lowerLeft.getY());
        int southPole = min(this.lowerLeft.getY(),this.lowerLeft.getY());

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
        animal.getAnimalStats().decreaseEnergyAmount(1);
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


        mapChanged("Object in position " + oldPosition +
                " moved to " + animal.getPosition());

    }

    public int countAliveAnimals(){
        int aliveAnimal = 0;
        for(Vector2d square : animals.keySet()){
            List<Animal> animalList = animals.get(square);
            aliveAnimal += animalList.size();
        }
        return aliveAnimal;
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


    public boolean isOccupied(Vector2d position) { return this.animals.containsKey(position); }

    @Override
    public Map <Vector2d, List <Animal>> getAnimals() {
        return this.animals;
    }

    @Override
    public MapParameters getMapParameters() {
        return mapParameters;
    }

    @Override
    public Map<Vector2d, Grass> getFoodMap() {
        return foodMap;
    }

    @Override
    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return upperRight;
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(lowerLeft,upperRight);
    }

}
