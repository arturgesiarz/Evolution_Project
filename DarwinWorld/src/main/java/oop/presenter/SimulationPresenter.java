package oop.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import oop.Simulation;
import oop.model.*;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;
import oop.model.util.MapParameters;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private static final double CELL_WIDTH = 50; //stala sluzaca do okreslenia szerokosci okienka
    private static final double CELL_HEIGHT = 50; //stala sluzaca do okreslenia wysokosci okienka

    public VBox animalStatsBox;
    @FXML
    private Label animalsAmount;
    @FXML
    private Label grassAmount;
    @FXML
    private Label emptyCells;
    @FXML
    private Label averageEnergyAmount;
    @FXML
    private Label averageDeadLifeSpan;
    @FXML
    private Label averageChildAmount;
    @FXML
    private Label numberOfDeadAnimals;
    @FXML
    private Label animalGenome;
    @FXML
    private Label animalPosition;
    @FXML
    private Label childAmount;
    @FXML
    private Label lifeTime;
    @FXML
    private Label energyAmount;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button pauseSimulationButton;

    private Simulation simulation;
    private WorldMap map;
    private boolean simulationPaused = false;


    public void initialize() {
        animalStatsBox.setVisible(false);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll( mapGrid.getChildren().get(0) ); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void createRawGrid(int noRows, int noCols){

        for(int i = 0; i <= noRows; i++){
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT)); //ustawianie wysokosci komorki
        }
        for(int i = 0; i <= noCols; i++){
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH)); //ustawianie szeroskosci komorki
        }
    }

    private void addAxesGrid(int noRows, int noCols){ //metoda dodaje do grida naglowki osi czyli x/y 0 1 2.. itd
        Label labelToAdd;

        labelToAdd = new Label("y\\x");
        GridPane.setHalignment(labelToAdd, HPos.CENTER);
        mapGrid.add(labelToAdd,0,0);

        for(int i = 0; i <= noRows; i ++){ //dodaje naglowki dla kolumn
            labelToAdd = new Label("" + (map.getLowerLeft().getX() + i));
            GridPane.setHalignment(labelToAdd, HPos.CENTER);
            mapGrid.add(labelToAdd,i + 1,0);
        }

        for(int i = 0; i <= noCols; i ++){ //dodaje naglowki dla wierszy
            labelToAdd = new Label("" + (map.getLowerLeft().getY() + i));
            GridPane.setHalignment(labelToAdd, HPos.CENTER);
            mapGrid.add(labelToAdd,0,i + 1);
        }
    }

    private void complementsRestGrid(int noRows, int noCols){ //metoda uzupelnia reszte calego grida -> aktualizuje przemieszczanie sie
        VBox vBoxToAdd;
        for(int i = 0; i <= noRows; i ++){
            for(int j = 0; j <= noCols; j ++){
                Vector2d actVector = new Vector2d(
                        map.getLowerLeft().getX() + j,
                        map.getLowerLeft().getY() + i
                );
                WorldElement worldElement = map.objectAt(actVector).orElse(null);
                if (worldElement != null) {
                    vBoxToAdd = new WorldElementBox(worldElement).getContainer();
                } else {
                    vBoxToAdd = new VBox(new Label(""));
                }
                // labelToAdd = new Label(map.objectAt(actVector).map(WorldElement::toString).orElse(""));

                GridPane.setHalignment(vBoxToAdd, HPos.CENTER);
                mapGrid.add(vBoxToAdd,j + 1,i + 1);
            }
        }
    }

    void drawMap() {
        //
        clearGrid();
        int mapWidth  = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();

        for (int i = 0 ; i < mapWidth  ; i++) { mapGrid.getColumnConstraints().add( new ColumnConstraints(50) ); }
        for (int i = 0 ; i < mapHeight ; i++) { mapGrid.getRowConstraints().add( new RowConstraints(50) ); }


        Label label;

        label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);

        for (int i = 0 ; i <= mapWidth ; i++) {
            label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }

        for (int i = 0 ; i <= mapHeight ; i++) {
            label = new Label( String.valueOf(mapHeight - i) );
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }

        fillPaneAnimals();
        updateStats();
    } // end method drawMap()

    private void fillPaneAnimals() {
        //
        Node objectLook;

        for ( Food food : map.getFoodMap().values() ) {
            objectLook = new Rectangle(50, 50, Color.GREEN);
            GridPane.setHalignment( objectLook, HPos.CENTER );
            mapGrid.add(objectLook, food.getPosition().getX() + 1, map.getMapParameters().height() - food.getPosition().getY() + 1);
        }

        for ( Animal animal : map.getAnimals().values().stream().flatMap(List::stream).toList() ) {
            objectLook = new Circle(20, Color.BLACK);
            // TODO
        }

    }



    @FXML
    public void pauseSimulation() {
        //
        if ( simulationPaused ){
            simulationPaused = false;
            simulation.unpauseSimulation();
            pauseSimulationButton.setText("Pause");

        } else {
            simulationPaused = true;
            simulation.pauseSimulation();
            pauseSimulationButton.setText("Unpause");
        }
    }

    private void updateStats() {
        GlobalStats globalStats = simulation.getGlobalStats();
        animalsAmount.setText("Liczba zwierząt: " + globalStats.getAnimalsAmount() );
        grassAmount.setText("Liczba pól trawy: " + globalStats.getGrassAmount() );
        emptyCells.setText("Pustych pól: " + globalStats.getEmptyCells() );
        averageChildAmount.setText("Średnia energia: " + globalStats.getAverageEnergyAmount() );
        averageDeadLifeSpan.setText("Średnia życia martwych: " + globalStats.getAverageDeadLifeSpan() );
        averageChildAmount.setText("Średnia ilość dzieci: " + globalStats.getAverageChildAmount() );
        numberOfDeadAnimals.setText("Martwych zwierząt: " + globalStats.getNumberOfDeadAnimals() );
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }
}
