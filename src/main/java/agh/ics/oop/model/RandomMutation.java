package agh.ics.oop.model;

import java.util.List;

public class RandomMutation implements Mutation{

    int maxMutations;
    int minMutations;

    @Override
    public void setMutationRange(int min, int max) {
        minMutations = min;
        maxMutations = max;
    }
    public void mutate(Genome genome){
        List<Integer> genomeList = genome.getGenesList();

        int mutationCount = (int) (Math.random() * (maxMutations - minMutations)) + minMutations;
        for(int i=0; i < mutationCount; i++){
            int mutationIndex = (int) (Math.random() * genomeList.size());
            int newGene = (int) (Math.random() * 8);
            genomeList.set(mutationIndex, newGene);
        }
    }

}
