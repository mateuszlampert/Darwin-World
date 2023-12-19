package agh.ics.oop.model;

public class InvalidPositionException extends Exception{
    private static final String POSITION = "Position ";
    private static final String IS_INVALID = " is invalid for the map!";
    private final String positionString;

    public InvalidPositionException(Vector2d vec){
        positionString = vec.toString();
    }

    @Override
    public String toString() {
        return POSITION + positionString + IS_INVALID;
    }
}
