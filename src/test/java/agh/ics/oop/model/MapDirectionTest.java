package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {

    @Test
    public void testNext(){
        assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.next());
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.EAST.next());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.next());
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH_WEST.next());
        assertEquals(MapDirection.NORTH_WEST, MapDirection.WEST.next());
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_WEST.next());
    }

    @Test
    public void testPrevious(){
        assertEquals(MapDirection.NORTH_WEST, MapDirection.NORTH.previous());
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_EAST.previous());
        assertEquals(MapDirection.NORTH_EAST, MapDirection.EAST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH_EAST.previous());
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_WEST.previous());
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.WEST.previous());
        assertEquals(MapDirection.WEST, MapDirection.NORTH_WEST.previous());
    }
}
