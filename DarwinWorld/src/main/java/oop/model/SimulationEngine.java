package oop.model;

import oop.Simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    //
    private final List <Simulation> simulationList;
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public SimulationEngine( List <Simulation> simulationList ) {
        this.simulationList = simulationList;
    } // constructor

    public void runAsyncInThreadPool() throws InterruptedException {
        //
        for (Simulation currentSimulation : simulationList ) {
            executorService.submit( currentSimulation );
        }

        awaitSimulationsEnd();
    }

    private void awaitSimulationsEnd() throws InterruptedException {
        //
        if ( ! executorService.awaitTermination(2, TimeUnit.SECONDS) ) {
            executorService.shutdown();
        }

    } // end awaitSimulationsEnd() method
}
