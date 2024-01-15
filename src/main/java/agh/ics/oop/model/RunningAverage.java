package agh.ics.oop.model;

public class RunningAverage {
    private int sum;
    private int count;

    public RunningAverage() {
        sum = 0;
        count = 0;
    }

    public void addNumber(int number) {
        sum += number;
        count++;
    }

    public void increaseSum(int number){
        sum += number;
    }

    public void removeNumber(int number){
        sum -= number;
        count--;
    }

    public void reset(){
        sum = 0;
        count = 0;
    }

    public double getAverage() {
        if (count == 0) {
            return 0;
        }
        return (double) sum / count;
    }
}
