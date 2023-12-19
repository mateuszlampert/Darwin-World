package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, ArrayList<WorldElement>> animals = new HashMap<>();
    protected final Map<Vector2d, WorldElement> grasses = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final String mapId;

    protected AbstractWorldMap(String mapId){
        this.mapId = mapId;
    }

    @Override
    public void removeAnimal(WorldElement animal){
        Vector2d pos = animal.getPosition();
        ArrayList<WorldElement> movablesAtPos = animals.get(pos);
        movablesAtPos.remove(animal);
        if(movablesAtPos.isEmpty()){ // freeing memory of empty list
            animals.remove(pos);
        }
    }

    private void putAnimal(WorldElement obj){
        Vector2d pos = obj.getPosition();
        ArrayList<WorldElement> movablesAtPos = animals.get(pos);
        if(movablesAtPos == null){ // could replace it with compute if absent call
            movablesAtPos = new ArrayList<>();
            animals.put(pos, movablesAtPos);
        }
        movablesAtPos.add(obj);
    }

    private WorldElement getBestAnimalAt(Vector2d position){ //currently, best is the first animal to get on that position
        ArrayList<WorldElement> movablesAtPos = animals.get(position);
        if(movablesAtPos == null){
            return null;
        }
        return movablesAtPos.get(0);
    }

    @Override
    public void move(Animal obj, MoveDirection direction) {
        removeAnimal(obj);
        obj.move(this);
        putAnimal(obj);
        mapChanged("Object at " + obj.getPosition() +" moved!");
    }

    @Override
    public void placeAnimal(Animal animal) throws InvalidPositionException{
        Vector2d animalPos = animal.getPosition();
        if(!canMoveTo(animalPos)){
            throw new InvalidPositionException(animalPos);
        }
        putAnimal(animal);
        mapChanged("Animal placed at " + animalPos);
    }

    @Override
    public void placeGrass(Grass grass) throws InvalidPositionException, PositionAlreadyOccupiedException{
        Vector2d grassPos = grass.getPosition();
        if(grasses.get(grassPos) != null){
            throw new PositionAlreadyOccupiedException(grassPos);
        }
        if(!canMoveTo(grassPos)){
            throw new InvalidPositionException(grassPos);
        }
        grasses.put(grassPos, grass);
        mapChanged("Grass placed at " + grassPos);

    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return getBestAnimalAt(position);
    }

    @Override
    public List getElements() {
        List list = new ArrayList<>();
        list.addAll(animals.values());
        return list;
    }

    public String getId(){
        return this.mapId;
    }

    @Override
    public String toString(){
        Boundary bounds = this.getCurrentBounds();
        return this.visualizer.draw(bounds.lowerLeft(), bounds.upperRight());
    }

    protected void mapChanged(String message){
        for(MapChangeListener listener : this.mapChangeListeners){
            listener.mapChanged(this, message);
        }
    }


    public void addListener(MapChangeListener listener){
        this.mapChangeListeners.add(listener);
    }
    public void removeListener(MapChangeListener listener){
        this.mapChangeListeners.remove(listener);
    }

}
