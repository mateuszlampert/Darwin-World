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
            map.place(animal);
            mapAnimals.add(animal);
        } catch (PositionAlreadyOccupiedException e){
            System.out.println(e.toString());
            //continue
        }
    }

    public void removeDead(){

    }

    public void moveAnimals(){
        for(Animal animal : mapAnimals){
            animal.rotate();
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
