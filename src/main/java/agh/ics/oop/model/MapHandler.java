package agh.ics.oop.model;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static agh.ics.oop.model.MoveDirection.FORWARD;

public class MapHandler {
    private final AbstractWorldMap map;
    private List<Animal> mapAnimals = new ArrayList<Animal>();

    public MapHandler(AbstractWorldMap map){
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
        for (Animal animal: mapAnimals){
            if (animal.getEnergy() <= 0){
                map.removeMovable(animal);
                mapAnimals.remove(animal);
            }
        }
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