package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Genome implements Iterator<Integer> {
    private final List<Integer> genes;
    private int prevMove = -1;
    private final AnimalBehavior animalBehavior;
    private final int genomeLength;

    public Genome(int genomeLength, AnimalBehavior animalBehavior){
        this.animalBehavior = animalBehavior;
        genes = new ArrayList<>(genomeLength);
        this.genomeLength = genomeLength;

        for (int i = 0; i < genomeLength; i++){
            genes.add( (int) (Math.random() * 8));
        }
    }

    public Genome(List<Integer> genes, AnimalBehavior animalBehavior) {
        this.genes = genes;
        this.animalBehavior = animalBehavior;
        this.genomeLength = genes.size();
    }

    public List<Integer> getGenesList(){
        return genes;
    }

    public List<Integer> getGenesBefore(float percent){
        int n = (int) percent * genomeLength;
        return genes.subList(0, n);
    }

    public List<Integer> getGenesAfter(float percent){
        int n = (int) percent * genomeLength;
        return genes.subList(n, genomeLength);
    }

    @Override
    public boolean hasNext() { // using the logic we have, it only doesnt have next when genes.size is equal to 0 which should not happen
        return true;
    }

    @Override
    public Integer next() {
        int currMove = animalBehavior.nextMove(prevMove, genomeLength);
        prevMove = currMove;
        return genes.get(currMove);
    }

    @Override
    public String toString(){
        return genes.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genome genome = (Genome) o;
        return Objects.equals(genes, genome.genes) && Objects.equals(animalBehavior, genome.animalBehavior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genes, animalBehavior);
    }

    public Integer peekNext() {
        return genes.get(animalBehavior.nextMove(prevMove, genomeLength));
    }
}
