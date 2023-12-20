package agh.ics.oop.model;

import java.util.List;

public class Genome {
    private final List<Integer> genes;
    private int prevMove = -1;
    private final AnimalBehavior animalBehavior;

    public Genome(List<Integer> genes, AnimalBehavior animalBehavior) {
        this.genes = genes;
        this.animalBehavior = animalBehavior;
    }

    public int getNextMove(){
        int currMove = animalBehavior.nextMove(prevMove, genes.size());
        prevMove = currMove;
        return genes.get(currMove);
    }
}
