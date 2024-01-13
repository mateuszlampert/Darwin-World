package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.HashMap;
import java.util.Map;

public class RectangularMap extends AbstractWorldMap{
    private final Vector2d mapBottomLeft;
    private final Vector2d mapTopRight;
    private final Boundary mapBoundary;

    public RectangularMap(int width, int height, String mapId){
        super(mapId);
        this.mapBottomLeft = new Vector2d(0, 0);
        this.mapTopRight = new Vector2d(width-1, height-1);
        this.mapBoundary = new Boundary(this.mapBottomLeft, this.mapTopRight);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        if(!position.precedes(mapTopRight)){
            return false;
        }
        if(!position.follows(mapBottomLeft)){
            return false;
        }

        return true;
    }

    @Override
    public Boundary getCurrentBounds(){
        return this.mapBoundary;
    }

    @Override
    public AnimalState determineMove(Vector2d position, MapDirection direction) {
        return null;
    }
}