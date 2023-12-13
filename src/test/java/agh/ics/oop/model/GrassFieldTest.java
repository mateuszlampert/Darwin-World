package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GrassFieldTest {

    @Test
    public void testTooManyGrass(){
        GrassField map = new GrassField(10, "test_map_0");
        assertAll( () -> { // should not throw exception if we want to place more grass than available
            map.place(new Grass(new Vector2d(2, 2), 10));
            map.place(new Grass(new Vector2d(4, 4), 10));
            map.place(new Grass(new Vector2d(7, 7), 10));
        });
        assertEquals(10, map.getElements().size());


    }

    @Test
    public void addOneAnimal(){
        GrassField map = new GrassField(10, "test_map_0");
        assertAll( () -> {
            map.place(new Animal(new Vector2d(2, 2)));
        });
    }

    @Test
    public void addAnimalOnTopOfGrass(){
        GrassField map = new GrassField(10, "test_map_0");
        assertAll( () -> {
            map.place(new Grass(new Vector2d(-2,-2), 10));
            map.place(new Animal(new Vector2d(-2,-2)));
        });

    }
    @Test
    public void addAnimalsAtHugeDistance(){
        GrassField map = new GrassField(10, "test_map_0");
        assertAll( () -> {
            map.place(new Animal(new Vector2d(2000000000,2000000000)));
            map.place(new Animal(new Vector2d(-2000000000,-2000000000)));
        });
    }


    @Test
    public void countGrass(){
        int counter = 0;
        GrassField map = new GrassField(10, "test_map_0");
        for(int i =0; i < 10; i++){
            for(int j=0; j<10; j++){
                if(map.isOccupied(new Vector2d(i,j))){
                    counter++;
                }
            }
        }
        assertEquals(10, counter);
    }

    @Test
    public void countGrass2(){
        int counter = 0;
        GrassField map = new GrassField(10, "test_map_0");
        for(int i =0; i < 10; i++){
            for(int j=0; j<10; j++){
                if(map.objectAt(new Vector2d(i,j)) instanceof Grass){
                    counter++;
                }
            }
        }
        assertEquals(10, counter);
    }

    @Test
    public void tryMoveOnAnimal(){
        GrassField map = new GrassField(10, "test_map_0");
        assertAll(()->map.place(new Animal(new Vector2d(2,2))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2d(2,2))));
    }
}
