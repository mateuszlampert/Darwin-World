package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class AnimalLabel extends Label {

    private final AnimalStatistics animalStatistics;
    private final Animal animal;

    public AnimalLabel(AnimalStatistics animalStatistics, Animal animal){
        this.animalStatistics = animalStatistics;
        this.animal = animal;
    }

    public VBox showStats(){
        VBox stats = new VBox();
        stats.setAlignment(Pos.CENTER);

        Label genotype = new Label("Genotype: " + animal.getGenome());
        Label genome = new Label("Current genome: " + animal.getGenome().peekNext());
        Label bornOn = new Label("Born on day: " + animalStatistics.getBornOn());
        Label energy = new Label("Current energy: " + animal.getEnergy());
        Label age = new Label("Age: " + animalStatistics.getAge());
        Label diedOn = new Label("Died on day: " + animalStatistics.getDiedOn());
        Label childrenCount = new Label("Number of children: " + animalStatistics.getChildrenCount());
        Label eatenGrass = new Label("Total eaten grass: " + animalStatistics.getEatenGrass());
        stats.getChildren().addAll(genotype, genome, energy, bornOn, age, diedOn, childrenCount, eatenGrass);

        return stats;
    }

    public void registerListener(StatsChangeListener listener){
        animalStatistics.addListener(listener);
    }

    public void removeListener(StatsChangeListener listener){
        animalStatistics.removeListener(listener);
    }

    public Animal getAnimal(){
        return this.animal;
    }
}
