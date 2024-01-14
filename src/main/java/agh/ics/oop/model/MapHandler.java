package agh.ics.oop.model;

import agh.ics.oop.SimulationSettings;

import java.util.*;

import static agh.ics.oop.model.MoveDirection.FORWARD;

public class MapHandler implements DeathListener{
    private int mapAge = 0;
    private final WorldMap map;
    private final SimulationSettings simulationSettings;
    private List<Animal> aliveAnimals = new ArrayList<Animal>();
    private List<Animal> dyingAnimals = new LinkedList<>(); // animals that have <0 energy, but not yet removed
    private List<Animal> deadAnimals = new ArrayList<>();

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
            animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.BORN, mapAge);
        } catch (InvalidPositionException e){
            System.out.println(e.toString());
        }
    }

    public void removeDead(){
        for (Animal animal: dyingAnimals){
            aliveAnimals.remove(animal);
            map.removeAnimal(animal);
            deadAnimals.add(animal);
            animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.DIED, mapAge);
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
            animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.EATEN_GRASS);
        });

    }

    public void reproduce(){
        List<ReproductionPair> pairs = map.getAnimalsToReproduce();

        for(ReproductionPair pair : pairs){
            if(pair.parentEnergyAbove(simulationSettings.energyNeededToReproduce())){
                handleReproduction(pair);
            }

        }
    }

    public void growGrass(){
        map.growGrass();
    }

    public void updateStatistics(){
        mapAge+=1;
        for(Animal animal : aliveAnimals){
            animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.SURVIVED_DAY);
        }
    }

    private void handleReproduction(ReproductionPair pair){ //could be moved to separate class to keep code clean
        int reproductionCost = simulationSettings.energyLostToReproduce();

        Vector2d childPosition = pair.getChildPosition();
        MapDirection childDirection = MapDirection.randomDirection();
        int childEnergy = 2*reproductionCost;
        Genome childGenome = new Genome(pair.generateChildGenomeList(), simulationSettings.animalBehavior());
        simulationSettings.mutation().mutate(childGenome);

        Animal child = new Animal(childPosition, childDirection, childEnergy, childGenome);
        pair.decreaseParentsEnergy(reproductionCost);

        pair.updateParentStatistics(child);

        placeAnimal(child);
    }

    @Override
    public void animalDied(Animal animal) {
        dyingAnimals.add(animal);
    }

    public List<Animal> getMapAnimals(){
        return this.aliveAnimals;
    }
}