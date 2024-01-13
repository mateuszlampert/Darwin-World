package agh.ics.oop.model;

import java.util.*;

public class CrawlingJungle extends AbstractPlantGrowing{

    public CrawlingJungle(int width, int height, int plantsToGrow, int plantsEnergy) {
        super(width, height, plantsToGrow, plantsEnergy);
    }

    @Override
    public Set<Vector2d> getFavourablePositions(){
        Set<Vector2d> favourablePositions = new HashSet<>();
        MapDirection currDirection = MapDirection.NORTH;

        for (Vector2d position: grasses.keySet()){
            for (int i = 0; i < 8; i++){
                Vector2d nextPosition = position.add(currDirection.toMoveVector());
                currDirection = currDirection.next();
                if (!grasses.containsKey(nextPosition)){
                    favourablePositions.add(nextPosition);
                }
            }
        }

        return favourablePositions;
    }
}
