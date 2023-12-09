package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {

    @Test
    public void testEquals(){
        Vector2d v = new Vector2d(1,1);
        assertEquals(v, v);
        assertEquals(v, new Vector2d(1,1));
        assertNotEquals(v, new Vector2d(-1, -1));
        assertNotEquals(v, new Vector2d(1, -1));
        assertNotEquals(v, new Vector2d(-1, 1));
        assertNotEquals(v, new Vector2d(0, 0));
        assertNotEquals(v, new Vector2d(2, 2));
        assertNotEquals(v, MapDirection.EAST);
        assertNotEquals(v, null);
        assertNotEquals(v, new Object());
        assertNotEquals(v, -1);
        assertNotEquals(v, 0);
        assertNotEquals(v, 1);
        assertNotEquals(v, true);
        assertNotEquals(v, false);
    }

    @Test
    public void testToString(){
        assertEquals("(1,1)", new Vector2d(1, 1).toString());
        assertEquals("(-1,1)", new Vector2d(-1, 1).toString());
        assertEquals("(1,-1)", new Vector2d(1, -1).toString());
        assertEquals("(-1,-1)", new Vector2d(-1, -1).toString());
    }

    @Test
    public void testPrecedes(){
        assertTrue(new Vector2d(1,1).precedes(new Vector2d(2,2)));
        assertTrue(new Vector2d(-1,1).precedes(new Vector2d(2,2)));
        assertTrue(new Vector2d(1,-1).precedes(new Vector2d(2,2)));
        assertTrue(new Vector2d(-1,-1).precedes(new Vector2d(2,2)));
        assertTrue(new Vector2d(1,1).precedes(new Vector2d(1,1)));
        assertFalse(new Vector2d(1,1).precedes(new Vector2d(0,0)));
        assertFalse(new Vector2d(1,1).precedes(new Vector2d(1,0)));
        assertFalse(new Vector2d(1,1).precedes(new Vector2d(0,1)));
    }

    @Test
    public void testFollows(){
        assertFalse(new Vector2d(1,1).follows(new Vector2d(2,2)));
        assertFalse(new Vector2d(-1,1).follows(new Vector2d(2,2)));
        assertFalse(new Vector2d(1,-1).follows(new Vector2d(2,2)));
        assertFalse(new Vector2d(-1,-1).follows(new Vector2d(2,2)));
        assertTrue(new Vector2d(1,1).follows(new Vector2d(1,1)));
        assertTrue(new Vector2d(1,1).follows(new Vector2d(0,0)));
        assertTrue(new Vector2d(1,1).follows(new Vector2d(1,0)));
        assertTrue(new Vector2d(1,1).follows(new Vector2d(0,1)));
    }

    @Test
    public void testUpperRight(){
        assertEquals(new Vector2d(3,4), new Vector2d(3,4).upperRight(new Vector2d(2,3)));
        assertEquals(new Vector2d(3,4), new Vector2d(2,3).upperRight(new Vector2d(3,4)));
        assertEquals(new Vector2d(5,6), new Vector2d(5,3).upperRight(new Vector2d(5,6)));
        assertEquals(new Vector2d(8,7), new Vector2d(4,7).upperRight(new Vector2d(8,7)));
        assertEquals(new Vector2d(3,3), new Vector2d(3,3).upperRight(new Vector2d(3,3)));
    }

    @Test
    public void testLowerLeft(){
        assertEquals(new Vector2d(2,3), new Vector2d(3,4).lowerLeft(new Vector2d(2,3)));
        assertEquals(new Vector2d(2,3), new Vector2d(2,3).lowerLeft(new Vector2d(3,4)));
        assertEquals(new Vector2d(5,3), new Vector2d(5,3).lowerLeft(new Vector2d(5,6)));
        assertEquals(new Vector2d(4,7), new Vector2d(4,7).lowerLeft(new Vector2d(8,7)));
        assertEquals(new Vector2d(3,3), new Vector2d(3,3).lowerLeft(new Vector2d(3,3)));
    }

    @Test
    public void testAdd(){
        assertEquals(new Vector2d(6,8), new Vector2d(2,3).add(new Vector2d(4,5)));
        assertEquals(new Vector2d(1,5), new Vector2d(2,3).add(new Vector2d(-1,2)));
        assertEquals(new Vector2d(2,3), new Vector2d(2,3).add(new Vector2d(0,0)));
        assertEquals(new Vector2d(-98,-97), new Vector2d(2,3).add(new Vector2d(-100,-100)));
    }

    @Test
    public void testSubtract(){
        assertEquals(new Vector2d(-2,-2), new Vector2d(2,3).subtract(new Vector2d(4,5)));
        assertEquals(new Vector2d(3,1), new Vector2d(2,3).subtract(new Vector2d(-1,2)));
        assertEquals(new Vector2d(2,3), new Vector2d(2,3).subtract(new Vector2d(0,0)));
        assertEquals(new Vector2d(102,103), new Vector2d(2,3).subtract(new Vector2d(-100,-100)));
    }

    @Test
    public void testOpposite(){
        assertEquals(new Vector2d(0,0), new Vector2d(0,0).opposite());
        assertEquals(new Vector2d(-1,0), new Vector2d(1,0).opposite());
        assertEquals(new Vector2d(0,-1), new Vector2d(0,1).opposite());
        assertEquals(new Vector2d(-1,-1), new Vector2d(1,1).opposite());
        assertEquals(new Vector2d(1,0), new Vector2d(-1,0).opposite());
        assertEquals(new Vector2d(0,1), new Vector2d(0,-1).opposite());
        assertEquals(new Vector2d(1,1), new Vector2d(-1,-1).opposite());
    }
}
