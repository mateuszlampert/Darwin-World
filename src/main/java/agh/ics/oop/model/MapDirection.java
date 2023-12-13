package agh.ics.oop.model;

import java.util.Map;

public enum MapDirection {
    NORTH(new Vector2d(0,1), "POLNOC"),
    NORTH_WEST(new Vector2d(-1, 1), "POLNOCNY ZACHOD"),
    WEST(new Vector2d(-1,0), "ZACHOD"),
    SOUTH_WEST(new Vector2d(-1, -1), "POLUDNIOWY ZACHOD"),
    SOUTH(new Vector2d(0,-1), "POLUDNIE"),
    SOUTH_EAST(new Vector2d(1, -1), "POLUDNIOWY WSCHOD"),
    EAST(new Vector2d(1,0), "WSCHOD"),
    NORTH_EAST(new Vector2d(1, 1), "POLNOCNY WSCHOD");

    private final Vector2d moveVector;
    private final String directionString;

    private MapDirection(Vector2d moveVector, String directionString){
        this.moveVector = moveVector;
        this.directionString = directionString;
    }

    public MapDirection rotate(int rotation){
        return MapDirection.values()[(ordinal() + rotation) % 8];
    }

    public String toString() {
        return this.directionString;
    }

//    TO REMOVE LATER
    public MapDirection next() {
        return rotate(1);
    }

//    TO REMOVE LATER
    public MapDirection previous() {
        return rotate(7);
    }

    public Vector2d toMoveVector(){
        return this.moveVector;
    }
}
