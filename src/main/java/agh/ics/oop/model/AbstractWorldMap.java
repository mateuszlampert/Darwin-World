package agh.ics.oop.model;

import agh.ics.oop.MapVisualizer;

import java.util.*;

abstract public class AbstractWorldMap implements WorldMap{
    private static final AnimalComparator ANIMAL_COMPARATOR = new AnimalComparator();
    protected final Map<Vector2d, TreeSet<Animal>> animals = new HashMap<>();
    protected final Map<Vector2d, Grass> grasses = new HashMap<>();
    private final MapVisualizer visualizer = new MapVisualizer(this);
    private final String mapId;
    protected PlantGrowing plantGrowing;

    protected AbstractWorldMap(String mapId){
        this.mapId = mapId;
    }

    public void setPlantGrowing(PlantGrowing plantGrowing) {
        this.plantGrowing = plantGrowing;
    }

    public void growGrass(){
        Set<Vector2d> newGrassPositions = plantGrowing.growGrass(this);
    }

    @Override
    public void removeAnimal(Animal animal){
        Vector2d pos = animal.getPosition();
        TreeSet<Animal> animalsAtPos = animals.get(pos);
        animalsAtPos.remove(animal);
        if(animalsAtPos.isEmpty()){ // freeing memory of empty list
            animals.remove(pos);
        }
    }

    @Override
    public void placeAnimal(Animal animal) throws InvalidPositionException{
        Vector2d animalPos = animal.getPosition();
        if(!canMoveTo(animalPos)){
            throw new InvalidPositionException(animalPos);
        }
        putAnimal(animal);
    }


    private void putAnimal(Animal obj){
        Vector2d pos = obj.getPosition();
        TreeSet<Animal> animalsAtPosition = animals.computeIfAbsent(pos,k -> new TreeSet<Animal>(ANIMAL_COMPARATOR));
        animalsAtPosition.add(obj);
    }

    private Animal getBestAnimalAt(Vector2d position){
        TreeSet<Animal> animalsAtPosition = animals.get(position);
        if(animalsAtPosition == null || animalsAtPosition.isEmpty()){
            return null;
        }
        return animalsAtPosition.first();
    }

    @Override
    public void move(Animal animal, MoveDirection direction) {
        removeAnimal(animal);
        animal.move(this);
        putAnimal(animal);
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
    public WorldElement objectAt(Vector2d position){
        if (animalAt(position) != null){
            return animalAt(position);
        }
        else if (grassAt(position) != null) {
            return grassAt(position);
        }
        else return null;
    }

    @Override
    public Animal animalAt(Vector2d position){
        return getBestAnimalAt(position);
    }

    @Override
    public Grass grassAt(Vector2d position){
        return grasses.get(position);
    }

    @Override
    public List<TreeSet<Animal>> getAnimals() {
        return new ArrayList<>(animals.values());
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

    public List<ReproductionPair> getAnimalsToReproduce(){
        List<ReproductionPair>  animalsToReproduce = new ArrayList<>();
        for(TreeSet<Animal> animalsAtPos : animals.values()){
            if(animalsAtPos.size() >= 2){
                Iterator<Animal> it = animalsAtPos.iterator();
                ReproductionPair pair = new ReproductionPair(it.next(), it.next());
                animalsToReproduce.add(pair);
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

    public PlantGrowing getPlantGrowing(){return plantGrowing;}

    public int getGrassCount(){
        return this.grasses.size();
    }
    public int getFreeSpaceCount() {
        HashSet<Vector2d> takenPositions = new HashSet<>();
        takenPositions.addAll(grasses.keySet());
        takenPositions.addAll(animals.keySet());
        return getCurrentBounds().getBoundarySize() - takenPositions.size();

    }

    public Set<Animal> getAnimalsAtPos(Vector2d position){
        return animals.get(position);

    }
}
