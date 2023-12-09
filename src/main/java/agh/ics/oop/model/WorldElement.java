package agh.ics.oop.model;

public interface WorldElement {
    public Vector2d getPosition();

    private void setPosition(){};

    public String toString();
    public void move(MoveValidator validator, MoveDirection direction);
}
