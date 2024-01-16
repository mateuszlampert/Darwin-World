package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;

public class SimulationLabel extends Label {

    private final MapStatisticsHandler mapStatistics;

    public SimulationLabel(MapStatisticsHandler mapStatistics){
        this.mapStatistics = mapStatistics;
    }

    public VBox showStats(){
        VBox stats = new VBox();
        stats.setAlignment(Pos.CENTER);

        Label age = new Label("Simulation age: " + mapStatistics.getSimulationAge());
        Label alive = new Label("Alive animal count: " + mapStatistics.getAliveAnimals());
        Label aliveAndDead = new Label("Every animal count: " + mapStatistics.getAliveAndDeadAnimals());
        Label freeSpace = new Label("Free grass fields: " + "TODO");
        Label mostPopularGenotype = new Label("Most popular genotype: " + mapStatistics.getMostUsedGenome());
        Label averageEnergy = new Label("Average energy: " + mapStatistics.getAverageEnergyLevel());
        Label averageLifespan = new Label("Average lifespan: " + mapStatistics.getAverageLifeSpan());
        Label averageChildrenCount = new Label("Average children count: " + mapStatistics.getAverageChildrenCount());

        stats.getChildren().addAll(age, alive, aliveAndDead, freeSpace, mostPopularGenotype, averageEnergy, averageLifespan, averageChildrenCount);

        return stats;
    }

//    public void registerListener(StatsChangeListener listener){
//        animalStatistics.addListener(listener);
//    }
//
//    public void removeListener(StatsChangeListener listener){
//        animalStatistics.removeListener(listener);
//    }
//
//    public Animal getAnimal(){
//        return this.animal;
//    }
}
