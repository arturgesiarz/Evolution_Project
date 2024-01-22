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
import oop.model.Animal;
import oop.model.Food;
import oop.model.MapChangeListener;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    //
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

    Simulation simulation;
    WorldMap map;
    boolean simulationPaused = false;


    public void initialize() {
        animalStatsBox.setVisible(false);
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

    private void clearGrid() {
        mapGrid.getChildren().retainAll( mapGrid.getChildren().get(0) ); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
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
