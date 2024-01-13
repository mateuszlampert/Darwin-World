package agh.ics.oop.model;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private static final int MOVEMENT_ENERGY_LOSS = 1;
    private MapDirection direction;
    private Vector2d position;
    private final Genome genome;
    private int energy;

    private List<DeathListener> deathListeners = new ArrayList<>();

    public Animal(int startingEnergy){
        this.position = new Vector2d(2,2);
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(new ArrayList<>(), new FullPredestination());
        this.energy = startingEnergy;
    }

    public Animal(Vector2d position, int genomeLength, int startingEnergy){
        this.position = position;
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(genomeLength, new FullPredestination());
    }

    public Animal(Vector2d position, MapDirection direction, int startingEnergy, Genome genome){
        this.energy = startingEnergy;
        this.genome = genome;
        this.direction = direction;
        this.position = position;
    }

    private void decreaseEnergy(int amount){
        this.energy -= amount;
        checkIfAlive();
    }

    private void checkIfAlive(){
        if(this.energy < 0){
            for(DeathListener listener : deathListeners){
                listener.animalDied(this);
            }
        }
    }

    public void eat(Grass plant){
        this.energy += plant.getCalories();
    }

    public int getEnergy() {
        return this.energy;
    }

    public void listenForDeath(DeathListener listener){
        deathListeners.add(listener);
    }

    public Vector2d getPosition() {
        return this.position;
    }
    public MapDirection getDirection() {
        return this.direction;
    }
    public boolean isAt(Vector2d position){
        return this.getPosition().equals(position);
    }
    public boolean isFacing(MapDirection direction){ // tej metody nie bylo w instrukcjach, ale dodalem ja aby ulatwic testowanie
        return this.getDirection().equals(direction);
    }

    public void rotate(){
        int rotationDelta = genome.next();
        this.direction = direction.rotate(rotationDelta);
    }

    public void move(MoveDeterminer determiner){
        rotate();
        AnimalState afterMove = determiner.determineMove(position, direction);
        setPosition(afterMove.position());
        setDirection(afterMove.direction());
    }

    private void setPosition(Vector2d newPosition){
        this.position = newPosition;
    }
    private void setDirection(MapDirection newDirection){this.direction = newDirection; }

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

