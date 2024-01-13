package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SimulationSettings;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

public class SimulationPresenter implements MapChangeListener {
    private static final double CELL_WIDTH = 40;
    private static final double CELL_HEIGHT = 40;
    private static final String EMPTY_CELL_STRING = "";
    @FXML
    private TextField inputMoves;
    @FXML
    private Label movesLabel;
    @FXML
    private GridPane mapGrid;
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

    private WorldMap map;
    private SimulationSettings configuration;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void drawMap(){
        clearGrid();
        Boundary mapBounds = map.getCurrentBounds();

        for(int i = 0; i <= mapBounds.getYSpan(); i++) {
            ColumnConstraints column = new ColumnConstraints(CELL_WIDTH);
            mapGrid.getColumnConstraints().add(column);
        }

        for(int i = 0; i <= mapBounds.getXSpan(); i++) {
            RowConstraints row = new RowConstraints(CELL_HEIGHT);
            mapGrid.getRowConstraints().add(row);
        }

        for(int y = mapBounds.getDownY(); y <= mapBounds.getUpY(); y++){
            for(int x = mapBounds.getLeftX(); x <= mapBounds.getRightX(); x++){
                WorldElement objectAtPos = map.objectAt(new Vector2d(x, y));
                String cellString = EMPTY_CELL_STRING;
                if(objectAtPos != null){
                    cellString = objectAtPos.toString();
                }
                Label cellLabel = new Label(cellString);

                int gridYPosition = mapBounds.getUpY() - y;
                int gridXPosition = x - mapBounds.getLeftX();
                mapGrid.add(cellLabel, gridXPosition, gridYPosition);
                GridPane.setHalignment(cellLabel, HPos.CENTER);
            }
        }
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    @Override
    public void mapChanged(WorldMap worldMap,String message) {
        Platform.runLater(() -> {
            movesLabel.setText(message);
            drawMap();
        });
    }

    public void onSimulationStartClicked(ActionEvent actionEvent) {
        setConfiguration();
        ArrayList<Vector2d> positions = new ArrayList<>(Arrays.asList(new Vector2d(3, 3), new Vector2d(3, 2)));
        Simulation simulation = new Simulation(map, positions, configuration, 100);
        SimulationEngine engine = new SimulationEngine(simulation);
        engine.runAsync();
    }

    private void setConfiguration(){
        this.configuration = new SimulationSettings(
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

    private PlantGrowing getPlantGrowing(){
        return switch (plantsGrowing.getValue()){
            case "Crawling Jungle" -> new CrawlingJungle();
            default -> new WoodyEquator();
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
