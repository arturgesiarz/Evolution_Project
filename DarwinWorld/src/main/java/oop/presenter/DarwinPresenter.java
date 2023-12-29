package oop.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class DarwinPresenter {
    private static final double CELL_WIDTH = 30; //TODO ZMIENIC - stala sluzaca do okreslenia szerokosci okienka
    private static final double CELL_HEIGHT = 30; //TODO ZMIENIC - stala sluzaca do okreslenia wysokosci okienka
    @FXML
    public Label infoLabel;
    @FXML
    public GridPane mapGrid;
    @FXML
    public void onSimulationStartClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void onSimulationStopClicked(ActionEvent actionEvent) {
    }
}
