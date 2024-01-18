package agh.ics.oop.model;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CellVBox extends VBox {

    private Label label;
    private Grass grass;
    private Animal animal;
    private AnimalStatistics animalStatistics;

    public void setAnimal(Animal animal){
        this.animal = animal;
        this.animalStatistics = animal.getAnimalStatistics();
        setLabel(animal.toString());
    }

    public void setGrass(Grass grass){
        this.grass = grass;
        setLabel(grass.toString());
    }

    public void setLabel(String text){
        this.label = new Label(text);
    }

    public VBox getAnimalStats(){
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

    public ProgressBar getAnimalHealthBar(){
        ProgressBar healthBar = new ProgressBar(animal.getHealthPercentage());
        Color color = healthBarColor();
        String style = String.format("-fx-accent: #%02X%02X%02X;", (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
        healthBar.setStyle(style);

        return healthBar;
    }

    public void registerListener(StatsChangeListener listener){
        animalStatistics.addListener(listener);
    }

    public void removeListener(StatsChangeListener listener){
        animalStatistics.removeListener(listener);
    }

    public Label getLabel(){
        return this.label;
    }

    public Animal getAnimal(){
        return this.animal;
    }

    private Color healthBarColor(){
        double r = 1 - animal.getHealthPercentage();
        double g = animal.getHealthPercentage();
        double b = 0;

        return new Color(r, g, b, 1);
    }
}
