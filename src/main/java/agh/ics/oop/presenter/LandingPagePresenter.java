package agh.ics.oop.presenter;

import agh.ics.oop.*;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LandingPagePresenter {
    private int count = 0;
    private String selectedConfigurationFromFile;
    private List<Control> controls;
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
    @FXML
    private ChoiceBox<String> savedConfigurations;
    @FXML
    private Button saveCurrentConfigButton;
    @FXML
    private Button loadSavedConfigurationButton;



    public void onSimulationStartClicked(ActionEvent actionEvent) {
        try{
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
//                    e.printStackTrace();
                    return;
                }

                Stage stage = new Stage();
                Scene scene = new Scene(viewRoot);


                stage.setScene(scene);
                stage.setTitle("Simulation at map " + map.getId());
                stage.minWidthProperty().bind(viewRoot.minWidthProperty());
                stage.minHeightProperty().bind(viewRoot.minHeightProperty());

                scene.getStylesheets().add("/styles.css");
                SimulationPresenter presenter = loader.getController();

                presenter.setSimulation(simulation);
                presenter.setWorldMap(map);

                simulation.addMapListener(presenter);
                stage.setOnCloseRequest(e -> handleSimulationStageOnCloseRequest(simulation));

                stage.show();
            });

        } catch (InvalidDataTypeException e) {
            showInvalidDataFormatAlert(e.toString());
        } catch (InvalidRangeException e) {
            showInvalidRangeAlert(e.toString());
        }
    }

    private SimulationSettings createConfiguration() throws InvalidDataTypeException, InvalidRangeException{
        if (minMutations.getValue() > maxMutations.getValue()){
            throw new InvalidRangeException("Mutations");
        }
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
        return new Globe("globe" + count, configuration);
    }

    private PlantGrowing getPlantGrowing() throws InvalidDataTypeException{
        PlantGrowing plantsGrowingToConfigure = null;
        switch (plantsGrowing.getValue()){
            case "CrawlingJungle" -> plantsGrowingToConfigure = new CrawlingJungle(mapWidth.getValue(), mapHeight.getValue(), startingPlants.getValue(), plantsEnergy.getValue());
            case "WoodyEquator" -> plantsGrowingToConfigure = new WoodyEquator(mapWidth.getValue(), mapHeight.getValue(), startingPlants.getValue(), plantsEnergy.getValue());
            default -> throw new InvalidDataTypeException("Plants Growing");
        };
        return plantsGrowingToConfigure;
    }

    private Mutation getMutation() throws InvalidDataTypeException{
        Mutation mutationToConfigure = null;
        switch (mutation.getValue()){
            case "RandomMutation" -> mutationToConfigure = new RandomMutation();
            default -> throw new InvalidDataTypeException("Mutation");
        };
        return mutationToConfigure;
    }

    private AnimalBehavior getAnimalBehavior() throws InvalidDataTypeException{
        AnimalBehavior animalBehaviorToConfigure = null;
        switch (animalBehavior.getValue()){
            case "LittleMadness" -> animalBehaviorToConfigure = new LittleMadness();
            case "FullPredestination" -> animalBehaviorToConfigure = new FullPredestination();
            default -> throw new InvalidDataTypeException("Animal behavior");
        };
        return animalBehaviorToConfigure;
    }

    private void handleSimulationStageOnCloseRequest(Simulation simulation){
        simulation.kill();
    }

    public void saveToFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) { // czy to na pewno logika prezentera?
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("width:" + mapWidth.getValue() + "\n");
                writer.write("height:" + mapHeight.getValue() + "\n");
                writer.write("startingPlants:" + startingPlants.getValue() + "\n");
                writer.write("plantsEnergy:" + plantsEnergy.getValue() + "\n");
                writer.write("plantsPerDay:" + plantsPerDay.getValue() + "\n");
                writer.write("plantsGrowing:" + plantsGrowing.getValue() + "\n");
                writer.write("startingAnimals:" + startingAnimals.getValue() + "\n");
                writer.write("startingEnergy:" + startingEnergy.getValue() + "\n");
                writer.write("energyNeededToReproduce:" + energyNeededToReproduce.getValue() + "\n");
                writer.write("energyLostToReproduce:" + energyLostToReproduce.getValue() + "\n");
                writer.write("minMutations:" + minMutations.getValue() + "\n");
                writer.write("maxMutations:" + maxMutations.getValue() + "\n");
                writer.write("mutation:" + mutation.getValue() + "\n");
                writer.write("genomeLength:" + genomeLength.getValue() + "\n");
                writer.write("animalBehavior:" + animalBehavior.getValue());
            } catch (IOException e) {
                showProblemWithFileAlert("saving to");
            }
        }
    }

    public void loadFromFile(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text file (*.txt)", "*.txt"));
        File file = fileChooser.showOpenDialog(new Stage());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String dataLabel;
            String line;
            String value;
            while ((line = reader.readLine()) != null){
                dataLabel = line.split(":")[0];
                value = line.split(":")[1];
                switch (dataLabel){
                    case "width" -> setSpinnerValue(mapWidth, value, dataLabel);
                    case "height" -> setSpinnerValue(mapHeight, value, dataLabel);
                    case "startingPlants" -> setSpinnerValue(startingPlants, value, dataLabel);
                    case "plantsEnergy" -> setSpinnerValue(plantsEnergy, value, dataLabel);
                    case "plantsPerDay" -> setSpinnerValue(plantsPerDay, value, dataLabel);
                    case "plantsGrowing" -> setChoiceBoxValue(plantsGrowing, value, dataLabel);
                    case "startingAnimals" -> setSpinnerValue(startingAnimals, value, dataLabel);
                    case "startingEnergy" -> setSpinnerValue(startingEnergy, value, dataLabel);
                    case "energyNeededToReproduce" -> setSpinnerValue(energyNeededToReproduce, value, dataLabel);
                    case "energyLostToReproduce" -> setSpinnerValue(energyLostToReproduce, value, dataLabel);
                    case "minMutations" -> setSpinnerValue(minMutations, value, dataLabel);
                    case "maxMutations" -> setSpinnerValue(maxMutations, value, dataLabel);
                    case "mutation" -> setChoiceBoxValue(mutation, value, dataLabel);
                    case "genomeLength" -> setSpinnerValue(genomeLength, value, dataLabel);
                    case "animalBehavior" -> setChoiceBoxValue(animalBehavior, value, dataLabel);
                    default -> throw new InvalidDataTypeException("File formatting is not supported");
                }
            }
        }
        catch (IOException | RuntimeException e){
            showProblemWithFileAlert("loading from");
        }
        catch (InvalidDataTypeException e){
            showInvalidDataFormatAlert(e.toString());
        }
    }

    private void setSpinnerValue(Spinner<Integer> spinner, String value, String label) throws InvalidDataTypeException{
        try{
            spinner.getValueFactory().setValue(Integer.parseInt(value));
        } catch (RuntimeException e){
            throw new InvalidDataTypeException(label);
        }

    }

    private void setChoiceBoxValue(ChoiceBox<String> choiceBox, String value, String label) throws InvalidDataTypeException {
        try{
            choiceBox.setValue(value);
        } catch (RuntimeException e){
            throw new InvalidDataTypeException(label);
        }
    }

    private void showInvalidDataFormatAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid data format");
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showProblemWithFileAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problem with file");
        alert.setContentText("Problem occurred while " + content + " file. Ensure that the selected file is correct");
        alert.showAndWait();
    }

    private void showInvalidRangeAlert(String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Problem with range");
        alert.setContentText(content);
        alert.showAndWait();
    }
}
