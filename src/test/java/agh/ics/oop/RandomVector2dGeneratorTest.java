package agh.ics.oop;

import agh.ics.oop.model.Vector2d;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RandomVector2dGeneratorTest {
    @Test
    public void testAreVectorsUnique(){
        int size=20;
        RandomVector2dGenerator generator = new RandomVector2dGenerator(size);
        Set set = new HashSet<Vector2d>();
        for(int i=0; i < size*size; i++){
            set.add(generator.next());
        }
        assertEquals(size*size, set.size());
    }

    @Test
    public void testAreVectorsCorrect(){
        int size = 10;
        RandomVector2dGenerator generator = new RandomVector2dGenerator(size);
        while(generator.hasNext()){
            Vector2d v = generator.next();
            assertTrue(v.getX() < size && v.getX() >= 0);
            assertTrue(v.getY() < size && v.getY() >= 0);
        }
    }

    @Test
    public void testHugeData(){
        int size = 3800;
        RandomVector2dGenerator generator = new RandomVector2dGenerator(size);
        while(generator.hasNext()){
            generator.next();
        }
        assertTrue(true);
    }

    @Test
    public void testHasNext(){
        int size=20;
        RandomVector2dGenerator generator = new RandomVector2dGenerator(size);
        for(int i=0; i < size*size; i++){
            assertTrue(generator.hasNext());
            generator.next();
        }
        assertFalse(generator.hasNext());
    }
}
