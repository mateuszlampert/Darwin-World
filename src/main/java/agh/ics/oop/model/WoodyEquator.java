package agh.ics.oop.model;

import java.util.*;

public class WoodyEquator extends AbstractPlantGrowing{
    private final int equatorHeight;

    public WoodyEquator(int width, int height, int plantsToGrow, int plantsEnergy){
        super(width, height, plantsToGrow, plantsEnergy);
        this.equatorHeight = (int) Math.ceil(0.1 * height);
//        System.out.println(equatorHeight);
//        System.out.println((height / 2) - equatorHeight);
    }

    public Set<Vector2d> getFavourablePositions(MoveValidator validator) {
        Set<Vector2d> favourablePositions = new HashSet<>();

        for (int i = 0; i < width; i++){
            for (int j = (height / 2) - equatorHeight; j <= (height / 2) + equatorHeight; j++){
                Vector2d pos = new Vector2d(i, j);
//                System.out.println(pos);
                if (validator.canMoveTo(pos) && !grasses.containsKey(pos)){
                    favourablePositions.add(pos);
                }
            }
        }
//        System.out.println(favourablePositions);
        return favourablePositions;
    }
}