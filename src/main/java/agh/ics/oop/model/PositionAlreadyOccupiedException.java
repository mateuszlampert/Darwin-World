package agh.ics.oop.model;

public class PositionAlreadyOccupiedException extends Exception{

    private static final String POSITION = "Position ";
    private static final String IS_ALREADY_OCCUPIED = " is already occupied.";
    private final String positionString;
    public PositionAlreadyOccupiedException(Vector2d vec){
        positionString = vec.toString();
    }

    @Override
    public String toString() {
        return POSITION + positionString + IS_ALREADY_OCCUPIED;
    }
}
