package agh.ics.oop.model;

import agh.ics.oop.SimulationSettings;

import java.util.*;

public class Globe extends AbstractWorldMap{

    private final SimulationSettings configuration;
    private final Boundary bounds;

    public Globe(String mapId, SimulationSettings configuration){
        super(mapId);

        int width = configuration.width();
        int height = configuration.height();
        this.bounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.configuration = configuration;
        this.plantGrowing = configuration.plantsGrowing();
        this.plantGrowing.setPlantsToGrow(this.configuration.startingPlants());
        this.plantGrowing.setGrasses(this.grasses);
        this.plantGrowing.growGrass(this);
        this.plantGrowing.setPlantsToGrow(this.configuration.plantsPerDay());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(bounds.lowerLeft()) && position.precedes(bounds.upperRight());
    }

    @Override
    public Boundary getCurrentBounds() {
        return bounds;
    }

    public boolean canReproduce(Animal animal){
        return animal.getEnergy() >= configuration.energyNeededToReproduce();
    }

    private void place(Grass grass) throws PositionAlreadyOccupiedException, InvalidPositionException{
        Vector2d grassPos = grass.getPosition();

        if(grasses.containsKey(grassPos)){ // nie mozemy umiescic trawy na trawie, ale trawe "pod" zwierzakiem juz tak
            throw new PositionAlreadyOccupiedException(grassPos);
        }

        else if (grassPos.precedes(bounds.upperRight()) || grassPos.follows(bounds.lowerLeft())){
            grasses.put(grassPos, grass);
        }
        else {
            throw new InvalidPositionException(grassPos);
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement a = super.objectAt(position);
        if(a == null){
            a = grasses.get(position);
        }
        return a;
    }

    public SimulationSettings getConfiguration() {
        return configuration;
    }

    @Override
    public AnimalState determineMove(Vector2d position, MapDirection direction) {
        Vector2d newPosition = position.add(direction.toMoveVector());
        MapDirection newDirection = direction;

        if (!(newPosition.follows(bounds.lowerLeft()) && newPosition.precedes(bounds.upperRight()))){
            return new AnimalState(position, direction);
        }
        else if (newPosition.getY() > bounds.getUpY() || newPosition.getY() < bounds.getDownY()) {
            return new AnimalState(position, direction.rotate(4));
        }
        else{
            if (newPosition.getX() > bounds.getRightX()){
                newPosition = new Vector2d(bounds.getLeftX(), newPosition.getY());
            }
            else if (newPosition.getX() < bounds.getLeftX()){
                newPosition = new Vector2d(bounds.getRightX(), newPosition.getY());
            }
            return new AnimalState(newPosition, direction);
        }
    }
}
