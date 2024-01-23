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
import oop.model.maps.Hole;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;

import java.util.List;
import java.util.Map;

public class SimulationPresenter implements MapChangeListener {
    private static final double CELL_WIDTH = 50; //stala sluzaca do okreslenia szerokosci okienka
    private static final double CELL_HEIGHT = 50; //stala sluzaca do okreslenia wysokosci okienka
    @FXML
    public VBox animalStatsBox;
    @FXML
    public VBox globalStatsBox;
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
    private WorldMap map = null;
    private boolean simulationPaused = false;
    private WorldElement animalToFollow = null;
    private GlobalStats animalStatistics = null;

    public void initialize() {

        animalStatsBox.setVisible(false);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll( mapGrid.getChildren().get(0) ); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void createMapGridWithAxes(){
        int mapWith = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();

        for (int i = 0; i <= mapWith + 1; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = 0; i <= mapHeight + 1; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        Label label;
        for (int i = 0; i < mapWith + 1; i++){
            label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }
        for (int i = 0; i < mapHeight + 1; i++){
            label = new Label(String.valueOf(mapHeight - i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }
        label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);


    }

    void drawMap(){
        int mapWith = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();

        clearGrid();
        createMapGridWithAxes();

        for(Map.Entry<Vector2d, List<WorldElement>> entry : map.createElements().entrySet()){
            int test = 0;

            for(WorldElement object : map.createElements().get(entry.getKey())){
                if(object instanceof Animal){
                    Node objectLook;
                    objectLook = new Circle(20, Color.BLACK);

                    GridPane.setHalignment(objectLook, HPos.CENTER);

                    int x = object.getPosition().getX();
                    int y = object.getPosition().getY();

                    mapGrid.add(objectLook, x + 1, mapHeight - y + 1);

                    test = 1;
                }
            }
            if(test == 0){
                for(WorldElement object : map.createElements().get(entry.getKey())){
                    if(object instanceof Hole){
                        Node objectLook;
                        objectLook = new Rectangle(50, 50, Color.BLUEVIOLET);

                        GridPane.setHalignment(objectLook, HPos.CENTER);

                        int x = object.getPosition().getX();
                        int y = object.getPosition().getY();

                        mapGrid.add(objectLook, x + 1, mapHeight - y + 1);

                        test = 1;
                    }
                }
            }
            if(test == 0){
                for(WorldElement object : map.createElements().get(entry.getKey())){
                    if(object instanceof Grass){
                        Node objectLook;
                        objectLook = new Rectangle(50, 50, Color.GREEN);
                        GridPane.setHalignment(objectLook, HPos.CENTER);

                        int x = object.getPosition().getX();
                        int y = object.getPosition().getY();

                        mapGrid.add(objectLook, x + 1, mapHeight - y + 1);
                    }
                }
            }
        }

        updateAllStats();
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

    void animalClicked(WorldElement animal){

        animalToFollow = animal;
        animalStatsBox.setVisible(true);

        //animalStatistics.setCurrent_animal((Animal) animal);
        //animalStatistics.updateStats((Animal) animal);

        updateOneAnimalStats((Animal) animal);
        drawMap();
    }

    private void updateAllStats() {
        GlobalStats globalStats = simulation.getGlobalStats();
        animalsAmount.setText("Liczba zwierząt: " + globalStats.getAnimalsAmount() );
        grassAmount.setText("Liczba pól trawy: " + globalStats.getGrassAmount() );
        emptyCells.setText("Pustych pól: " + globalStats.getEmptyCells() );
        averageChildAmount.setText("Średnia energia: " + globalStats.getAverageEnergyAmount() );
        averageDeadLifeSpan.setText("Średnia życia martwych: " + globalStats.getAverageDeadLifeSpan() );
        averageChildAmount.setText("Średnia ilość dzieci: " + globalStats.getAverageChildAmount() );
        numberOfDeadAnimals.setText("Martwych zwierząt: " + globalStats.getNumberOfDeadAnimals() );
    }

    private void updateOneAnimalStats(Animal animal) {
        animalGenome.setText("Genom zwierzaka: " + animal.getGenesHandler().getGenes());
        animalPosition.setText("Aktualna pozycja zwierzaka: " + animal.getPosition());
        childAmount.setText("Liczba dzieci zwierzaka: " + animal.getAnimalStats().getChildAmount());
        lifeTime.setText("Czas zycia zwierzaka: " + animal.getAnimalStats().getLifeTime());
        energyAmount.setText("Energia zwierzaka: " + animal.getAnimalStats().getEnergyAmount());
    }

    public void setWorldMap(WorldMap map) {
        this.map = map;
    }

    public void setGlobalStats(GlobalStats globalStats) {
        this.animalStatistics = globalStats;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }
}
