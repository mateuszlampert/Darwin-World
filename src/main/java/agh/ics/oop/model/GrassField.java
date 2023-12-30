package agh.ics.oop.model;

import agh.ics.oop.RandomVector2dGenerator;
import agh.ics.oop.SimulationSettings;

import java.util.*;

public class GrassField extends AbstractWorldMap{

    private final Map<Vector2d, WorldElement> unmovable = new HashMap<>(); // Grass, ...
    //private final Map<Vector2d, WorldElement> movable = new HashMap<>(); // Animal, ...
    private final int grassMaxCount;
    private final int grassUpperBound;
    private final SimulationSettings configuration;

    public GrassField(int grassMaxCount, String mapId, SimulationSettings configuration){
        super(mapId);
        this.configuration = configuration;
        this.grassMaxCount = configuration.startingPlants();
        this.grassUpperBound = (int) Math.sqrt(grassMaxCount * 10);;
        this.generateGrasses(grassMaxCount);
    }

    private void generateGrasses(int amount){
        RandomVector2dGenerator generator = new RandomVector2dGenerator(this.grassUpperBound);
        for(int i = 0; i <amount; i++){
            if(generator.hasNext()){
                Vector2d randomVector = generator.next();
                Grass randomGrass = new Grass(randomVector, 10);
                try {
                    place(randomGrass);
                } catch(PositionAlreadyOccupiedException e){
                    System.out.println(e.toString());
                    //continue
                }
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        //return !(objectAt(position) instanceof Animal);
        return true;
    }

    public boolean canReproduce(Animal animal){
        return animal.getEnergy() >= configuration.energyNeededToReproduce();
    }


    private void place(Grass grass) throws PositionAlreadyOccupiedException{
        if(unmovable.size() >= grassMaxCount){
            return;
        }
        Vector2d grassPos = grass.getPosition();
        if(grassPos.getX() < 0 || grassPos.getX() > grassUpperBound){
            return;
        }
        if(grassPos.getY() < 0 || grassPos.getY() > grassUpperBound){
            return;
        }
        if(unmovable.containsKey(grassPos)){ // nie mozemy umiescic trawy na trawie, ale trawe "pod" zwierzakiem juz tak
            throw new PositionAlreadyOccupiedException(grassPos);
        }
        unmovable.put(grassPos, grass);
    }


    @Override
    public WorldElement objectAt(Vector2d position) {
        WorldElement a = super.objectAt(position);
        if(a == null){
            a = unmovable.get(position);
        }
        return a;
    }

    @Override
    public List<WorldElement> getAnimals() {
        List list = new ArrayList<>();
        list.addAll(super.getAnimals());
        list.addAll(unmovable.values());
        return list;
    }

    @Override
    public Boundary getCurrentBounds(){
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(Vector2d v : animals.keySet()){
            lowerLeft = v.lowerLeft(lowerLeft);
            upperRight = v.upperRight(upperRight);
        }
        for(Vector2d v : unmovable.keySet()){
            lowerLeft = v.lowerLeft(lowerLeft);
            upperRight = v.upperRight(upperRight);
        }
        return new Boundary(lowerLeft, upperRight);
    }

    public SimulationSettings getConfiguration() {
        return configuration;
    }
}
