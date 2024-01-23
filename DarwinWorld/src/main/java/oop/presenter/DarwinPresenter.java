package oop.presenter;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oop.Simulation;
import oop.model.ConsoleMapDisplay;
import oop.model.FileMapDisplay;
import oop.model.SimulationEngine;
import oop.model.maps.MapWithHoles;
import oop.model.maps.RectangularMap;
import oop.model.maps.WorldMap;
import oop.model.util.GlobalStats;
import oop.model.util.MapParameters;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DarwinPresenter {
    private static final double CELL_WIDTH = 30;
    private static final double CELL_HEIGHT = 30;

    @FXML
    public TextField mapHeight;
    @FXML
    public TextField mapWidth;
    @FXML
    public ToggleButton mapBasicToggleButton;
    @FXML
    public ToggleButton mapExtendedToggleButton;
    @FXML
    public ToggleButton genesBasicToggleButton;
    @FXML
    public ToggleButton genesExtendedToggleButton;
    @FXML
    public ImageView backgroundImageView;
    @FXML
    public TextField energyOnePlant;
    @FXML
    public TextField energyAnimalBeginning;
    @FXML
    public TextField energyFullAnimal;
    @FXML
    public TextField energyLoseForBaby;
    @FXML
    public TextField plantsBeginning;
    @FXML
    public TextField plantsPerDay;
    @FXML
    public TextField animalsBeginning;
    @FXML
    public TextField minumumMutation;
    @FXML
    public TextField maximumMutation;
    @FXML
    public TextField genomeLength;
    @FXML
    public ComboBox saveComboBox;

    @FXML
    private ComboBox<String> configurationsComboBox;
    private MapParameters mapParameters;
    private int mapModeOn = -1;
    private int genesModeOn = -1;

    @FXML
    private void initialize() {
        Image backgroundImage = new Image("img/background.png");
        backgroundImageView.setImage(backgroundImage);
        setTextForFormatter(4, mapWidth);
        setTextForFormatter(4, mapHeight);

        setTextForFormatter(4, energyOnePlant);
        setTextForFormatter(4, energyAnimalBeginning);
        setTextForFormatter(4, energyFullAnimal);
        setTextForFormatter(4, energyLoseForBaby);

        setTextForFormatter(4, plantsBeginning);
        setTextForFormatter(4, plantsPerDay);
        setTextForFormatter(4, animalsBeginning);
        setTextForFormatter(4, minumumMutation);
        setTextForFormatter(4, maximumMutation);
        setTextForFormatter(4, genomeLength);

    }
    private void setTextForFormatter(int maxInt, TextField atribute){
        atribute.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0," + maxInt +"}")) {
                return change;
            }
            return null;
        }));
    }

//    @FXML
//    public void onSimulationStartClicked(ActionEvent actionEvent) {
//
//    }
    @FXML
    public void onMapBasic() {
        mapBasicToggleButton.setDisable(true);
        mapExtendedToggleButton.setDisable(false);
        mapModeOn = 1;
    }
    @FXML
    public void onMapExtended() {
        mapBasicToggleButton.setDisable(false);
        mapExtendedToggleButton.setDisable(true);
        mapModeOn = 0;
    }

    @FXML
    public void onGenesBasic() {
        genesBasicToggleButton.setDisable(true);
        genesExtendedToggleButton.setDisable(false);
        genesModeOn = 1;
    }

    @FXML
    public void onGenesExtended() {
        genesBasicToggleButton.setDisable(false);
        genesExtendedToggleButton.setDisable(true);
        genesModeOn = 0;

    }

    @FXML
    public void onOpenFileClicked() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) configurationsComboBox.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(selectedFile);
                convertFile(rootNode);
                setText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            showFileReadErrorAlert();
        }
    }

    @FXML
    public void onSaveFileClicked() {
        if(!checkIfAllArgsSelected()){
            showFileReadErrorAlertForNotAllArgs();
        }
        else{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> dataToSave = saveData();
                    objectMapper.writeValue(file, dataToSave);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                showFileReadErrorAlertForNotSelected();
            }
        }
    }
    private boolean checkIfAllArgsSelected(){
        int selector = 0;
        if (!mapWidth.getText().isEmpty() ){
            selector += 1;
        }
        if (!mapHeight.getText().isEmpty() ){
            selector += 1;
        }
        if (!energyOnePlant.getText().isEmpty()){
            selector += 1;
        }
        if (!energyAnimalBeginning.getText().isEmpty()){
            selector += 1;
        }
        if (!energyFullAnimal.getText().isEmpty()){
            selector += 1;
        }
        if (!energyLoseForBaby.getText().isEmpty()){
            selector += 1;
        }
        if (!plantsBeginning.getText().isEmpty()){
            selector += 1;
        }
        if (!plantsPerDay.getText().isEmpty()){
            selector += 1;
        }
        if (!animalsBeginning.getText().isEmpty()){
            selector += 1;
        }
        if (!minumumMutation.getText().isEmpty()){
            selector += 1;
        }
        if (!maximumMutation.getText().isEmpty()){
            selector += 1;
        }
        if (!genomeLength.getText().isEmpty()){
            selector += 1;
        }
        selector += genesModeOn;
        selector += mapModeOn;

        if(selector >= 12) return true;
        return false;
    }

    private void showFileReadErrorAlertForNotAllArgs() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Not All Args");
        alert.setHeaderText("Error");
        alert.setContentText("Not all arguments have been selected!. Please introduce them!");
        alert.showAndWait();
    }

    private void showFileReadErrorAlertForNotSelected() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Select");
        alert.setHeaderText("Error while select place!");
        alert.setContentText("Filed to select where to save the file! Check that you have selected the correct path!");
        alert.showAndWait();
    }

    private Map<String, Object> saveData(){
        Map<String, Object> dataToSave = new HashMap<>();

        dataToSave.put("width", mapWidth.getText());
        dataToSave.put("height", mapHeight.getText());
        dataToSave.put("mapMode", String.valueOf(mapModeOn));
        dataToSave.put("amountOfPlantsBeginning", plantsBeginning.getText());
        dataToSave.put("grassEnergy", energyOnePlant.getText());
        dataToSave.put("amountOfPlantsDaily", plantsPerDay.getText());
        dataToSave.put("initialNumberOfAnimals", animalsBeginning.getText());
        dataToSave.put("startEnergy", energyAnimalBeginning.getText());
        dataToSave.put("minimumEnergyRequiredForCopulation", energyFullAnimal.getText());
        dataToSave.put("energyLostInCopulation",energyLoseForBaby.getText());
        dataToSave.put("minimumNumberOfMutation", minumumMutation.getText());
        dataToSave.put("maximumNumberOfMutation", maximumMutation.getText());
        dataToSave.put("genesMode", String.valueOf(genesModeOn));
        dataToSave.put("genomeLength", genomeLength.getText());

        return dataToSave;
    }

    private void showFileReadErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error while loading file!");
        alert.setContentText("Failed to load file. Check that the selected file is correct!");
        alert.showAndWait();
    }

    private void convertFile(JsonNode node){
        mapParameters = new MapParameters(
                node.get("width").asInt(),
                node.get("height").asInt(),
                node.get("mapMode").asInt(),
                node.get("amountOfPlantsBeginning").asInt(),
                node.get("grassEnergy").asInt(),
                node.get("amountOfPlantsDaily").asInt(),
                node.get("initialNumberOfAnimals").asInt(),
                node.get("startEnergy").asInt(),
                node.get("minimumEnergyRequiredForCopulation").asInt(),
                node.get("energyLostInCopulation").asInt(),
                node.get("minimumNumberOfMutation").asInt(),
                node.get("maximumNumberOfMutation").asInt(),
                node.get("genesMode").asInt(),
                node.get("genomeLength").asInt()
        );

    }

    private void setText(){

        // konfiguracja mapy
        mapHeight.setText(String.valueOf(mapParameters.height()));
        mapWidth.setText(String.valueOf(mapParameters.width()));

        if(mapParameters.mapMode() == 0){ // with holes
            onMapExtended();
        }
        else{  // zwykla mapa
            onMapBasic();
        }

        // konfiguracja energii
        energyOnePlant.setText(String.valueOf(mapParameters.grassEnergy()));
        energyAnimalBeginning.setText(String.valueOf(mapParameters.startEnergy()));
        energyFullAnimal.setText(String.valueOf(mapParameters.minimumEnergyRequiredForCopulation()));
        energyLoseForBaby.setText(String.valueOf(mapParameters.energyLostInCopulation()));

        // konfiguracja spawnowania
        plantsBeginning.setText(String.valueOf(mapParameters.amountOfPlantsBeginning()));
        plantsPerDay.setText(String.valueOf(mapParameters.amountOfPlantsDaily()));
        animalsBeginning.setText(String.valueOf(mapParameters.initialNumberOfAnimals()));
        minumumMutation.setText(String.valueOf(mapParameters.minimumNumberOfMutation()));
        maximumMutation.setText(String.valueOf(mapParameters.maximumNumberOfMutation()));
        genomeLength.setText(String.valueOf(mapParameters.genomeLength()));

        if(mapParameters.genesMode() == 0){ // extened genes
            onGenesExtended();
        }
        else{  // basic genes
            onGenesBasic();
        }

    }

    @FXML
    public void onSimulationStartClicked() throws IOException {
        if(!checkIfAllArgsSelected()){
            showFileReadErrorAlertForNotAllArgs();
        }
        else{
            // ustawiam paramtery
            setMapParameters();

            // inicializacja nowego okna
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("workSimulation.fxml"));
            Stage stage = new Stage();
            BorderPane viewRoot = loader.load();
            configureStage(stage, viewRoot);
            stage.show();
 
            // tworzenie mapy
            WorldMap map = null;
            if (mapParameters.mapMode() == 1) {
                map = new RectangularMap(mapParameters);
            }
            else {
                map = new MapWithHoles(mapParameters);
            }

            // stworzenie prezentera
            SimulationPresenter presenter = loader.getController();
            presenter.initialize();
            presenter.setWorldMap(map);
            presenter.setGlobalStats(new GlobalStats(map));

            map.addObserver(presenter);

            // stworzenie symulacji
            MapPreparator mapPreparator = new MapPreparator(map, mapParameters);
            Simulation simulation = new Simulation(
                    mapPreparator.getAnimalPositions(), mapPreparator.getGenes(), map);

            // dodawanie symulacji
            presenter.setSimulation(simulation);
            stage.setOnCloseRequest(event -> simulation.stopSimulation());
            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));

            // uruchamianie symulacji
            simulationEngine.runAsyncInThreadPool();

        }
    }

    private void setMapParameters(){
        this.mapParameters = new MapParameters(
                Integer.parseInt(mapWidth.getText()),
                Integer.parseInt(mapHeight.getText()),
                mapModeOn,
                Integer.parseInt(plantsBeginning.getText()),
                Integer.parseInt(energyOnePlant.getText()),
                Integer.parseInt(plantsPerDay.getText()),
                Integer.parseInt(animalsBeginning.getText()),
                Integer.parseInt(energyAnimalBeginning.getText()),
                Integer.parseInt(energyFullAnimal.getText()),
                Integer.parseInt(energyLoseForBaby.getText()),
                Integer.parseInt(minumumMutation.getText()),
                Integer.parseInt(maximumMutation.getText()),
                genesModeOn,
                Integer.parseInt(genomeLength.getText())
        );

    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DarwinWorld");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setResizable(false);
    }

}
