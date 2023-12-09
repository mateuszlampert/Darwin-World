package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    NORTH_WEST,
    NORTH_EAST,
    SOUTH,
    SOUTH_WEST,
    SOUTH_EAST,
    WEST,
    EAST;

    public static final Vector2d MOVE_VECTOR_NORTH = new Vector2d(0,1);
    public static final Vector2d MOVE_VECTOR_EAST = new Vector2d(1,0);
    public static final Vector2d MOVE_VECTOR_SOUTH = new Vector2d(0,-1);
    public static final Vector2d MOVE_VECTOR_WEST = new Vector2d(-1,0);
    public static final Vector2d MOVE_VECTOR_NORTH_WEST = new Vector2d(-1, 1);
    public static final Vector2d MOVE_VECTOR_NORTH_EAST = new Vector2d(1, 1);
    public static final Vector2d MOVE_VECTOR_SOUTH_WEST = new Vector2d(-1, -1);
    public static final Vector2d MOVE_VECTOR_SOUTH_EAST = new Vector2d(1, -1);


    public String toString() {
        return switch (this) {
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
            case EAST -> "Wschod";
            case NORTH_WEST -> "Polnocny Zachod";
            case NORTH_EAST -> "Polnocny Wschod";
            case SOUTH_WEST -> "Poludniowy Zachod";
            case SOUTH_EAST -> "Poludniowy Wschod";
        };
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> MapDirection.NORTH_EAST;
            case NORTH_EAST -> MapDirection.EAST;
            case EAST -> MapDirection.SOUTH_EAST;
            case SOUTH_EAST -> MapDirection.SOUTH;
            case SOUTH -> MapDirection.SOUTH_WEST;
            case SOUTH_WEST -> MapDirection.WEST;
            case WEST -> MapDirection.NORTH_WEST;
            case NORTH_WEST -> MapDirection.NORTH;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> MapDirection.NORTH_WEST;
            case NORTH_WEST -> MapDirection.WEST;
            case WEST -> MapDirection.SOUTH_WEST;
            case SOUTH_WEST -> MapDirection.SOUTH;
            case SOUTH -> MapDirection.SOUTH_EAST;
            case SOUTH_EAST -> MapDirection.EAST;
            case EAST -> MapDirection.NORTH_EAST;
            case NORTH_EAST -> MapDirection.NORTH;
        };
    }

    public Vector2d toMoveVector(){
        return switch (this){
            case NORTH -> MOVE_VECTOR_NORTH;
            case NORTH_WEST -> MOVE_VECTOR_NORTH_WEST;
            case NORTH_EAST -> MOVE_VECTOR_NORTH_EAST;
            case EAST -> MOVE_VECTOR_EAST;
            case SOUTH_EAST -> MOVE_VECTOR_SOUTH_EAST;
            case SOUTH -> MOVE_VECTOR_SOUTH;
            case SOUTH_WEST -> MOVE_VECTOR_SOUTH_WEST;
            case WEST -> MOVE_VECTOR_WEST;
        };
    }
}
