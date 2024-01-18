package agh.ics.oop;

import agh.ics.oop.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable {
    private final MapHandler mapHandler;
    private boolean running = true;
    private boolean killed = false;
    private final int dayTime;
    private final boolean shouldSaveStatsToFile;
    private final String simulationStartDatetime;

    public Simulation(WorldMap map, List<Vector2d> positions, SimulationSettings configuration, int dayTime) {
        this.mapHandler = new MapHandler(map, configuration);
        this.dayTime = dayTime;
        this.shouldSaveStatsToFile = configuration.saveToFile();
        simulationStartDatetime = java.time.LocalTime.now().toString();

        for (Vector2d position : positions) {
            Animal animal = new Animal(position, configuration.genomeLength(), configuration.startingEnergy());
            mapHandler.placeAnimal(animal);
        }

    }

    @Override
    public void run() {
        while (!killed) {
            synchronized (this) {
                while (!running) {
                    waitForResume();
                }
            }
            waitBetweenDays();
            singleDay();
            if(shouldSaveStatsToFile){
                saveStatsToFile();
            }
        }
    }


    public void pause() {
        this.running = false;
    }

    public void play() {
        synchronized (this) {
            this.running = true;
            notify();
        }
    }

    public void kill() {
        this.killed = true;
    }

    private void waitForResume(){
        try {
            wait();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitBetweenDays(){
        try{
            Thread.sleep(dayTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveStatsToFile(){
        String formattedDatetime = simulationStartDatetime.replace(":", "_");
        String filename = "./simulation_" + formattedDatetime + ".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(mapHandler.getSerializedStatistics());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing statistics to file: " + e.getMessage());
        }
    }

    public void singleDay() {
        mapHandler.removeDead();
        mapHandler.moveAnimals();
        mapHandler.eatGrass();
        mapHandler.reproduce();
        mapHandler.growGrass();

        mapHandler.updateStatistics();
        mapHandler.notifyListeners("DAY PASSED");

        System.out.println(java.time.LocalTime.now());
    }

    public void addMapListener(MapChangeListener listener){
        mapHandler.addListener(listener);
    }
}