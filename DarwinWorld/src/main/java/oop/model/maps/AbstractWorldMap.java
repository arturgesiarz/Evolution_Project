package oop.model.maps;
import oop.model.Animal;
import oop.model.MapDirection;
import oop.model.Vector2d;
import oop.model.WorldElement;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements WorldMap {
    protected final int minimumEnergyRequiredForCopulation;   // minimalna liczba energi potrzebna do tego aby zwierzaki mogly ze soba kopulowac
    protected final int energyLostInCopulation;  // energia tracona podczas kopulacji
    protected final Map<Vector2d, Animal> animals = new HashMap<>();  // mapa samych zwierzat
    protected Map <Vector2d, WorldElement> wordMap = new HashMap<>();  // mapa wszystkiego
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

        if(position.getY() > northPole || position.getY() < southPole){  // chcemy wejsc na biegun
            return true;
        }
        return false;  // jestesmy ciagle na normlanej mapie
    }
    @Override
    public void place(WorldElement animal){
        wordMap.put( animal.getPosition(), animal );  // moge polozyc gdzie chce dane zwierze
    }
    @Override
    public void move(Animal animal) {
        //TODO ZAKTUALIZOWAC MAPE PO RUCHU DANEGO ZWIERZECIA
        animal.move();
    }
    @Override
    public void removeDeadAnimal(Animal animal) { //TODO

    }
}
