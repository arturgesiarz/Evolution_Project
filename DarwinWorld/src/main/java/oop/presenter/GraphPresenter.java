package oop.presenter;

import com.github.sh0nk.matplotlib4j.PythonExecutionException;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import oop.model.displayers.PlotGraph;

import java.io.IOException;

public class GraphPresenter {
    //
    @FXML
    private ToggleButton plotA;
    @FXML
    private ToggleButton plotB;
    @FXML
    private ToggleButton plotC;
    @FXML
    private ToggleButton plotD;
    private PlotGraph plotGraph;


    @FXML
    public void plotAnimalsAmount() throws PythonExecutionException, IOException {
        plotGraph.plotAnimalsAmount();
    }

    @FXML
    public void plotAverageEnergyAmount() throws PythonExecutionException, IOException {
        plotGraph.plotAverageEnergyAmount();
    }

    @FXML
    public void plotAverageChildAmount() throws PythonExecutionException, IOException {
        plotGraph.plotAverageChildAmount();
    }

    @FXML
    public void plotNumberOfDeadAnimals() throws PythonExecutionException, IOException {
        plotGraph.plotNumberOfDeadAnimals();
    }
}
