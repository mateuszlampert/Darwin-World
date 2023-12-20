package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.FORWARD;

public class MapHandler {
    private final WorldMap map;
    private List<Animal> mapAnimals = new ArrayList<Animal>();

    public MapHandler(WorldMap map){
        this.map = map;
    }

    public void placeAnimal(Animal animal){
        try {
            map.placeAnimal(animal);
            mapAnimals.add(animal);
        } catch (InvalidPositionException e){
            System.out.println(e.toString());
            return;
        }
    }

    public void removeDead(){
        for (Animal animal: mapAnimals){
            if (animal.getEnergy() <= 0){
                //map.removeAnimal(animal);
                //mapAnimals.remove(animal);
            }
        }
    }

    public void moveAnimals(){
        for(Animal animal : mapAnimals){
            map.move(animal, FORWARD);
        }
    }

    public void eatGrass(){
    }

    public void reproduce(){
    }

    public void growGrass(){
    }

    public List<Animal> getMapAnimals(){
        return this.mapAnimals;
    }
}