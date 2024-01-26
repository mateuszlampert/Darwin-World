package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement{
    private static final int MOVEMENT_ENERGY_LOSS = 1;
    private MapDirection direction;
    private Vector2d position;
    private final Genome genome;
    private final int maxEnergy;
    private int energy;
    private final AnimalStatistics animalStatistics = new AnimalStatistics();

    private final List<DeathListener> deathListeners = new ArrayList<>();

    public Animal(int startingEnergy){
        this.position = new Vector2d(2,2);
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(new ArrayList<>(), new FullPredestination());
        this.maxEnergy = startingEnergy;
        this.energy = startingEnergy;
    }

    public Animal(Vector2d position, int genomeLength, int startingEnergy){
        this.position = position;
        this.direction = MapDirection.NORTH;
        this.genome = new Genome(genomeLength, new FullPredestination());
        this.energy = startingEnergy;
        this.maxEnergy = startingEnergy;
    }

    public Animal(Vector2d position, MapDirection direction, int startingEnergy, Genome genome){
        this.energy = startingEnergy;
        this.genome = genome;
        this.direction = direction;
        this.position = position;
        this.maxEnergy = startingEnergy;
    }

    public void decreaseEnergy(int amount){
        this.energy -= amount;
        checkIfAlive();
    }

    private void checkIfAlive(){ // ta metoda jest wywoływana tylko w jednym miejscu; obie metody są na tyle, krókie, że lepiej je połączyć
        if(this.energy <= 0){
            for(DeathListener listener : deathListeners){
                listener.animalDied(this);
            }
        }
    }

    public void eat(Grass plant){
        this.energy += plant.getCalories();
    }
    public void listenForDeath(DeathListener listener){
        deathListeners.add(listener);
    }

    public int getEnergy() {
        return this.energy;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    public MapDirection getDirection() {
        return this.direction;
    }
    public Genome getGenome(){
        return this.genome;
    }
    public boolean isAt(Vector2d position){
        return this.getPosition().equals(position);
    }
    public boolean isFacing(MapDirection direction){ // tej metody nie bylo w instrukcjach, ale dodalem ja aby ulatwic testowanie
        return this.getDirection().equals(direction);
    }
    public AnimalStatistics getStatisticsHandler(){
        return this.animalStatistics;
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
        decreaseEnergy(MOVEMENT_ENERGY_LOSS);
    }

    private void setPosition(Vector2d newPosition){
        this.position = newPosition;
    }
    private void setDirection(MapDirection newDirection){this.direction = newDirection; }

    public String toString() {
        return "\uD83D\uDC12";
    }

    public AnimalStatistics getAnimalStatistics(){
        return animalStatistics;
    }

    public double getHealthPercentage(){
        return (double) Math.min(this.energy, this.maxEnergy) / this.maxEnergy;
    }
}

