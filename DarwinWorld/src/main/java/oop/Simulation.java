package oop;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.genes.AbstractGenesHandler;
import oop.model.genes.GenesHandler;
import oop.model.maps.WorldMap;
import java.util.ArrayList;
import java.util.List;

public class Simulation{
    private final List<Animal> animalsList;
    private final List<GenesHandler> genesList;
    private final WorldMap worldMap;
    public Simulation(List <Vector2d> positionsList, List <GenesHandler> genesList, WorldMap worldMap){
        List <Animal> animalsList = new ArrayList<>();

        for(int i = 0; i < positionsList.size(); i ++){
            Animal newAnimal = new Animal(positionsList.get(i), genesList.get(i));
            worldMap.place(newAnimal);
            animalsList.add(newAnimal);
        }

        this.animalsList = animalsList;
        this.genesList = genesList;
        this.worldMap = worldMap;
    } // end method Simulation()



}
