package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class ReproductionPair {
    private Animal animal1;
    private Animal animal2;
    public ReproductionPair(Animal animal1, Animal animal2){
        this.animal1 = animal1;
        this.animal2 = animal2;
    }

    public List<Integer> generateChildGenomeList(){
        int a1Energy = animal1.getEnergy();
        int a2Energy = animal2.getEnergy();

        float genomeSplit = a1Energy / (float)(a1Energy + a2Energy); // percent share of animal 1 genes

        List<Integer> childGenomesValues = new ArrayList<>();

        double choice = Math.random();
        if(choice > 0.5){ // child left genes will be from parent 1 and right from parent 2
            List<Integer> a1Genes = animal1.getGenome().getGenesBefore(genomeSplit);
            List<Integer> a2Genes = animal2.getGenome().getGenesAfter(genomeSplit);
            childGenomesValues.addAll(a1Genes);
            childGenomesValues.addAll(a2Genes);
        }else{ // child left genes will be from parent 2 and right from parent 1
            List<Integer> a2Genes = animal2.getGenome().getGenesBefore(1- genomeSplit);
            List<Integer> a1Genes = animal1.getGenome().getGenesAfter(1 - genomeSplit);
            childGenomesValues.addAll(a2Genes);
            childGenomesValues.addAll(a1Genes);
        }

        return childGenomesValues;
    }

    public boolean parentEnergyAbove(int requiredEnergy){
        return (animal1.getEnergy() >= requiredEnergy && animal2.getEnergy() >= requiredEnergy);
    }

    public void decreaseParentsEnergy(int amount){
        animal1.decreaseEnergy(amount);
        animal2.decreaseEnergy(amount);
    }

    public Animal getParent1(){
        return animal1;
    }
    public Animal getParent2(){
        return animal2;
    }

    public Vector2d getChildPosition(){
        return animal1.getPosition();
    }

}
