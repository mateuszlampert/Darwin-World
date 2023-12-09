package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap{
    protected final Map<Vector2d, WorldElement> movable = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private String mapId;

    protected AbstractWorldMap(String mapId){
        this.mapId = mapId;
    }


    @Override
    public void move(WorldElement obj, MoveDirection direction) {
        movable.remove(obj.getPosition());
        obj.move(this, direction);
        movable.put(obj.getPosition(), obj);
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
        movable.put(pos, animal);
        mapChanged("Animal placed at " + animal.getPosition());
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public WorldElement objectAt(Vector2d position) {
        return movable.get(position);
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
