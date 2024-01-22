package oop.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import oop.model.util.MapParameters;

import java.io.File;
import java.io.IOException;

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
    public TextField energyAnimalBeggining;
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
    private ComboBox<String> configurationsComboBox;
    private MapParameters mapParameters;


    @FXML
    private void initialize() {
//        Image backgroundImage = new Image("background.png");
//        backgroundImageView.setImage(backgroundImage);

        //todo dodac zabezpiecznia na wpisanie tak jak to
        mapWidth.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d{0,3}")) {
                return change;
            }
            return null;
        }));





    }

    @FXML
    public void onSimulationStartClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void onMapBasic() {
        mapBasicToggleButton.setDisable(true);
        mapExtendedToggleButton.setDisable(false);
    }
    @FXML
    public void onMapExtended() {
        mapBasicToggleButton.setDisable(false);
        mapExtendedToggleButton.setDisable(true);
    }

    @FXML
    public void onGenesBasic() {
        genesBasicToggleButton.setDisable(true);
        genesExtendedToggleButton.setDisable(false);
    }

    @FXML
    public void onGenesExtended() {
        genesBasicToggleButton.setDisable(false);
        genesExtendedToggleButton.setDisable(true);

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
        energyAnimalBeggining.setText(String.valueOf(mapParameters.startEnergy()));
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



}
