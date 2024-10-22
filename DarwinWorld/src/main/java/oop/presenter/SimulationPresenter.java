package oop.presenter;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import oop.model.simulation.Simulation;
import oop.model.*;
import oop.model.animal.Animal;
import oop.model.displayers.MapChangeListener;
import oop.model.maps.Hole;
import oop.model.maps.MapUtil;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;

import java.util.List;
import java.util.Map;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    public Label decendantsAmount;
    private double CELL_WIDTH = 50; //stala sluzaca do okreslenia szerokosci okienka
    private double CELL_HEIGHT = 50; //stala sluzaca do okreslenia wysokosci okienka
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
    private Label evolutionTime;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button pauseSimulationButton;

    @FXML
    private ToggleButton equatorShowing;
    private Simulation simulation;
    private WorldMap map = null;
    private boolean simulationPaused = false;
    private boolean shouldShowEquator = false;
    private WorldElement animalToFollow = null;
    private GlobalStats animalStatistics = null;
    private final ColorDisplay colorD = new ColorDisplay();

    public void initialize() {
        animalStatsBox.setVisible(false);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll( mapGrid.getChildren().get(0) ); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void createMapGridWithAxes(){
        int mapWidth = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();

        if(mapWidth  * mapHeight <= 100){
            CELL_WIDTH = 50;
            CELL_HEIGHT = 50;
        }
        else{
            CELL_WIDTH = (int) Math.sqrt( (double) (650 * 650) / (mapWidth  * mapHeight));
            CELL_HEIGHT = (int)  Math.sqrt( (double) (650 * 650) / (mapWidth  * mapHeight));
        }


        for (int i = 0; i <= mapWidth; i++) {
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
        }

        for (int i = 0; i <= mapHeight; i++) {
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

        Label label;
        for (int i = 0; i < mapWidth; i++){
            label = new Label(String.valueOf(i));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, i + 1, 0);
        }
        for (int i = 0; i < mapHeight; i++){
            label = new Label(String.valueOf(mapHeight - i - 1));
            GridPane.setHalignment(label, HPos.CENTER);
            mapGrid.add(label, 0, i + 1);
        }
        label = new Label("y\\x");
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, 0, 0);


    }

    private Shape createRing(double outerRadius, double innerRadius) {
        Arc outerArc = new Arc(0, 0, outerRadius, outerRadius, 0, 360);
        outerArc.setType(ArcType.ROUND);

        Arc innerArc = new Arc(0, 0, innerRadius, innerRadius, 0, 360);
        innerArc.setType(ArcType.ROUND);

        return Shape.subtract(outerArc, innerArc);
    }

    synchronized void drawMap(){
        int mapWidth   = map.getMapParameters().width();
        int mapHeight = map.getMapParameters().height();

        clearGrid();
        createMapGridWithAxes();

        for(Map.Entry<Vector2d, List<WorldElement>> entry : map.createElements().entrySet()){
            int test = 0;

            if( map.createElements().get(entry.getKey()) == null ) { continue; }
            for(WorldElement object : map.createElements().get(entry.getKey())){
                if(object instanceof Animal){

                    Node objectLook;
                    colorD.determineColor( ((Animal) object).getAnimalStats().getEnergyAmount() );
                    objectLook = new Circle((int) (CELL_WIDTH / 2), Color.rgb(colorD.getX(), colorD.getY(), colorD.getZ()));

                    if (object.equals(animalToFollow)){
                        objectLook = new Circle((int) (CELL_WIDTH / 2), Color.BLUE);
                        animalStatistics.updateAllStats();
                        updateOneAnimalStats((Animal) object);
                    }
                    objectLook.setMouseTransparent(false);
                    objectLook.setOnMouseClicked(event -> animalClicked(object));

                    GridPane.setHalignment(objectLook, HPos.CENTER);

                    int x = object.getPosition().getX();
                    int y = object.getPosition().getY();

                    mapGrid.add(objectLook, x + 1, mapHeight - y);

                    test = 1;
                }
            }
            if(test == 0){
                for(WorldElement object : map.createElements().get(entry.getKey())){
                    if(object instanceof Hole){
                        Node objectLook;

                        Shape ring = createRing(CELL_HEIGHT / 2, CELL_WIDTH / 2 - CELL_WIDTH / 4);
                        ring.setFill(Color.VIOLET);
                        ring.setStroke(Color.RED);
                        objectLook = ring;

//                        objectLook = new Rectangle(CELL_WIDTH, CELL_HEIGHT, Color.BLUEVIOLET);

                        GridPane.setHalignment(objectLook, HPos.CENTER);

                        int x = object.getPosition().getX();
                        int y = object.getPosition().getY();

                        mapGrid.add(objectLook, x + 1, mapHeight - y);

                        test = 1;
                    }
                }
            }

            if(test == 0){
                for(WorldElement object : map.createElements().get(entry.getKey())){
                    if(object instanceof Grass){
                        Node objectLook;
                        objectLook = new Rectangle(CELL_WIDTH, CELL_HEIGHT, Color.GREEN);
                        GridPane.setHalignment(objectLook, HPos.CENTER);

                        int x = object.getPosition().getX();
                        int y = object.getPosition().getY();

                        mapGrid.add(objectLook, x + 1, mapHeight - y);
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

            equatorShowing.setDisable(true);

        } else {
            simulationPaused = true;
            simulation.pauseSimulation();
            pauseSimulationButton.setText("Continue");

            equatorShowing.setDisable(false);
        }
    }

    synchronized void animalClicked(WorldElement animal){
        animalToFollow = animal;
        animalStatsBox.setVisible(true);
        updateOneAnimalStats((Animal) animal);
        Platform.runLater(this::drawMap);
    }

    private void updateAllStats() {
        GlobalStats globalStats = simulation.getGlobalStats();
//        globalStats.updateAllStats();

        animalsAmount.setText("Liczba zwierzat: " + globalStats.getAnimalsAmount() );
        evolutionTime.setText("Czas ewolucji: " + simulation.getEvolutionTime() );
        grassAmount.setText("Liczba pol trawy: " + globalStats.getGrassAmount() );
        emptyCells.setText("Pola bez zwierzat: " + globalStats.getEmptyCells() );
        averageChildAmount.setText("Srednia energia: " + String.format("%.2f", globalStats.getAverageEnergyAmount()));
        averageDeadLifeSpan.setText("Srednia zycia martwych: " + String.format("%.2f",globalStats.getAverageDeadLifeSpan()));
        averageChildAmount.setText("Srednia ilosc dzieci: " + String.format("%.2f",globalStats.getAverageChildAmount()));
        numberOfDeadAnimals.setText("Martwych zwierzat: " + globalStats.getNumberOfDeadAnimals() );

        if (globalStats.getAnimalsAmount() == 0) {
            simulation.pauseSimulation(); }
    }

    private void updateOneAnimalStats(Animal animal) {
        animalGenome.setText("Genom zwierzaka: " + animal.getGenesHandler().getGenes());
        animalPosition.setText("Aktualna pozycja zwierzaka: " + animal.getPosition());
        childAmount.setText("Liczba dzieci zwierzaka: " + animal.getAnimalStats().getChildAmount());
        decendantsAmount.setText("Liczba potomkow zwierzaka: " + animal.getAnimalStats().getDescendantsAmount());
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
    public void mapChanged(WorldMap worldMap, String message) { Platform.runLater(this::drawMap); }

    @FXML
    public void showEquator() {
        if (shouldShowEquator) {
            clearGrid();
            Platform.runLater(this::drawMap);
            shouldShowEquator = false;
        }
        else {
            drawEquator();
            shouldShowEquator = true;
        }

    }

    private void drawEquator() {
        int mapHeight = map.getMapParameters().height();

        Vector2d leftEquatorBorder = MapUtil.getLeftEquatorBorder(map);
        Vector2d rightEquatorBorder = MapUtil.getRightEquatorBorder(map);

        for (int x = 0 ; x < rightEquatorBorder.getX() ; x++)
            for(int y = leftEquatorBorder.getY(); y < rightEquatorBorder.getY() ; y++) {

                Node objectLook;
                objectLook = new Rectangle(CELL_WIDTH, CELL_HEIGHT, Color.LIGHTBLUE);
                GridPane.setHalignment(objectLook, HPos.CENTER);
                mapGrid.add(objectLook, x + 1, mapHeight - y);
            }
    }



}
