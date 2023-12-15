package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable {

    private final int simulationSteps;
    private final AbstractWorldMap map;
    private final SimulationSettings configuration;
    private final MapHandler mapHandler;

    public Simulation(AbstractWorldMap map, List<Vector2d> positions, SimulationSettings configuration, int simulationSteps) {
        this.configuration = configuration;
        this.map = map;
        this.mapHandler = new MapHandler(map);
        this.simulationSteps = simulationSteps;

        for (Vector2d position : positions) {
            Animal animal = new Animal(position);
            mapHandler.placeAnimal(animal);
        }
    }

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(mapHandler.getMapAnimals());
    }


    @Override
    public void run() {
        for (int i = 0; i < simulationSteps; i++) { // will be infinite loop later
            mapHandler.removeDead();
            mapHandler.moveAnimals();
            //mapHandler.eatGrass();
            //mapHandler.reproduce();
            //mapHandler.growGrass();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
