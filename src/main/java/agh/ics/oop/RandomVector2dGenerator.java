package agh.ics.oop;

import agh.ics.oop.model.Vector2d;

import java.util.*;

public class RandomVector2dGenerator implements Iterable<Vector2d>{
    private final List<Vector2d> cache;
    int taken = 0;
    public RandomVector2dGenerator(int maxWidth, int maxHeight, int count){
        cache = new ArrayList<>(maxWidth*maxHeight);

        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {
                cache.add(new Vector2d(i, j));
            }
        }
        while (taken < count){
            int toSwap = (int) (Math.random()*(maxHeight*maxWidth - taken) + taken);
            Collections.swap(cache, toSwap, taken);
            taken++;
        }
    }

    @Override
    public Iterator<Vector2d> iterator() {
        return new CustomIterator();
    }

    public class CustomIterator implements Iterator<Vector2d>{
        @Override
        public boolean hasNext() {
            return taken > 0; // nazwa taken jest niejasna w tym kontekście
        }
        @Override
        public Vector2d next() {
            if (hasNext()){
                taken--;
                return cache.get(taken);
            }
            return null; // raczej wyjątek
        }
    }
}