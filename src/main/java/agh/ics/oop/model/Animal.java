package agh.ics.oop.model;

public class Animal implements WorldElement{
    private MapDirection direction;
    private Vector2d position;

    public Animal(){
        this.position = new Vector2d(2,2);
        this.direction = MapDirection.NORTH;
    }
    public Animal(Vector2d position){
        this.position = position;
        this.direction = MapDirection.NORTH;
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

    public String toString() {
        return switch (direction){
            case NORTH -> "^";
            case SOUTH -> "v";
            case EAST -> ">";
            case WEST -> "<";
        };
    }

    public boolean isAt(Vector2d position){
        return this.getPosition().equals(position);
    }
    public boolean isFacing(MapDirection direction){ // tej metody nie bylo w instrukcjach, ale dodalem ja aby ulatwic testowanie
        return this.getDirection().equals(direction);
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
                Vector2d v2 = this.getDirection().toUnitVector();
                Vector2d newPosition = v1.add(v2);
                if (validator.canMoveTo(newPosition)){
                    this.setPosition(newPosition);
                }
            }
            case BACKWARD -> {
                Vector2d v1 = this.getPosition();
                Vector2d v2 = this.getDirection().toUnitVector().opposite();
                Vector2d newPosition = v1.add(v2);
                if (validator.canMoveTo(newPosition)){
                    this.setPosition(newPosition);
                }
            }
        }
    }
}
