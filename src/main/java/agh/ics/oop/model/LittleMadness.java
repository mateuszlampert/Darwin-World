package agh.ics.oop.model;

import java.util.Random;

public class LittleMadness implements AnimalBehavior{

    @Override
    public int nextMove(int lastMove, int genomeLength){
        if (Math.random() < 0.8){
            return (lastMove + 1) % genomeLength;
        }
        else{
            int next = (int) (Math.random()*(genomeLength));

            while (next != lastMove){
                next = (int) (Math.random()*(genomeLength));
            }

            return next;
        }
    }
}
