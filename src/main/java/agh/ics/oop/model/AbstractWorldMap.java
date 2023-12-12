package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, ArrayList<WorldElement>> movable = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private String mapId;

    protected AbstractWorldMap(String mapId){
        this.mapId = mapId;
    }

    private void removeMovable(WorldElement obj){
        Vector2d pos = obj.getPosition();
        ArrayList<WorldElement> movablesAtPos = movable.get(pos);
        movablesAtPos.remove(obj);
        if(movablesAtPos.isEmpty()){ // freeing memory of empty list
            movable.remove(pos);
        }
    }

    private void putMovable(WorldElement obj){
        Vector2d pos = obj.getPosition();
        ArrayList<WorldElement> movablesAtPos = movable.get(pos);
        if(movablesAtPos == null){ // could replace it with compute if absent call
            movablesAtPos = new ArrayList<>();
            movable.put(pos, movablesAtPos);
        }
        movablesAtPos.add(obj);
    }

    private WorldElement getBestMovableAt(Vector2d position){ //currently, best is the first animal to get on that position
        ArrayList<WorldElement> movablesAtPos = movable.get(position);
        if(movablesAtPos == null){
            return null;
        }
        return movablesAtPos.get(0);
    }


    @Override
    public void move(WorldElement obj, MoveDirection direction) {
        removeMovable(obj);
        obj.move(this, direction);
        putMovable(obj);
        mapChanged("Object at " + obj.getPosition() +" moved!");
    }

    @Override
    public void place(WorldElement element) throws PositionAlreadyOccupiedException {
        if(element instanceof Animal) place((Animal) element);
    }

    private void place(Animal animal) throws PositionAlreadyOccupiedException {
        Vector2d pos = animal.getPosition();
        if(!canMoveTo(pos)){
            throw new PositionAlreadyOccupiedException(pos);
        }
        //movable.put(pos, animal);
        putMovable(animal);
        mapChanged("Animal placed at " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return getBestMovableAt(position);
    }

    @Override
    public List getElements() {
        List list = new ArrayList<>();
        list.addAll(movable.values());
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
