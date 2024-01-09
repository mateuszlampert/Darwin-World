package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractPlantGrowing implements PlantGrowing {
    protected final int width;
    protected final int height;
    protected final int favourablePlantsToGrow;
    protected final int plantsToGrow;
    protected MapDirection currDirection = MapDirection.NORTH;

    protected AbstractPlantGrowing(int width, int height, int plantsToGrow) {
        this.width = width;
        this.height = height;
        this.favourablePlantsToGrow = (int) (Math.random() * plantsToGrow);
        this.plantsToGrow = plantsToGrow;
    }

    public Set<Vector2d> generateGrassPositions(Map<Vector2d, Grass> grasses) {
        Set<Vector2d> newGrassPositions = new HashSet<>();
        Set<Vector2d> favourablePositions = getFavourablePositions(grasses);

        List<Vector2d> favourablePositionsList = new ArrayList<>(favourablePositions);
        int taken = 0;

        for (int i = 0; i < favourablePlantsToGrow && i < favourablePositionsList.size(); i++){
            int toSwap = (int) (Math.random()*(width*height - i) + i);
            Collections.swap(favourablePositionsList, toSwap, i);
            newGrassPositions.add(favourablePositionsList.get(i));
            taken += 1;
        }

        List<Vector2d> unfavourablePositionsList = new ArrayList<>(width*height - grasses.size() - favourablePositions.size());

        for (int i = 0; i < width; i++){
            for (int j = 0; j < height; j++){
                Vector2d currPosition = new Vector2d(i, j);
                if (!(grasses.containsKey(currPosition) || favourablePositions.contains(currPosition))){
                    unfavourablePositionsList.add(currPosition);
                }
            }
        }

        for (int i = 0; i < this.plantsToGrow - taken && i < unfavourablePositionsList.size(); i++){
            int toSwap = (int) (Math.random()*(width*height - i) + i);
            Collections.swap(unfavourablePositionsList, toSwap, i);
            newGrassPositions.add(unfavourablePositionsList.get(i));
        }

        return newGrassPositions;
    }
}
