package agh.ics.oop.model;  // przerośnięty pakiet - trzeba go podzielić dalej

import java.util.*;

public abstract class AbstractPlantGrowing implements PlantGrowing {
    protected final int width; // szerokość czego?
    protected final int height;
    protected int favourablePlantsToGrow;
    protected int plantsToGrow;
    protected Map<Vector2d, Grass> grasses; // ?
    private final int calories;

    protected AbstractPlantGrowing(int width, int height, int plantsToGrow, int energy) {
        this.width = width;
        this.height = height;
        this.favourablePlantsToGrow = (int) Math.ceil(0.8 * plantsToGrow);
        this.plantsToGrow = plantsToGrow;
        this.calories = energy;
    }

    public void setPlantsToGrow(int plantsToGrow) {
        this.plantsToGrow = plantsToGrow;
        this.favourablePlantsToGrow = (int) Math.ceil(0.8 * plantsToGrow); // chodziło raczej o losowanie; w ten sposób dopóki nie rośnie więcej niż 4 rośliny na rundę, to wszystko rośnie na preferowanych polach
    }

    public void setGrasses(Map<Vector2d, Grass> grasses) {
        this.grasses = grasses;
    }

    public Set<Vector2d> determineGrassPositions(MoveValidator validator) {
        Set<Vector2d> newGrassPositions = new HashSet<>();
        Set<Vector2d> favourablePositions = getFavourablePositions(validator);

        List<Vector2d> favourablePositionsList = new ArrayList<>(favourablePositions);
        int takenFavourable = 0;

        for (int i = 0; i < favourablePlantsToGrow && i < favourablePositionsList.size(); i++) {
            int toSwap = (int) (Math.random() * (favourablePositions.size() - i) + i);
            Collections.swap(favourablePositionsList, toSwap, i);
            newGrassPositions.add(favourablePositionsList.get(i));
            takenFavourable += 1;
        }

        int taken = takenFavourable;

        List<Vector2d> unfavourablePositionsList = new ArrayList<>(width * height - grasses.size() - favourablePositions.size());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Vector2d currPosition = new Vector2d(i, j);
                if (!(grasses.containsKey(currPosition) || favourablePositions.contains(currPosition))) {
                    unfavourablePositionsList.add(currPosition);
                }
            }
        }
        for (int i = 0; i < this.plantsToGrow - taken && i < unfavourablePositionsList.size(); i++) {
            int toSwap = (int) (Math.random() * (unfavourablePositionsList.size() - i) + i);
            Collections.swap(unfavourablePositionsList, toSwap, i);
            newGrassPositions.add(unfavourablePositionsList.get(i));
            taken += 1;
        }

        if (taken < plantsToGrow && takenFavourable < favourablePositionsList.size()) {
            for (int i = takenFavourable; i < favourablePositionsList.size() && taken < plantsToGrow; i++) {
                int toSwap = (int) (Math.random() * (favourablePositions.size() - i) + i);
                Collections.swap(favourablePositionsList, toSwap, i);
                newGrassPositions.add(favourablePositionsList.get(i));
                taken += 1;
            }
        }

        return newGrassPositions;
    }

    public Set<Vector2d> growGrass(MoveValidator validator) {
        Set<Vector2d> newGrassPositions = determineGrassPositions(validator);
        for (Vector2d pos : newGrassPositions) {
            try {
                placeGrass(new Grass(pos, calories), validator);
            } catch (PositionAlreadyOccupiedException PAOE) { // PAOE?
                PAOE.printStackTrace();
                System.out.println("PAOE");
                // to modify later // indeed
            } catch (InvalidPositionException IPE) {
                IPE.printStackTrace();
                System.out.println("IPE");
                // to modify later
            }
        }
        return newGrassPositions;
    }

    public void placeGrass(Grass grass, MoveValidator validator) throws InvalidPositionException, PositionAlreadyOccupiedException {
        Vector2d grassPos = grass.getPosition();
        if (grasses.get(grassPos) != null) {
            throw new PositionAlreadyOccupiedException(grassPos);
        } else if (!validator.canMoveTo(grassPos)) {
            throw new InvalidPositionException(grassPos);
        }
        grasses.put(grassPos, grass);
    }
}