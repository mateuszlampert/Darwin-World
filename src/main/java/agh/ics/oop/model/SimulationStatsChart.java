package agh.ics.oop.model;

import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SimulationStatsChart extends Label {

    private final NumberAxis xAxis = new NumberAxis();
    private final NumberAxis yAxis = new NumberAxis();
    private final LineChart<Number,Number> statsChart = new LineChart<Number,Number>(xAxis,yAxis);

    private final XYChart.Series animalCountSeries = new XYChart.Series();
    private final XYChart.Series averageEnergySeries = new XYChart.Series();
    private final XYChart.Series averageLifespanSeries = new XYChart.Series();
    private final XYChart.Series averageChildrenCountSeries = new XYChart.Series();
    private final XYChart.Series grassCount = new XYChart.Series();
    private final XYChart.Series emptySpaceCount = new XYChart.Series();



    public SimulationStatsChart() {
        xAxis.setLabel("Day of simulation");

        statsChart.setTitle("Simulation statistics chart");
        animalCountSeries.setName("Animal count");
        averageEnergySeries.setName("Average energy");
        averageLifespanSeries.setName("Average lifespan");
        averageChildrenCountSeries.setName("Average children count");
        grassCount.setName("Grass count");
        emptySpaceCount.setName("Empty space count");

        statsChart.getData().addAll(animalCountSeries, averageEnergySeries, averageLifespanSeries, averageChildrenCountSeries, grassCount, emptySpaceCount);
        statsChart.setCreateSymbols(false); // disabling drawing dots on data points
    }

    public void updateSeries(MapStatisticsHandler currentStats){
        int simulationAge = currentStats.getSimulationAge();
        animalCountSeries.getData().add(new XYChart.Data(simulationAge, currentStats.getAliveAnimals()));
        averageEnergySeries.getData().add(new XYChart.Data(simulationAge, currentStats.getAverageEnergyLevel()));
        averageLifespanSeries.getData().add(new XYChart.Data(simulationAge, currentStats.getAverageLifeSpan()));
        averageChildrenCountSeries.getData().add(new XYChart.Data(simulationAge, currentStats.getAverageChildrenCount()));
        grassCount.getData().add(new XYChart.Data(simulationAge, currentStats.getGrassCount()));
        emptySpaceCount.getData().add(new XYChart.Data(simulationAge, currentStats.getFreeSpace()));
    }

    public LineChart getChart(){
        VBox chartVbox = new VBox();
        chartVbox.getChildren().add(statsChart);
        return statsChart;
    }
}
