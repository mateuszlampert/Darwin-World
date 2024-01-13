package agh.ics.oop.model;

import agh.ics.oop.RandomVector2dGenerator;
import agh.ics.oop.SimulationSettings;

import java.util.*;

public class Globe extends AbstractWorldMap{

    private final SimulationSettings configuration;
    private final Boundary bounds;
    private final Map<Vector2d, Animal> animals = new HashMap<>();
    private final Map<Vector2d, Grass> grasses = new HashMap<>();

    public Globe(String mapId, SimulationSettings configuration){
        super(mapId);

        int width = configuration.width();
        int height = configuration.height();
        this.bounds = new Boundary(new Vector2d(0, 0), new Vector2d(width - 1, height - 1));
        this.configuration = configuration;
        generateGrass();
        System.out.print(this.toString());
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getY() >= bounds.getDownY() && position.getY() <= bounds.getUpY();
    }

    public void generateGrass(){
        Set<Vector2d> positions = configuration.plantsGrowing().generateGrassPositions(grasses);
        System.out.println(positions);
        for (Vector2d pos: positions){
            grasses.put(pos, new Grass(pos, configuration.plantsEnergy()));
//            try{
//                placeGrass(new Grass(pos, configuration.plantsEnergy()));
//                System.out.println(1);
//            }
//            catch (PositionAlreadyOccupiedException PAOE){
//                PAOE.printStackTrace();
//                System.out.println("PAOE");
//                // to modify later
//            }
//            catch (InvalidPositionException IPE){
//                IPE.printStackTrace();
//                System.out.println("IPE");
//                // to modify later
//            }
        }
        System.out.println(grasses);
    }

    @Override
    public Boundary getCurrentBounds() {
        return bounds;
    }

    public boolean canReproduce(Animal animal){
        return animal.getEnergy() >= configuration.energyNeededToReproduce();
    }

    private void place(Grass grass) throws PositionAlreadyOccupiedException, InvalidPositionException{
        Vector2d grassPos = grass.getPosition();

        if(grasses.containsKey(grassPos)){ // nie mozemy umiescic trawy na trawie, ale trawe "pod" zwierzakiem juz tak
            throw new PositionAlreadyOccupiedException(grassPos);
        }

        else if (grassPos.precedes(bounds.upperRight()) || grassPos.follows(bounds.lowerLeft())){
            grasses.put(grassPos, grass);
        }
        else {
            throw new InvalidPositionException(grassPos);
        }
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement a = super.objectAt(position);
        if(a == null){
            a = grasses.get(position);
        }
        return a;
    }

    public SimulationSettings getConfiguration() {
        return configuration;
    }
}
