package agh.ics.oop.model;

import java.util.*;

import static agh.ics.oop.model.MoveDirection.FORWARD;

public class MapHandler implements DeathListener{
    private final WorldMap map;
    private List<Animal> aliveAnimals = new ArrayList<Animal>();
    private List<Animal> dyingAnimals = new LinkedList<>(); // animals that have <0 energy, but not yet removed
    private List<Animal> deadAnimals = new ArrayList<>();

    public MapHandler(WorldMap map){
        this.map = map;
    }

    public void placeAnimal(Animal animal){
        try {
            map.placeAnimal(animal);
            aliveAnimals.add(animal);
            animal.listenForDeath(this);
        } catch (InvalidPositionException e){
            System.out.println(e.toString());
        }
    }

    public void removeDead(){
        for (Animal animal: dyingAnimals){
            aliveAnimals.remove(animal);
            map.removeAnimal(animal);
            deadAnimals.add(animal);
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
        });

    }

    public void reproduce(){

    }

    public void growGrass(){

    }

    @Override
    public void animalDied(Animal animal) {
        dyingAnimals.add(animal);
    }

    public List<Animal> getMapAnimals(){
        return this.aliveAnimals;
    }
}