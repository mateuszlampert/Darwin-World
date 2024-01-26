package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javax.swing.*;

public class SimulationLabel extends Label {  // to na pewno część modelu?


    public static VBox showStats(MapStatisticsHandler mapStatistics){
        VBox stats = new VBox();
        stats.setAlignment(Pos.CENTER);

        Label age = new Label("Simulation age: " + mapStatistics.getSimulationAge());
        Label alive = new Label("Alive animal count: " + mapStatistics.getAliveAnimals());
        Label aliveAndDead = new Label("Dead and alive animal count: " + mapStatistics.getAliveAndDeadAnimals());
        Label grassCount = new Label("Grass count: " + mapStatistics.getGrassCount());
        Label freeSpace = new Label("Free fields: " + mapStatistics.getFreeSpace());
        Label mostPopularGenotype = new Label("Most popular genotype: " + mapStatistics.getMostUsedGenome());
        ScrollPane genotypeScroller = new ScrollPane(mostPopularGenotype);
        genotypeScroller.setPrefWidth(30);
        Label averageEnergy = new Label("Average energy: " + String.format("%.2f", mapStatistics.getAverageEnergyLevel()));
        Label averageLifespan = new Label("Average lifespan: " + String.format("%.2f", mapStatistics.getAverageLifeSpan()));
        Label averageChildrenCount = new Label("Average children count: " + String.format("%.2f", mapStatistics.getAverageChildrenCount()));

        stats.getChildren().addAll(age, alive, aliveAndDead,grassCount, freeSpace, genotypeScroller, averageEnergy, averageLifespan, averageChildrenCount);

        return stats;
    }

}
