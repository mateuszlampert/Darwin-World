package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    @FXML
    private Spinner<Integer> dayTime;
    @FXML
    private CheckBox shouldSave;

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        SimulationSettings configuration = createConfiguration();
        AbstractWorldMap map = createMap(configuration);
        RandomVector2dGenerator generator = new RandomVector2dGenerator(configuration.width(), configuration.height(), configuration.startingAnimals());

        List<Vector2d> positions = new ArrayList<>(configuration.startingAnimals());
        for (Vector2d pos : generator) {
            positions.add(pos);
        }

        Simulation simulation = new Simulation(map, positions, configuration, dayTime.getValue());

        Platform.runLater(() -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
            BorderPane viewRoot;

            try {
                viewRoot = loader.load();
            } catch (IOException e) {
                System.out.println("Couldn't load the file!");
                e.printStackTrace();
                return;
            }

            Stage stage = new Stage();
            Scene scene = new Scene(viewRoot);
            stage.setScene(scene);
            stage.setTitle("Simulation app");
            stage.minWidthProperty().bind(viewRoot.minWidthProperty());
            stage.minHeightProperty().bind(viewRoot.minHeightProperty());

            SimulationPresenter presenter = loader.getController();

            presenter.setSimulation(simulation);
            presenter.setWorldMap(map);
            presenter.setConfiguration(configuration);

            simulation.addMapListener(presenter);
            //map.addListener(presenter);

            stage.show();
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
                getAnimalBehavior(),
                 shouldSave.isSelected()
        );
    }

    private AbstractWorldMap createMap(SimulationSettings configuration){
        count += 1;
        return new Globe("_globe_" + count, configuration);
    }

    private PlantGrowing getPlantGrowing(){
        return switch (plantsGrowing.getValue()){
            case "Crawling Jungle" -> new CrawlingJungle(mapWidth.getValue(), mapHeight.getValue(), startingPlants.getValue(), plantsEnergy.getValue());
            default -> new WoodyEquator(mapWidth.getValue(), mapHeight.getValue(), startingPlants.getValue(), plantsEnergy.getValue());
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
