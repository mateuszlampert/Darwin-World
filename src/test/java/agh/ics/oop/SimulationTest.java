package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    private final SimulationSettings configuration = new SimulationSettings(1, 1, 1, 1, 1, new WoodyEquator(), 1, 1, 1, 1, 1, 1, new RandomMutation(), 1, new FullPredestination());


    @Test
    public void testSimpleOneAnimal() {
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4,"test_map_0");

        animalInitPositions.add(new Vector2d(1,1));
        expectedAnimalPositions.add(new Vector2d(1,2));
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,animalInitPositions, configuration, 1);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for (int i = 0; i < animalList.size(); i++) {
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testSimpleTwoAnimals() {
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4,"test_map_0");

        animalInitPositions.add(new Vector2d(1,1));
        animalInitPositions.add(new Vector2d(2,1));
        expectedAnimalPositions.add(new Vector2d(1,3));
        expectedAnimalPositions.add(new Vector2d(2,3));
        expectedAnimalDirections.add(MapDirection.NORTH);
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,animalInitPositions, configuration, 10);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for (int i = 0; i < animalList.size(); i++) {
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testTwoAnimalsSamePlace(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4,"test_map_0");

        animalInitPositions.add(new Vector2d(1,2));
        animalInitPositions.add(new Vector2d(1,1));
        expectedAnimalPositions.add(new Vector2d(1,3));
        expectedAnimalPositions.add(new Vector2d(1,3));
        expectedAnimalDirections.add(MapDirection.NORTH);
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,animalInitPositions, configuration, 10);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for (int i = 0; i < animalList.size(); i++) {
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testOneAnimalOneStep(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4,"test_map_0");

        animalInitPositions.add(new Vector2d(1,0));
        expectedAnimalPositions.add(new Vector2d(1,1));
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,animalInitPositions, configuration, 1);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for (int i = 0; i < animalList.size(); i++) {
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

}