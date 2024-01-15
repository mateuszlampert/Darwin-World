package agh.ics.oop.model;

import agh.ics.oop.SimulationSettings;

import java.util.*;

import static agh.ics.oop.model.MoveDirection.FORWARD;

public class MapHandler implements DeathListener{
    private final WorldMap map;
    private final SimulationSettings simulationSettings;
    private List<Animal> aliveAnimals = new ArrayList<Animal>();
    private List<Animal> dyingAnimals = new LinkedList<>(); // animals that have <0 energy, but not yet removed
    private List<Animal> deadAnimals = new ArrayList<>();
    private MapStatisticsHandler statisticsHandler = new MapStatisticsHandler();

    public MapHandler(WorldMap map, SimulationSettings simulationSettings){
        this.map = map;
        this.simulationSettings = simulationSettings;
        simulationSettings.mutation().setMutationRange(simulationSettings.minMutations(), simulationSettings.maxMutations());
    }

    public void placeAnimal(Animal animal){
        try {
            map.placeAnimal(animal);
            aliveAnimals.add(animal);
            animal.listenForDeath(this);
            statisticsHandler.animalBorn(animal);
        } catch (InvalidPositionException e){
            System.out.println(e.toString());
        }
    }

    public void removeDead(){
        for (Animal animal: dyingAnimals){
            aliveAnimals.remove(animal);
            map.removeAnimal(animal);
            deadAnimals.add(animal);
            statisticsHandler.animalDied(animal);
        }
        dyingAnimals.clear();
    }

    public void moveAnimals(){
        for(Animal animal : aliveAnimals){
            map.move(animal, FORWARD);
        }
    }

    public void eatGrass(){
        Map<Grass, Animal> animalOnGrasses = map.getAnimalOnGrasses();
        animalOnGrasses.forEach((grass, animal) -> {
            animal.eat(grass);
            map.removeGrass(grass);
            statisticsHandler.animalAte(animal);
        });

    }

    public void reproduce(){
        List<ReproductionPair> pairs = map.getAnimalsToReproduce();

        for(ReproductionPair pair : pairs){
            if(pair.parentEnergyAbove(simulationSettings.energyNeededToReproduce())){
                Animal child = generateChild(pair);
                placeAnimal(child);
                statisticsHandler.pairReproduced(pair, child);
            }

        }
    }

    public void growGrass(){
        map.growGrass();
    }

    public void updateStatistics(){
        statisticsHandler.nextDay();
        for(Animal animal : aliveAnimals){
            statisticsHandler.survivedDay(animal);
        }
    }

    private Animal generateChild(ReproductionPair pair){ //could be moved to separate class to keep code clean
        int reproductionCost = simulationSettings.energyLostToReproduce();

        Vector2d childPosition = pair.getChildPosition();
        MapDirection childDirection = MapDirection.randomDirection();
        int childEnergy = 2*reproductionCost;
        Genome childGenome = new Genome(pair.generateChildGenomeList(), simulationSettings.animalBehavior());

        simulationSettings.mutation().mutate(childGenome);

        Animal child = new Animal(childPosition, childDirection, childEnergy, childGenome);

        pair.decreaseParentsEnergy(reproductionCost);

        return child;
    }

    @Override
    public void animalDied(Animal animal) {
        dyingAnimals.add(animal);
    }

    public List<Animal> getMapAnimals(){
        return this.aliveAnimals;
    }
}