package agh.ics.oop;

import agh.ics.oop.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {


    @Test
    public void testNoData(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Animal> expectedAnimalList = new ArrayList<Animal>();
        WorldMap map = new RectangularMap(4,4, "test_map_0");

        String[] in = {};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);


        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();
        List<Animal> actualAnimalList = s.getAnimals();
        assertEquals(expectedAnimalList, actualAnimalList);
    }

    @Test
    public void testSimpleOneAnimal(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4, "test_map_0");

        String[] in = {"f", "r", "r", "f", "r", "r" , "f", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(1,1));

        expectedAnimalPositions.add(new Vector2d(2,0));
        expectedAnimalDirections.add(MapDirection.SOUTH);


        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }
    @Test
    public void testComplexOneAnimal(){ // zwierze robi dwa kolka wokol planszy, najpierw normalnie a pozniej tylem
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4, "test_map_0");

        String[] in = {"f", "f", "f","f","r", "r","f", "f", "f","f","r", "r","f", "f", "f","f","r", "r","f", "f", "f","f","b","b","b","b","l", "l","b","b","b","b","l", "l","b","b","b","b","l", "l","b","b","b","b"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(0,0));

        expectedAnimalPositions.add(new Vector2d(0,0));
        expectedAnimalDirections.add(MapDirection.NORTH);

        Simulation s = new Simulation(map, moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }


    @Test
    public void testOutOfMap(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(5,5, "test_map_0");

        String[] in = {"r","f","r", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(4,0));

        expectedAnimalPositions.add(new Vector2d(4,0));

        expectedAnimalDirections.add(MapDirection.EAST);

        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        System.out.println(animalList.get(0).getPosition());
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testMixedDataOneAnimal(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(4,4, "test_map_0");

        String[] in = {"f", "f", "r", "r", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(0,0));

        expectedAnimalPositions.add(new Vector2d(1,2));
        expectedAnimalDirections.add(MapDirection.EAST);

        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }


    }

    @Test
    public void testMixedDataFewAnimals() {
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(5,5, "test_map_0");

        String[] in = {"r", "r","r", "r", "f", "f", "f", "f", "l", "l", "l", "l", "f", "f", "l", "r","l","r"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(0, 0));
        animalInitPositions.add(new Vector2d(2, 2));

        expectedAnimalPositions.add(new Vector2d(2, 1));
        expectedAnimalPositions.add(new Vector2d(4, 3));
        expectedAnimalDirections.add(MapDirection.WEST);
        expectedAnimalDirections.add(MapDirection.EAST);

        Simulation s = new Simulation(map,moves, animalInitPositions);
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
    public void testSimpleOneAnimalBigMap(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(10,10, "test_map_0");

        String[] in = {"f", "f", "f", "f" , "f", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(1,1));

        expectedAnimalPositions.add(new Vector2d(1,7));
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testComplexOneAnimalBigMap(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(10,10, "test_map_0");

        String[] in = {"f","f","f","f","f","f","f","f","f","f","r", "r","f","f","f","f","f","f","f","f","f","f","b","b","b","b","b","b","b","b","b","b","l", "l","b","b","b","b","b","b","b","b","b","b"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(0,0));

        expectedAnimalPositions.add(new Vector2d(0,0));
        expectedAnimalDirections.add(MapDirection.NORTH);


        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }
    @Test
    public void testAnimalAtSamePlace(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        ArrayList<Vector2d> expectedAnimalPositions = new ArrayList<Vector2d>();
        ArrayList<MapDirection> expectedAnimalDirections = new ArrayList<MapDirection>();
        WorldMap map = new RectangularMap(10,10, "test_map_0");

        String[] in = {"b", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(7,8));
        animalInitPositions.add(new Vector2d(7,7));


        expectedAnimalPositions.add(new Vector2d(7,8));
        expectedAnimalPositions.add(new Vector2d(7,7));


        expectedAnimalDirections.add(MapDirection.NORTH);
        expectedAnimalDirections.add(MapDirection.NORTH);



        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();

        List<Animal> animalList = s.getAnimals();
        for(int i =0; i < animalList.size(); i++){
            Animal currentAnimal = animalList.get(i);
            Vector2d expectedPosition = expectedAnimalPositions.get(i);
            MapDirection expectedDirection = expectedAnimalDirections.get(i);
            assertTrue(currentAnimal.isAt(expectedPosition));
            assertTrue(currentAnimal.isFacing(expectedDirection));
        }
    }

    @Test
    public void testAddTwoAnimalsAtSamePlace(){
        ArrayList<Vector2d> animalInitPositions = new ArrayList<Vector2d>();
        WorldMap map = new RectangularMap(4,4, "test_map_0");

        String[] in = {"f", "r", "r", "f", "r", "r" , "f", "f"};
        ArrayList<MoveDirection> moves = OptionsParser.Parse(in);
        animalInitPositions.add(new Vector2d(1,1));
        animalInitPositions.add(new Vector2d(1,1));


        Simulation s = new Simulation(map,moves, animalInitPositions);
        s.run();
        List<Animal> animalList = s.getAnimals();
        assertEquals(1, animalList.size());
    }

}