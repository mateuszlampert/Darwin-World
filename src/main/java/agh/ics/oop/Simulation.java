package agh.ics.oop;

import agh.ics.oop.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable{
  private final List<Animal> animals = new ArrayList<>();
  private final List<MoveDirection> moves;
  private final WorldMap map;
  public Simulation(WorldMap map, List<MoveDirection> moves, List<Vector2d> positions){
        this.map = map;
        this.moves = moves;
        for (Vector2d position : positions) {
            Animal animal = new Animal(position);
            try {
                map.place(animal);
                this.animals.add(animal);
            } catch (PositionAlreadyOccupiedException e){
                System.out.println(e.toString());
                //continue
            }
        }
    }

    public List<Animal> getAnimals(){
        List<Animal> safeAnimals = Collections.unmodifiableList(this.animals);
        return safeAnimals;
    }

    private List<MoveDirection> getMoves(){
        return this.moves;
    }

    @Override
    public void run(){
        List<Animal> animals = this.getAnimals();
        List<MoveDirection> moves = this.getMoves();
        int animal_count = animals.size();

        for(int i=0; i < moves.size(); i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            int animal_index = i % animal_count;

            Animal animal = animals.get(animal_index);
            MoveDirection move = moves.get(i);

            map.move(animal, move);
        }
    }
}
