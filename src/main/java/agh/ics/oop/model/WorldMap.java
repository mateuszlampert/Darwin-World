package agh.ics.oop.model;

import java.util.List;
import java.util.Map;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    String getId();

    void placeAnimal(Animal animal) throws InvalidPositionException;
    void removeAnimal(Animal animal);
    void placeGrass(Grass grass) throws PositionAlreadyOccupiedException, InvalidPositionException;
    void removeGrass(Grass grass);

    void move(Animal obj, MoveDirection direction);

    boolean isOccupied(Vector2d position);

    WorldElement objectAt(Vector2d position);

    List getAnimals();
    Map<Grass, Animal> getAnimalOnGrasses();

    Boundary getCurrentBounds();

    List<ReproductionPair> getAnimalsToReproduce();
}