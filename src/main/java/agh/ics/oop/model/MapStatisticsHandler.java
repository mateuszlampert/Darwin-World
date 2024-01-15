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

    public void animalBorn(Animal animal){
        aliveAnimals += 1;
        aliveAndDeadAnimals +=1;
        averageChildrenCount.addNumber(0);
        usedGenomeCounter.compute(animal.getGenome(), (key, count) -> (count == null) ? 1 : count + 1);
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.BORN, simulationAge);
    }

    public void animalDied(Animal animal){
        aliveAnimals-=1;
        averageChildrenCount.removeNumber(animal.getStatisticsHandler().getChildrenCount());
        averageDeadLifeSpan.addNumber(animal.getStatisticsHandler().getAge());
        usedGenomeCounter.compute(animal.getGenome(), (key, count) -> count - 1);
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.DIED, simulationAge);
    }

    public void animalAte(Animal animal){
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.EATEN_GRASS);
    }

    public void pairReproduced(ReproductionPair pair, Animal child){
        pair.getParent1().getStatisticsHandler().updateStatistics(AnimalStatsUpdate.CHILD_BORN, child);
        averageChildrenCount.increaseSum(2); // adding two, because each parent child count increased by 1
    }

    public void survivedDay(Animal animal){
        animal.getStatisticsHandler().updateStatistics(AnimalStatsUpdate.SURVIVED_DAY);
        averageEnergyLevel.addNumber(animal.getEnergy());
    }

    public void nextDay(){
        simulationAge+=1;
        averageEnergyLevel.reset();
    }

}
