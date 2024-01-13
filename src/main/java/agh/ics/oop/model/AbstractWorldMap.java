package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;
import agh.ics.oop.SimulationSettings;

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
        ArrayList<WorldElement> animalsAtPos = animals.get(pos);
        animalsAtPos.remove(animal);
        if(animalsAtPos.isEmpty()){ // freeing memory of empty list
            animals.remove(pos);
        }
        mapChanged("Animal at " + animal.getPosition() +" removed(died)!");
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
        mapChanged("Animal at " + obj.getPosition() +" moved!");
    }

    @Override
    public void placeGrass(Grass grass) throws InvalidPositionException, PositionAlreadyOccupiedException{
        Vector2d grassPos = grass.getPosition();
        if(grasses.get(grassPos) != null){
            throw new PositionAlreadyOccupiedException(grassPos);
        }
        else if(!canMoveTo(grassPos)){
            throw new InvalidPositionException(grassPos);
        }
        grasses.put(grassPos, grass);
        System.out.println(grasses);
        mapChanged("Grass placed at " + grassPos);
    }

    public void removeGrass(Grass grass){
        Vector2d grassPos = grass.getPosition();
        grasses.remove(grassPos);
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
    public List getAnimals() {
        List list = new ArrayList<>();
        list.addAll(animals.values());
        return list;
    }

    @Override
    public Map<Grass, Animal> getAnimalOnGrasses(){
        HashMap<Grass, Animal> animalOnGrasses = new HashMap<>();
        for(WorldElement grass : grasses.values()){
            WorldElement topAnimal = getBestAnimalAt(grass.getPosition());
            if(topAnimal != null){
                animalOnGrasses.put((Grass) grass, (Animal) topAnimal);
            }
        }
        return animalOnGrasses;
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
