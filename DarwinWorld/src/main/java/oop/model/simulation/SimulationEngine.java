package oop.model.simulation;

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

    public void runAsyncInThreadPool() {
        simulationList.forEach(executorService::submit);
        awaitSimulationsEnd();
    }

    private void awaitSimulationsEnd(){

        //konczymy dzialanie puli watkow
        executorService.shutdown();
        try {
            executorService.awaitTermination(2, TimeUnit.SECONDS);
        } catch (InterruptedException e){
            e.printStackTrace();
            executorService.shutdownNow();
        }

    }
}
