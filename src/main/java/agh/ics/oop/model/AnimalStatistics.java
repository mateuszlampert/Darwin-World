package agh.ics.oop.model;

public class AnimalStatistics {
    private int age = 0;
    private int childrenCount = 0;

    public void incrementAge(){
        age +=1;
    }
    public void incrementChildrenCount(){
        childrenCount+=1;
    }

    public int getAge(){
        return age;
    }
    public int getChildrenCount(){
        return childrenCount;
    }


}
