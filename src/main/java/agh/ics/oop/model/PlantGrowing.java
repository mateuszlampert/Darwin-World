package agh.ics.oop.model;

import java.util.Map;
import java.util.Set;

public interface PlantGrowing{
    Set<Vector2d> determineGrassPositions(MoveValidator validator);
    Set<Vector2d> getFavourablePositions(MoveValidator validator);
    Set<Vector2d> growGrass(MoveValidator validator);
    void setGrasses(Map<Vector2d, Grass> grasses);
    void setPlantsToGrow(int grasses);
}
