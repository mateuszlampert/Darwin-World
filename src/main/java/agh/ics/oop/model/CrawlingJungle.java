package agh.ics.oop.model;

import java.util.*;

public class CrawlingJungle extends AbstractPlantGrowing{

    public CrawlingJungle(int width, int height, int plantsToGrow) {
        super(width, height, plantsToGrow);
    }

    @Override
    public Set<Vector2d> getFavourablePositions(Map<Vector2d, Grass> grasses){
        Set<Vector2d> favourablePositions = new HashSet<>();

        for (Vector2d position: grasses.keySet()){
            for (int i = 0; i < 8; i++){
                Vector2d nextPosition = position.add(currDirection.toMoveVector());
                currDirection = currDirection.next();
                favourablePositions.add(nextPosition);
            }
        }

        return favourablePositions;
    }
}
