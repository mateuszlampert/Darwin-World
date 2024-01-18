package agh.ics.oop.model;

public class InvalidDataTypeException extends Exception{
    private String datatype;

    public InvalidDataTypeException(String datatype){
        this.datatype = datatype;
    }

    @Override
    public String toString() {return datatype + " - loaded data format is wrong"; }
}
