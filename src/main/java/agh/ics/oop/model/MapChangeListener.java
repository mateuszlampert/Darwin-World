package agh.ics.oop.model;

public interface MapChangeListener {

    void mapChanged(WorldMap worldMap, String message);

    void simulationStatsUpdated(MapStatisticsHandler statisticsHandler,String message);
}
