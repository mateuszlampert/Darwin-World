package agh.ics.oop.model;

import java.util.Random;

public class LittleMadness implements AnimalBehavior{

    @Override
    public int nextMove(int curr, int genomeLength){
        if (Math.random() < 0.8){
            return (curr + 1) % genomeLength;
        }
        else{
            int next = (int) (Math.random()*(genomeLength));

            while (next != curr){
                next = (int) (Math.random()*(genomeLength));
            }

            return next;
        }
    }
}
