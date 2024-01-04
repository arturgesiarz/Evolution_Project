package oop;
import javafx.application.Application;
import oop.model.Animal;
import oop.model.Vector2d;
import oop.model.genes.GenesBasic;
import oop.model.genes.GenesHandler;
import oop.model.maps.RectangularMap;

import java.util.ArrayList;
import java.util.List;

public class DarwinGUI {
    public static void main(String[] args){
        // Application.launch(DarwinApp.class, args);
        List<Vector2d> positions = List.of(new Vector2d(0,2), new Vector2d(0,0));  // tworze przykladowe pozycje poczatkowe naszych zwierzat
        List<GenesHandler> genes = List.of(new GenesBasic(List.of(1,2,3,4,5,6)), new GenesBasic(List.of(0,2,3,5,6,1)));
        RectangularMap map = new RectangularMap(10, 10);
        Simulation simulation = new Simulation(positions, genes, map);

    }
}
