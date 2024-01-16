package agh.ics.oop.model;

import java.util.HashMap;
import java.util.Map;

public class MapStatisticsHandler {

    private int simulationAge = 0;
    private int aliveAnimals = 0;
    private int aliveAndDeadAnimals = 0;
    private RunningAverage averageDeadLifeSpan = new RunningAverage();
    private RunningAverage averageEnergyLevel = new RunningAverage();
    private RunningAverage averageChildrenCount = new RunningAverage();
    private Map<Genome, Integer> usedGenomeCounter = new HashMap<>();
    private Genome topGenome = null;
    private int topGenomeCounter = -1;

    public void animalBorn(Animal animal){
        aliveAnimals += 1;
        aliveAndDeadAnimals +=1;
        averageChildrenCount.addNumber(0);
        changeGenomeFrequency(animal.getGenome(), 1);
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.BORN, simulationAge);
    }

    public void animalDied(Animal animal){
        aliveAnimals-=1;
        averageChildrenCount.removeNumber(animal.getStatisticsHandler().getChildrenCount());
        averageDeadLifeSpan.addNumber(animal.getStatisticsHandler().getAge());
        changeGenomeFrequency(animal.getGenome(), -1);
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.DIED, simulationAge);
    }

    public void animalAte(Animal animal){
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.EATEN_GRASS);
    }

    public void pairReproduced(ReproductionPair pair, Animal child){
        pair.getParent1().getStatisticsHandler().updateStatistics(AnimalStatsUpdate.CHILD_BORN, child);
        pair.getParent2().getStatisticsHandler().updateStatistics(AnimalStatsUpdate.CHILD_BORN, child);
        averageChildrenCount.increaseSum(2); // adding two, because each parent child count increased by 1
    }

    public void survivedDay(Animal animal){
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.SURVIVED_DAY);
        averageEnergyLevel.addNumber(animal.getEnergy());
    }

    public void nextDay(){
        simulationAge+=1;
        averageEnergyLevel.reset();
        System.out.println(usedGenomeCounter);
        System.out.println(topGenome);
        System.out.println(topGenomeCounter);
    }

    private void changeGenomeFrequency(Genome genome, int val){
        Integer genomeCount = usedGenomeCounter.get(genome);
        if(genomeCount == null){
            genomeCount = 0;
            usedGenomeCounter.put(genome, 0);
        }
        int newCount = genomeCount + val;
        usedGenomeCounter.replace(genome, newCount);

        if(genome.equals(topGenome)){
            topGenomeCounter = newCount;
        }

        if(newCount > topGenomeCounter){
            topGenomeCounter = newCount;
            topGenome = genome;
        }
    }

    public int getSimulationAge(){
        return simulationAge;
    }

    public int getAliveAnimals(){
        return aliveAnimals;
    }

    public int getAliveAndDeadAnimals() {
        return aliveAndDeadAnimals;
    }

    public double getAverageLifeSpan(){
        return averageDeadLifeSpan.getAverage();
    }

    public double getAverageEnergyLevel(){
        return averageEnergyLevel.getAverage();
    }

    public double getAverageChildrenCount(){
        return averageChildrenCount.getAverage();
    }

    public Genome getMostUsedGenome(){
        return topGenome;
    }


}
