package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap{
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    protected final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, WorldElement> grasses = new HashMap<>();
    private final List<MapChangeListener> mapChangeListeners = new ArrayList<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final String mapId;

    protected AbstractWorldMap(String mapId){
        this.mapId = mapId;
    }

    @Override
    public void removeAnimal(Animal animal){
        Vector2d pos = animal.getPosition();
        TreeSet<Animal> animalsAtPos = animals.get(pos);
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


    private void putAnimal(Animal obj){
        Vector2d pos = obj.getPosition();
        TreeSet<Animal> animalsAtPosition = animals.computeIfAbsent(pos,k -> new TreeSet<Animal>(ANIMAL_COMPARATOR));
        animalsAtPosition.add(obj);
    }

    private Animal getBestAnimalAt(Vector2d position){
        TreeSet<Animal> animalsAtPosition = animals.get(position);
        if(animalsAtPosition == null){
            return null;
        }
        return animalsAtPosition.first();
    }



    @Override
    public void move(Animal animal, MoveDirection direction) {
        removeAnimal(animal);
        animal.move(this);
        putAnimal(animal);
        mapChanged("Animal at " + animal.getPosition() +" moved!");
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

    public List<List<Animal>> getAnimalsToReproduce(){
        List<List<Animal>>  animalsToReproduce = new ArrayList<>();
        for(TreeSet<Animal> animalsAtPos : animals.values()){
            List<Animal> topTwoAnimals = animalsAtPos.stream().limit(2).toList();
            if(topTwoAnimals.size() == 2){
                animalsToReproduce.add(topTwoAnimals);
            }
        }
        return animalsToReproduce;
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
