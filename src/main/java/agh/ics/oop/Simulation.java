package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable {

    private final int simulationSteps;
    private final WorldMap map;
    private final SimulationSettings configuration;
    private final MapHandler mapHandler;

    public Simulation(WorldMap map, List<Vector2d> positions, SimulationSettings configuration, int simulationSteps) {
        this.configuration = configuration;
        this.map = map;
        this.mapHandler = new MapHandler(map);
        this.simulationSteps = simulationSteps;

        for (Vector2d position : positions) {
            Animal animal = new Animal(position, configuration.genomeLength(), configuration.startingEnergy());
            mapHandler.placeAnimal(animal);
        }
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(mapHandler.getMapAnimals());
    }

    @Override
    public void run() {
        for (int i = 0; i < simulationSteps; i++) { // will be infinite loop later
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            mapHandler.removeDead();
            mapHandler.moveAnimals();
            mapHandler.eatGrass();
//            mapHandler.reproduce();
            mapHandler.growGrass();
        }
    }
}
