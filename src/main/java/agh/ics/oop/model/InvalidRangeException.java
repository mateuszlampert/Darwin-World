package agh.ics.oop.model;

public class InvalidRangeException extends Exception{
    private final String datatype;

    public InvalidRangeException(String datatype){
        this.datatype = datatype;
    }

    @Override
    public String toString() {return datatype + " - invalid range. Minimum number cannot be higher than maximum"; }
}
