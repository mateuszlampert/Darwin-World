package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private MapDirection direction;
    private Vector2d position;
    private Genome genome;

    public Animal(){
        this.position = new Vector2d(2,2);
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(new ArrayList<>());
    }
    public Animal(Vector2d position){
        this.position = position;
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(new ArrayList<>());
    }

    public Vector2d getPosition() {
        return this.position;
    }
    private void setPosition(Vector2d position) { // walidacja jest robiona przez validator w metodzie move
        this.position = position;
    }

    public MapDirection getDirection() {
        return this.direction;
    }
    private void setDirection(MapDirection direction) {
        this.direction = direction;
    }


    public boolean isAt(Vector2d position){
        return this.getPosition().equals(position);
    }
    public boolean isFacing(MapDirection direction){ // tej metody nie bylo w instrukcjach, ale dodalem ja aby ulatwic testowanie
        return this.getDirection().equals(direction);
    }

    public void rotate(){
        int rotationDelta = genome.getNextCode();
        this.direction.rotate(rotationDelta);
    }


    public void move(MoveValidator validator,MoveDirection direction){
        switch(direction){
            case RIGHT ->{ //dla RIGHT i LEFT nie trzeba walidowac ruchu bo nie zmieniamy pozycji
                MapDirection newDirection = this.getDirection().next();
                this.setDirection(newDirection);
            }
            case LEFT -> {
                MapDirection newDirection = this.getDirection().previous();
                this.setDirection(newDirection);
            }
            case FORWARD -> {
                Vector2d v1 = this.getPosition();
                Vector2d v2 = this.getDirection().toMoveVector();
                Vector2d newPosition = v1.add(v2);
                if (validator.canMoveTo(newPosition)){
                    this.setPosition(newPosition);
                }
            }
            case BACKWARD -> {
                Vector2d v1 = this.getPosition();
                Vector2d v2 = this.getDirection().toMoveVector().opposite();
                Vector2d newPosition = v1.add(v2);
                if (validator.canMoveTo(newPosition)){
                    this.setPosition(newPosition);
                }
            }
        }
    }

    public String toString() {
        return switch (direction) {
            case NORTH -> "\u2191"; // strzałka w górę
            case NORTH_WEST -> "\u2196"; // strzałka w lewo-górę
            case WEST -> "\u2190"; // strzałka w lewo
            case SOUTH_WEST -> "\u2199"; // strzałka w lewo-dół
            case SOUTH -> "\u2193"; // strzałka w dół
            case SOUTH_EAST -> "\u2198"; // strzałka w prawo-dół
            case EAST -> "\u2192"; // strzałka w prawo
            case NORTH_EAST -> "\u2197"; // strzałka w prawo-górę
        };
    }
}
