package oop.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

public class DarwinPresenter {
    private static final double CELL_WIDTH = 30;
    private static final double CELL_HEIGHT = 30;

    @FXML
    public TextField mapHeight;
    @FXML
    public TextField mapWidth;
    @FXML
    public Label mapPropertiesLabel;
    @FXML
    public VBox mapProperties;
    @FXML
    public ToggleButton mapBasicToggleButton;
    @FXML
    public ToggleButton mapExtendedToggleButton;
    @FXML
    public TextField plantsBeggining;
    @FXML
    public ToggleButton genesBasicToggleButton;
    @FXML
    public ToggleButton genesExtendedToggleButton;


    @FXML
    private void initialize() {
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
}
