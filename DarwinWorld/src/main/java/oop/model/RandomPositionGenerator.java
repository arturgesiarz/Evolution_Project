package oop.model;

import oop.model.maps.WorldMap;

import java.util.*;
import java.util.function.Consumer;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final long objectsNumber;
    private final Vector2d rightBorder;
    private final Vector2d leftBorder;
    private final List<Vector2d> randomPoints;
    private final WorldMap map;
    private long succeedGrassPlaced = 0;

    public long getSucceedGrassPlaced() {
        return succeedGrassPlaced;
    }

    public List<Vector2d> getRandomPoints() {
        return randomPoints;
    }

    public RandomPositionGenerator(long objectsNumber, Vector2d leftBorder, Vector2d rightBorder, WorldMap map){
        this.objectsNumber = objectsNumber;
        this.rightBorder = rightBorder;
        this.leftBorder = leftBorder;
        this.map = map;
        this.randomPoints = generateObjectsRandomPosition( createPossiblePositions() );
    }

    public List<Vector2d> createPossiblePositions(){
        List<Vector2d> possiblePositions = new ArrayList<>();
        for(int i = 0; i <= rightBorder.getX(); i++){
            for(int j = leftBorder.getY(); j <= rightBorder.getY(); j++){
                Vector2d newPosition = new Vector2d(i,j);

                // okreslamy dostepne miejsca dla trawy tam gdzie jej nie ma
                if(!map.getFoodMap().containsKey(newPosition)){
                    possiblePositions.add(newPosition);
                }

            }
        }

        return possiblePositions;
    }

    public List<Vector2d> generateObjectsRandomPosition(List<Vector2d> possiblePositions){
        List<Vector2d> generatedPoints = new ArrayList<>();
        int lastIndex = possiblePositions.size() - 1;
        int randomSelect;
        long puttedGrass = 0;

        if(lastIndex >= 0){
            for(int i = 0; i < objectsNumber; i++){
                if ( possiblePositions.size() == 0 ) {
                    System.out.println("DUPA");
                    break;
                }
                randomSelect = (int)Math.floor(Math.random() * (lastIndex + 1) );
                generatedPoints.add(possiblePositions.get(randomSelect));
                possiblePositions.remove(randomSelect);
                lastIndex--;
                puttedGrass++;
            }
        }

        this.succeedGrassPlaced = puttedGrass;
        return generatedPoints;
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return randomPoints.iterator();
    }
    @Override
    public void forEach(Consumer<? super Vector2d> action) {
        Iterable.super.forEach(action);
    }
    @Override
    public Spliterator<Vector2d> spliterator() {
        return Iterable.super.spliterator();
    }
}
