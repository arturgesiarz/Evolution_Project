package oop;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.WorldMap;
import java.util.ArrayList;
import java.util.List;

public class Simulation{
    private final List<Animal> animalsList;
    private final List<MapDirection> movesList;
    private final WorldMap worldMap;
    public Simulation(List<Vector2d> positionsList, List<MapDirection> movesList, WorldMap worldMap){ //stawianie zwierzat na mapie
        List<Animal> animalsList = new ArrayList<>();

        for(Vector2d position : positionsList){
            Animal newAnimal = new Animal(position);
            worldMap.place(newAnimal);
            animalsList.add(newAnimal);
        }
        this.animalsList = animalsList;
        this.movesList = movesList;
        this.worldMap = worldMap;
    }

}