package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LandingPagePresenter {
    private int count = 0;
    @FXML
    private Spinner<Integer> mapWidth;
    @FXML
    private Spinner<Integer> mapHeight;
    @FXML
    private Spinner<Integer> startingPlants;
    @FXML
    private Spinner<Integer> plantsEnergy;
    @FXML
    private Spinner<Integer> plantsPerDay;
    @FXML
    private Spinner<Integer> startingAnimals;
    @FXML
    private Spinner<Integer> startingEnergy;
    @FXML
    private Spinner<Integer> energyNeededToReproduce;
    @FXML
    private Spinner<Integer> energyLostToReproduce;
    @FXML
    private Spinner<Integer> minMutations;
    @FXML
    private Spinner<Integer> maxMutations;
    @FXML
    private Spinner<Integer> genomeLength;
    @FXML
    private ChoiceBox<String> animalBehavior;
    @FXML
    private ChoiceBox<String> plantsGrowing;
    @FXML
    private ChoiceBox<String> mutation;

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        SimulationSettings configuration = createConfiguration();
        AbstractWorldMap map = createMap(configuration);

        Platform.runLater(() -> {
            SimulationApp simulationApp = new SimulationApp(map, configuration);
            simulationApp.start(new Stage());
        });
    }

    private SimulationSettings createConfiguration(){
         return new SimulationSettings(
                mapWidth.getValue(),
                mapHeight.getValue(),
                startingPlants.getValue(),
                plantsEnergy.getValue(),
                plantsPerDay.getValue(),
                getPlantGrowing(),
                startingAnimals.getValue(),
                startingEnergy.getValue(),
                energyNeededToReproduce.getValue(),
                energyLostToReproduce.getValue(),
                minMutations.getValue(),
                maxMutations.getValue(),
                getMutation(),
                genomeLength.getValue(),
                getAnimalBehavior()
        );
    }

    private AbstractWorldMap createMap(SimulationSettings configuration){
        count += 1;
        return new Globe("a" + count, configuration);
    }

    private PlantGrowing getPlantGrowing(){
        return switch (plantsGrowing.getValue()){
            case "Crawling Jungle" -> new CrawlingJungle(mapWidth.getValue(), mapHeight.getValue(), plantsPerDay.getValue());
            default -> new WoodyEquator(mapWidth.getValue(), mapHeight.getValue(), plantsPerDay.getValue());
        };
    }

    private Mutation getMutation(){
        return new RandomMutation();
    }

    private AnimalBehavior getAnimalBehavior(){
        return switch (animalBehavior.getValue()){
            case "Little Madness" -> new LittleMadness();
            default -> new FullPredestination();
        };
    }
}
