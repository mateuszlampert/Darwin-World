package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable {
    private final MapHandler mapHandler;
    private boolean running = true;
    private boolean killed = false;
    private final int dayTime;

    public Simulation(WorldMap map, List<Vector2d> positions, SimulationSettings configuration, int dayTime) {
        this.mapHandler = new MapHandler(map, configuration);
        this.dayTime = dayTime;

        for (Vector2d position : positions) {
            Animal animal = new Animal(position, configuration.genomeLength(), configuration.startingEnergy());
            mapHandler.placeAnimal(animal);
        }
    }

    public MapHandler getMapHandler(){
        return this.mapHandler;
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(mapHandler.getMapAnimals());
    }

    @Override
    public void run() {
        while (!killed) {
            synchronized (this) {
                while (!running) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            try{
                Thread.sleep(dayTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            singleDay();
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

    public  void singleDay() {
        mapHandler.removeDead();
        mapHandler.moveAnimals();
        mapHandler.eatGrass();
        mapHandler.reproduce();
        mapHandler.growGrass();
        mapHandler.updateStatistics();
        System.out.println("step");
        System.out.println(java.time.LocalTime.now());
    }
}