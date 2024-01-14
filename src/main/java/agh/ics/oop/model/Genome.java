package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Genome implements Iterator<Integer> {
    private final List<Integer> genes;
    private int prevMove = -1;
    private final AnimalBehavior animalBehavior;

    public Genome(int genomeLength, AnimalBehavior animalBehavior){
        this.animalBehavior = animalBehavior;
        genes = new ArrayList<>(genomeLength);

        for (int i = 0; i < genomeLength; i++){
            genes.add( (int) (Math.random() * 8));
        }
    }

    public Genome(List<Integer> genes, AnimalBehavior animalBehavior) {
        this.genes = genes;
        this.animalBehavior = animalBehavior;
    }

    public List<Integer> getGenesList(){
        return genes;
    }

    public List<Integer> getGenesBefore(float percent){
        int n = (int) percent * genes.size();
        return genes.subList(0, n);
    }

    public List<Integer> getGenesAfter(float percent){
        int n = (int) percent * genes.size();
        return genes.subList(n, genes.size());
    }

    @Override
    public boolean hasNext() { // using the logic we have, it only doesnt have next when genes.size is equal to 0 which should not happen
        return true;
    }

    @Override
    public Integer next() {
        int currMove = animalBehavior.nextMove(prevMove, genes.size());
        prevMove = currMove;
        return genes.get(currMove);
    }

    @Override
    public String toString(){
        return genes.toString();
    }
}
