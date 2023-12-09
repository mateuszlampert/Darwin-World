package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

import java.util.*;

//dziala dla liczb n<1000
public class RandomVector2dGenerator implements Iterator<Vector2d> {

    private final int size;
    private int currentIndex;
    private List<Integer> list1D;
    public RandomVector2dGenerator(int size){
        this.size = size;
        this.currentIndex = 0;
        this.list1D = this.GenerateShuffled1DList();
    }

    private List<Integer> GenerateShuffled1DList(){
        List<Integer> list = new ArrayList<>(this.size);
        for(int i=0; i < this.size; i++){
            for(int j=0; j < this.size; j++){
                list.add(i*this.size + j);
            }
        }
        Collections.shuffle(list);
        return list;
    }


    @Override
    public boolean hasNext() {
        return this.currentIndex < this.size*this.size;
    }

    @Override
    public Vector2d next() {
        int randomVector2d = this.list1D.get(currentIndex);
        int randomX = randomVector2d % this.size;
        int randomY = randomVector2d / this.size;
        this.currentIndex++;
        return new Vector2d(randomX, randomY);
    }
}
