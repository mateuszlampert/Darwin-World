package agh.ics.oop.model;

public interface MoveDeterminer extends MoveValidator{
    AnimalState determineMove(Vector2d position, MapDirection direction);
}
