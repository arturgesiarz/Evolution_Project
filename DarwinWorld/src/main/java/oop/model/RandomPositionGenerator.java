package oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class RandomPositionGenerator implements Iterable<Vector2d> {
    private final int objectsNumber;
    private final Vector2d upperRangeBladeOfGrass;
    private final List<Vector2d> randomPoints;

    public RandomPositionGenerator(int objectsNumber, Vector2d upperRangeObject){
        this.objectsNumber = objectsNumber;
        this.upperRangeBladeOfGrass = upperRangeObject;
        this.randomPoints = generateObjectsRandomPosition( createPossiblePositions() );
    }

    public List<Vector2d> createPossiblePositions(){
        List<Vector2d> possiblePositions = new ArrayList<>();
        for(int i = 0; i <= upperRangeBladeOfGrass.getX(); i++){
            for(int j = 0; j <= upperRangeBladeOfGrass.getY(); j++){
                possiblePositions.add(new Vector2d(i,j));
            }
        }
        return possiblePositions;
    }

    public List<Vector2d> generateObjectsRandomPosition(List<Vector2d> possiblePositions){
        List<Vector2d> generatedPoints = new ArrayList<>();
        int lastIndex = possiblePositions.size() - 1;
        int randomSelect;

        for(int i = 0; i < objectsNumber; i++){
            randomSelect = (int)Math.floor(Math.random() * (lastIndex + 1) );
            generatedPoints.add(possiblePositions.get(randomSelect));
            possiblePositions.remove(randomSelect);
            lastIndex--;
        }
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
