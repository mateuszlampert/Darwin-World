package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsParser {
    public static ArrayList<MoveDirection> Parse(String[] args){
        ArrayList<MoveDirection> out = new ArrayList<>();
        for (String arg : args) {
            switch (arg) {
                case "f","forward" -> out.add(MoveDirection.FORWARD);
                case "b","backward" -> out.add(MoveDirection.BACKWARD);
                case "r","right" -> out.add(MoveDirection.RIGHT);
                case "l","left" -> out.add(MoveDirection.LEFT);
                default -> {throw new IllegalArgumentException(arg + " is not legal move specification");}
            }
        }
        return out;
    }
}
