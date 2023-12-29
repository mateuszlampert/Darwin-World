package agh.ics.oop.model;

import java.util.Map;
import java.util.Set;

public interface PlantGrowing{
    Set<Vector2d> generateGrassPositions(Map<Vector2d, Grass> grasses);

    Set<Vector2d> getFavourablePositions(Map<Vector2d, Grass> grasses);
}
