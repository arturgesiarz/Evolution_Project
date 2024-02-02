package oop.model;

import oop.model.maps.Hole;
import oop.model.maps.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.lang.Math.min;
import static java.lang.Math.random;

public class RandomHolesGenerator {
    //
    private final int holesNumber;
    private final Vector2d rightBorder;
    private final Vector2d leftBorder;
    private final List <Hole> randomHoles;
    private final WorldMap map;

    public RandomHolesGenerator(int holesNumber, Vector2d leftBorder, Vector2d rightBorder, WorldMap map){
        this.holesNumber = min( holesNumber, map.getUpperRight().getX() * map.getUpperRight().getY() / 2 );
        this.rightBorder = rightBorder;
        this.leftBorder = leftBorder;

        this.map = map;
        this.randomHoles = generateRandomHoles( createPossiblePositionsForHoles() );
    }

    public List <Hole> getRandomHoles() {
        return randomHoles;
    }

    public List <Vector2d> createPossiblePositionsForHoles(){
        //
        List <Vector2d> possiblePositions = new ArrayList<>();

        for(int i = 0; i <= rightBorder.getX(); i++){
            for(int j = leftBorder.getY(); j <= rightBorder.getY(); j++){
                Vector2d newPosition = new Vector2d(i,j);
                possiblePositions.add( newPosition );
            }
        }

        return possiblePositions;
    } // end procedure createPossiblePositionsForHoles


    public List <Hole> generateRandomHoles(List<Vector2d> possiblePositions){
        //
        List<Hole> generatedHoles = new ArrayList<>();

        int lastIndex = possiblePositions.size() - 1;
        int randomSelect;

        if (lastIndex >= 0) {
            //
            Vector2d entrance;
            Vector2d exit;

            for(int i = 0; i < holesNumber; i++){

                // wybor pierwszej dziury
                randomSelect = (int) Math.floor(Math.random() * (lastIndex + 1) );
                entrance = possiblePositions.get(randomSelect);
                possiblePositions.remove(randomSelect);
                lastIndex--;

                // wybor drugiej dziury
                randomSelect = (int) Math.floor(Math.random() * (lastIndex + 1) );
                exit = possiblePositions.get(randomSelect);
                possiblePositions.remove(randomSelect);
                lastIndex--;

                // stowrzenie przejscia podziemnego
                Hole newHoleFirst = new Hole(entrance, exit);
                Hole newHoleSecond = new Hole(exit, entrance);
                generatedHoles.add( newHoleFirst );
                generatedHoles.add( newHoleSecond );

            }
        }

        return generatedHoles;
    } // end method generateRandomHoles()


}
