package agh.ics.oop.model;

public class Grass implements WorldElement{
    private final Vector2d position;
    private static final String GRASS_STRING = "*";
    private final int calories;

    public Grass(Vector2d pos, int calories){
        this.position = pos;
        this.calories = calories;
    }

    public Vector2d getPosition(){
        return this.position;
    }

    @Override
    public String toString() {
        return GRASS_STRING;
    }

    @Override
    public void move(MoveValidator validator, MoveDirection direction) {
        return; //grass shouldn't move
    }

    public int getCalories(){
        return this.calories;
    }
}
