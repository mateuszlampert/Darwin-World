package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;

public class AnimalStatistics{

    private int bornOn = 0;
    private int age = 0;
    private int diedOn = 0;
    private int childrenCount = 0;
    private int eatenGrass = 0;
    private final List<Animal> children = new ArrayList<>();
    private final List<StatsChangeListener> statsChangedListeners = new ArrayList<>();

    public void updateStatistics(AnimalStatsUpdate update){
        switch(update) {
            case EATEN_GRASS -> eatenGrass += 1;
            case SURVIVED_DAY -> age += 1;
            default -> throw new IllegalArgumentException("Invalid AnimalStatsUpdate: " + update);
        }
        statsChanged();
    }

    public void updateStatistics(AnimalStatsUpdate update, int ageInformation){
        switch(update){
            case BORN -> bornOn = ageInformation;
            case DIED -> diedOn = ageInformation;
            default -> throw new IllegalArgumentException("Invalid AnimalStatsUpdate: " + update);
        }
        statsChanged();
    }

    public void updateStatistics(AnimalStatsUpdate update, Animal animalInformation){
        switch(update){
            case CHILD_BORN -> {
                childrenCount+=1;
                children.add(animalInformation);
            }
            default -> throw new IllegalArgumentException("Invalid AnimalStatsUpdate: " + update);

        }
        statsChanged();
    }

    public int calculateDescendants(){
        int descendants = getChildrenCount();
        for(Animal child : children){
            descendants += child.getStatisticsHandler().calculateDescendants();
        }
        return descendants;
    }

    public int getAge(){
        return age;
    }
    public int getChildrenCount(){
        return childrenCount;
    }
    public int getBornOn(){
        return bornOn;
    }
    public int getDiedOn(){
        return diedOn;
    }
    public int getEatenGrass(){
        return eatenGrass;
    }

    protected void statsChanged(){
        for(StatsChangeListener listener : this.statsChangedListeners){
            listener.statsChanged(this);
        }
    }

    public void addListener(StatsChangeListener listener){
        this.statsChangedListeners.add(listener);
    }
    public void removeListener(StatsChangeListener listener){
        this.statsChangedListeners.remove(listener);
    }
}
