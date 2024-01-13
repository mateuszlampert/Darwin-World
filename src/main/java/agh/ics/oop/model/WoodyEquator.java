package agh.ics.oop.model;

import java.util.*;

public class WoodyEquator extends AbstractPlantGrowing{
    private final int equatorHeight;

    public WoodyEquator(int width, int height, int plantsToGrow){
        super(width, height, plantsToGrow);
        this.equatorHeight = (int) (0.2 * height);
    }

    @Override
    public Set<Vector2d> getFavourablePositions(Map<Vector2d, Grass> grasses) {
        Set<Vector2d> favourablePositions = new HashSet<>();

        for (int i = 0; i < width; i++){
            for (int j = (height / 2) - equatorHeight; j <= (height / 2) + equatorHeight; j++){
                favourablePositions.add(new Vector2d(i, j));
            }
        }

        return favourablePositions;
    }
}