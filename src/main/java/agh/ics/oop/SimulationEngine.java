package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine{

    private final List<Simulation> simulationsList;
    private final List<Thread> simulationThreads = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(4);
    public SimulationEngine(List<Simulation> simulations){
        simulationsList = simulations;
    }

    public SimulationEngine(Simulation simulation){ // allows to pass only one simulation, added for better readability
        simulationsList = new ArrayList<>();
        simulationsList.add(simulation);
    }

    public void runSync(){
        for(Simulation simulation : simulationsList){
            simulation.run();
        }
    }

    public void runAsync(){
        for(Simulation simulation : simulationsList){
            Thread simulationThread = new Thread(simulation);
            simulationThreads.add(simulationThread);
            simulationThread.start();
        }
    }

    public void runAsyncInThreadPool(){
        for(Simulation simulation : simulationsList){
            executorService.execute(simulation);
        }
    }

    public void awaitSimulationsEnd(){
        try {
            awaitSimulationThreadsEnd();
            executorService.shutdown();
            executorService.awaitTermination(10,TimeUnit.SECONDS);
        } catch(InterruptedException e){
            System.out.println("Simulation thread got interrupted!");
            e.printStackTrace();
        }
    }

    private void awaitSimulationThreadsEnd() throws InterruptedException{
        for(Thread thread : simulationThreads){
            thread.join();
        }
    }




}
