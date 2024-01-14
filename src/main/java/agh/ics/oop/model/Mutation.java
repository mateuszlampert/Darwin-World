package agh.ics.oop.model;

public interface Mutation {
    void setMutationRange(int min, int max);
    void mutate(Genome genome);
}
