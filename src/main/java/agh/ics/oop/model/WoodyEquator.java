package agh.ics.oop.model;

import java.util.*;

public class WoodyEquator extends AbstractPlantGrowing{
    private final int equatorHeight;

    public WoodyEquator(int width, int height, int plantsToGrow, int plantsEnergy){
        super(width, height, plantsToGrow, plantsEnergy);
        this.equatorHeight = Math.max((int) (0.2 * height), 1);
    }

    public Set<Vector2d> getFavourablePositions(MoveValidator validator) {
        Set<Vector2d> favourablePositions = new HashSet<>();

        for (int i = 0; i < width; i++){
            for (int j = (height / 2) - equatorHeight; j <= (height / 2) + equatorHeight; j++){
                Vector2d pos = new Vector2d(i, j);
                if (validator.canMoveTo(pos)){
                    favourablePositions.add(pos);
                }
            }
        }

        return favourablePositions;
    }
}