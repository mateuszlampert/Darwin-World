package agh.ics.oop.model;

public class FullPredestination implements AnimalBehavior{

    @Override
    public int nextMove(int curr, int genomeLength){
        return (curr + 1) % genomeLength;
    }
}
