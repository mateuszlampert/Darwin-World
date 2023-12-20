package agh.ics.oop.model;

public class FullPredestination implements AnimalBehavior{

    @Override
    public int nextMove(int lastMove, int genomeLength){
        return (lastMove+1) % genomeLength;
    }
}
