package agh.ics.oop.model;

import java.util.List;

public class Genome {
    private final List<Integer> genes;
    private int currMove = 0;
    private final AnimalBehavior animalBehavior;

    public Genome(List<Integer> genes, AnimalBehavior animalBehavior) {
        this.genes = genes;
        this.animalBehavior = animalBehavior;
    }

    public int getNextMove(){
        currMove = animalBehavior.nextMove(currMove, genes.size());
        return genes.get(currMove);
    }
}
