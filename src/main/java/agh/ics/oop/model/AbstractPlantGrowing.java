package agh.ics.oop.model;

import java.util.*;

public abstract class AbstractPlantGrowing implements PlantGrowing {
    protected final int width;
    protected final int height;
    protected final int favourablePlantsToGrow;
    protected int plantsToGrow;
    protected Map<Vector2d, Grass> grasses;
    private final int calories;

    protected AbstractPlantGrowing(int width, int height, int plantsToGrow, int energy) {
        this.width = width;
        this.height = height;
        this.favourablePlantsToGrow = (int) (0.8 * plantsToGrow);
        this.plantsToGrow = plantsToGrow;
        this.calories = energy;
    }

    public void setPlantsToGrow(int plantsToGrow){
        this.plantsToGrow = plantsToGrow;
    }

    public void setGrasses(Map<Vector2d, Grass> grasses){
        this.grasses = grasses;
    }

    public Set<Vector2d> determineGrassPositions() {
        Set<Vector2d> newGrassPositions = new HashSet<>();
        Set<Vector2d> favourablePositions = getFavourablePositions();

        List<Vector2d> favourablePositionsList = new ArrayList<>(favourablePositions);
        int taken = 0;

        for (int i = 0; i < favourablePlantsToGrow && i < favourablePositionsList.size(); i++){
            int toSwap = (int) (Math.random()*(favourablePositions.size() - i) + i);
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
            int toSwap = (int) (Math.random()*(unfavourablePositionsList.size() - i) + i);
            Collections.swap(unfavourablePositionsList, toSwap, i);
            newGrassPositions.add(unfavourablePositionsList.get(i));
        }

        return newGrassPositions;
    }

    public Set<Vector2d> growGrass(MoveValidator validator){
        Set<Vector2d> newGrassPositions = determineGrassPositions();
        for (Vector2d pos: determineGrassPositions()){
            try{
                placeGrass(new Grass(pos, calories), validator);
            }
            catch (PositionAlreadyOccupiedException PAOE){
                PAOE.printStackTrace();
                System.out.println("PAOE");
                // to modify later
            }
            catch (InvalidPositionException IPE){
                IPE.printStackTrace();
                System.out.println("IPE");
                // to modify later
            }
        }
        System.out.println(grasses);
        return newGrassPositions;
    }

    public void placeGrass(Grass grass, MoveValidator validator) throws InvalidPositionException, PositionAlreadyOccupiedException{
        Vector2d grassPos = grass.getPosition();
        if(grasses.get(grassPos) != null){
            throw new PositionAlreadyOccupiedException(grassPos);
        }
        else if(!validator.canMoveTo(grassPos)){
            throw new InvalidPositionException(grassPos);
        }
        grasses.put(grassPos, grass);
    }
}