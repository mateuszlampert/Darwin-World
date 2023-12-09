package agh.ics.oop.model;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public static final Vector2d UNIT_VECTOR_NORTH = new Vector2d(0,1);
    public static final Vector2d UNIT_VECTOR_EAST = new Vector2d(1,0);
    public static final Vector2d UNIT_VECTOR_SOUTH = new Vector2d(0,-1);
    public static final Vector2d UNIT_VECTOR_WEST = new Vector2d(-1,0);

    public String toString() {
        return switch(this){
            case NORTH -> "Polnoc";
            case SOUTH -> "Poludnie";
            case WEST -> "Zachod";
            case EAST -> "Wschod";
        };
    }

    public MapDirection next(){
        return switch(this){
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous(){
        return switch(this){
            case NORTH -> WEST;
            case EAST -> NORTH;
            case SOUTH -> EAST;
            case WEST -> SOUTH;
        };
    }

    public Vector2d toUnitVector(){
        return switch (this){
            case NORTH -> UNIT_VECTOR_NORTH;
            case EAST -> UNIT_VECTOR_EAST;
            case SOUTH -> UNIT_VECTOR_SOUTH;
            case WEST -> UNIT_VECTOR_WEST;
        };
    }
}
