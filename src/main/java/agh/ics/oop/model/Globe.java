package agh.ics.oop.model;

import agh.ics.oop.SimulationSettings;

public class Globe extends AbstractWorldMap{

    private final int width;
    private final int height;
    private final SimulationSettings configuration;


    public Globe(String mapId, int width, int height, SimulationSettings configuration){
        super(mapId);
        this.width = width;
        this.height = height;
        this.configuration = configuration;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return false;
    }

    @Override
    public Boundary getCurrentBounds() {
        return null;
    }
}
