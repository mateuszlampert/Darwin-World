package agh.ics.oop.presenter;
import agh.ics.oop.RandomVector2dGenerator;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationSettings;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class SimulationPresenter implements MapChangeListener, StatsChangeListener {
    private WorldMap map;
    private SimulationSettings configuration;
    private Simulation simulation;
    private boolean paused = false;
    private final Map<Vector2d, Node> nodes = new HashMap<>();
    private AnimalLabel trackedAnimal;
    private SimulationStatsChart statsChart = new SimulationStatsChart();

    @FXML
    private GridPane mapGrid;
    @FXML
    private Button startButton;
    @FXML
    private Button playButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button nextButton;
    @FXML
    private Button favourableButton;
    @FXML
    private VBox animalStats;
    @FXML
    private VBox simulationStats;
    @FXML
    private VBox simulationStatsChart;

    @FXML
    public void initialize(){
        simulationStatsChart.getChildren().add(statsChart.getChart());
    }

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setConfiguration(SimulationSettings configuration){
        this.configuration = configuration;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    public void drawMap(){
        clearGrid();
        Boundary bounds = map.getCurrentBounds();

        for (int i = -1; i <= bounds.getXSpan(); i++){
            ColumnConstraints col = new ColumnConstraints(25);
            mapGrid.getColumnConstraints().add(col);
        }

        for (int j = -1; j <= bounds.getYSpan(); j++){
            RowConstraints row = new RowConstraints(25);
            mapGrid.getRowConstraints().add(row);
        }

        Label header = new Label("y\\x");
        GridPane.setHalignment(header, HPos.CENTER);
        GridPane.setValignment(header, VPos.CENTER);
        mapGrid.add(header, 0, 0);

        for (int y = bounds.getDownY(); y <= bounds.getUpY(); y++){
            int gridYPos = bounds.getUpY() - y + 1;
            Label rowIdCell = new Label(String.valueOf(y));
            GridPane.setHalignment(rowIdCell, HPos.CENTER);
            GridPane.setValignment(rowIdCell, VPos.CENTER);

            mapGrid.add(rowIdCell, 0, gridYPos);
        }

        for (int x = bounds.getLeftX(); x <= bounds.getRightX(); x++){
            int gridXPos = x - bounds.getLeftX() + 1;
            Label colIdCell = new Label(String.valueOf(x));
            GridPane.setHalignment(colIdCell, HPos.CENTER);
            GridPane.setValignment(colIdCell, VPos.CENTER);

            mapGrid.add(colIdCell, gridXPos, 0);
        }

        for (int y = bounds.getDownY(); y <= bounds.getUpY(); y++){
            for (int x = bounds.getLeftX(); x <= bounds.getRightX(); x++){
                Vector2d pos = new Vector2d(x, y);
                Label cell = new Label();

                if (map.isOccupied(pos)){
                    if (map.animalAt(pos) != null){
                        if (trackedAnimal != null && map.animalAt(pos).equals(trackedAnimal.getAnimal())){
                            cell = trackedAnimal;
                            highlightAnimal();
                        }
                        else{
                            cell = new AnimalLabel(map.animalAt(pos).getAnimalStatistics(), map.animalAt(pos));
                            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> trackAnimal((AnimalLabel) e.getSource()));
                            cell.setText(map.animalAt(pos).toString());
                            cell.setTextFill(Color.BROWN);
                        }
                    }
                    else{
                        cell.setText(map.grassAt(pos).toString());
                        cell.setTextFill(Color.GREEN);
                    }
                }

                cell.setMinSize(24, 24);
                cell.setTextAlignment(TextAlignment.RIGHT);
                int gridYPos = bounds.getUpY() - y + 1;
                int gridXPos = x - bounds.getLeftX() + 1;
                nodes.put(pos, cell);

                GridPane.setHalignment(cell, HPos.CENTER);
                GridPane.setValignment(cell, VPos.CENTER);
                mapGrid.add(cell, gridXPos, gridYPos);
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> {
            drawMap();
            highlightAnimal();
        });
    }

    @Override
    public void simulationStatsUpdated(MapStatisticsHandler statisticsHandler,String message) {
        generateSimulationStats(statisticsHandler);
        generateSimulationStatsChart(statisticsHandler);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void onSimulationStartClicked() {
        Thread simulationThread = new Thread(() -> {
            simulation.run();
        });
        simulationThread.start();
        startButton.setDisable(true);
        pauseButton.setDisable(false);
        playButton.setDisable(true);
    }

    public void onPlayClicked(){
        simulation.play();
        pauseButton.setDisable(false);
        playButton.setDisable(true);
        nextButton.setDisable(true);
        favourableButton.setDisable(true);
        paused = false;
    }

    public void onPauseClicked(){
        pauseButton.setDisable(true);
        playButton.setDisable(false);
        nextButton.setDisable(false);
        favourableButton.setDisable(false);
        simulation.pause();
        paused = true;
    }

    public void onStopClicked() {
        simulation.kill();
        pauseButton.setDisable(true);
        playButton.setDisable(true);
        nextButton.setDisable(true);
    }

    public void onNextClicked() {
        simulation.singleDay();
    }

    public void highlightFavourablePositions(){
        favourableButton.setText("FADE");
        Set<Vector2d> favourablePositions = map.getPlantGrowing().getFavourablePositions(map);
        for (Vector2d pos: favourablePositions){
            int x = pos.getX();
            int y = pos.getY();
            nodes.get(new Vector2d(x, y)).setStyle("-fx-background-color: rgba(36,183,178,0.96)");
        }
        favourableButton.setOnAction(e -> fadeFavourablePositions(favourablePositions));
    }
    
    public void fadeFavourablePositions(Set<Vector2d> favourablePositions){
        favourableButton.setText("HIGHLIGHT");
        for (Vector2d pos: favourablePositions){
            int x = pos.getX();
            int y = pos.getY();
            nodes.get(new Vector2d(x, y)).setStyle(null);
        }
        if (this.trackedAnimal != null){
            highlightAnimal();
        }
        favourableButton.setOnAction(e -> highlightFavourablePositions());
    }

    public void trackAnimal(AnimalLabel animalLabel){
        if (paused){
            if (this.trackedAnimal != null){
                this.trackedAnimal.removeListener(this);
                this.trackedAnimal.setStyle(null);
            }
            this.trackedAnimal = animalLabel;
            animalLabel.registerListener(this);
            generateTrackedAnimalStats(animalLabel);
        }
    }

    public void highlightAnimal(){
        if (trackedAnimal != null){
            trackedAnimal.setStyle("-fx-font-weight: bold; -fx-background-color: black; -fx-text-fill: yellow ");
        }
    }

    @Override
    public void statsChanged(AnimalStatistics animalStatistics) {
        generateTrackedAnimalStats(trackedAnimal);
    }

    private void generateTrackedAnimalStats(AnimalLabel animalLabel){
        Platform.runLater(() -> {
            animalStats.getChildren().clear();
            animalStats.getChildren().add(animalLabel.showStats());
            highlightAnimal();
        });
    }

    private void generateSimulationStats(MapStatisticsHandler statisticsHandler){
        Platform.runLater(() -> {
            simulationStats.getChildren().clear();
            simulationStats.getChildren().add(SimulationLabel.showStats(statisticsHandler));
        });
    }

    private void generateSimulationStatsChart(MapStatisticsHandler statisticsHandler){
        Platform.runLater(() -> {
            statsChart.updateSeries(statisticsHandler);
        });
    }

}