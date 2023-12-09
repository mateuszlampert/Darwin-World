package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import agh.ics.oop.RandomVector2dGenerator;

import java.util.*;

public class GrassField extends AbstractWorldMap{

    private final Map<Vector2d, WorldElement> unmovable = new HashMap<>(); // Grass, ...
    //private final Map<Vector2d, WorldElement> movable = new HashMap<>(); // Animal, ...
    private final int grassMaxCount;
    private final int grassUpperBound;
    public GrassField(int grassMaxCount, String mapId){
        super(mapId);
        this.grassMaxCount = grassMaxCount;
        this.grassUpperBound = (int) Math.sqrt(grassMaxCount * 10);;
        this.generateGrasses(grassMaxCount);
    }
    private void generateGrasses(int amount){
        RandomVector2dGenerator generator = new RandomVector2dGenerator(this.grassUpperBound);
        for(int i = 0; i <amount; i++){
            if(generator.hasNext()){
                Vector2d randomVector = generator.next();
                Grass randomGrass = new Grass(randomVector);
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
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public void place(WorldElement element) throws PositionAlreadyOccupiedException{
        super.place(element);
        if(element instanceof Grass) place((Grass) element);

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
    public List<WorldElement> getElements() {
        List list = new ArrayList<>();
        list.addAll(super.getElements());
        list.addAll(unmovable.values());
        return list;
    }

    @Override
    public Boundary getCurrentBounds(){
        Vector2d lowerLeft = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2d upperRight = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        for(Vector2d v : movable.keySet()){
            lowerLeft = v.lowerLeft(lowerLeft);
            upperRight = v.upperRight(upperRight);
        }
        for(Vector2d v : unmovable.keySet()){
            lowerLeft = v.lowerLeft(lowerLeft);
            upperRight = v.upperRight(upperRight);
        }
        return new Boundary(lowerLeft, upperRight);
    }

}
