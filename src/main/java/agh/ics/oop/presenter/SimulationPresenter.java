package agh.ics.oop.presenter;
import agh.ics.oop.Simulation;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.util.*;

public class SimulationPresenter implements MapChangeListener, StatsChangeListener, StrongestGenotypeChangedListener {
    private WorldMap map;
    private Simulation simulation;

    BooleanProperty paused = new SimpleBooleanProperty(false);
    private final Map<Vector2d, CellVBox> cellVBoxMap = new HashMap<>();
    private CellVBox trackedAnimal = null;
    private final SimulationStatsChart statsChart = new SimulationStatsChart();
    private final Set<Animal> animalsWithStrongestGenotype = new HashSet<>();
    private Genome strongestGenotype = null;


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
    private VBox animalStatsVBox;
    @FXML
    private VBox simulationStatsVBox;
    @FXML
    private VBox simulationStatsChart;
    @FXML
    private Button mostFrequentGenotypeButton;

    @FXML
    public void initialize(){
        simulationStatsChart.getChildren().add(statsChart.getChart());
    }


    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    private void styleCellWithTrackedAnimal(CellVBox cell){
        cell.getStyleClass().add("trackedAnimalCell");
    }
    private void reverseStyleCellWithTrackedAnimal(CellVBox cell){
        cell.getStyleClass().remove("trackedAnimalCell");
    }

    private void styleCellStrongestGenotype(CellVBox cell){
        cell.getStyleClass().add("animalWithStrongestGenotype");
    }
    private void reverseStyleCellStrongestGenotype(CellVBox cell){
        cell.getStyleClass().remove("animalWithStrongestGenotype");
    }

    private void styleCellWithFavourablePosition(CellVBox cell){
        cell.getStyleClass().add("favourableGrassPosition");
    }

    private void reverseStyleCellWithFavourablePosition(CellVBox cell){
        cell.getStyleClass().remove("favourableGrassPosition");
    }

    private void styleCellWithAnimal(CellVBox cell){
        cell.getLabel().getStyleClass().add("animalLabel");
    }

    private void reverseStyleCellWithAnimal(CellVBox cell){
        cell.getLabel().getStyleClass().remove("animalLabel");
    }

    private void styleCellWithGrass(CellVBox cell){
        cell.getLabel().getStyleClass().add("grassLabel");
    }

    private void reverseStyleCellWithGrass(CellVBox cell){
        cell.getLabel().getStyleClass().remove("grassLabel");
    }


    public void drawMap(){
        clearGrid();
        Boundary bounds = map.getCurrentBounds();

        for (int i = -1; i <= bounds.getXSpan(); i++){
            ColumnConstraints col = new ColumnConstraints(28);
            mapGrid.getColumnConstraints().add(col);
        }

        for (int j = -1; j <= bounds.getYSpan(); j++){
            RowConstraints row = new RowConstraints(28);
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
                int gridYPos = bounds.getUpY() - y + 1;
                int gridXPos = x - bounds.getLeftX() + 1;
                CellVBox cell = new CellVBox();

                if (map.isOccupied(pos)){
                    if (map.getAnimalsAtPos(pos) != null){
                        cell.setAnimal(map.animalAt(pos));
                        cell.getChildren().addAll(cell.getAnimalHealthBar(), cell.getLabel());
                        cell.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> trackAnimal((CellVBox) e.getSource()));
                        styleCellWithAnimal(cell);
                        if (trackedAnimal != null && map.getAnimalsAtPos(pos).contains(trackedAnimal.getAnimal())){
                            styleCellWithTrackedAnimal(cell);
                        }
                        if (!animalsWithStrongestGenotype.isEmpty()){
                            for (Animal animalWithStrongestGenotype: animalsWithStrongestGenotype){
                                for (Animal animal: map.getAnimalsAtPos(pos)){
                                    if (animalWithStrongestGenotype.equals(animal)){
                                        styleCellStrongestGenotype(cell);
                                    }
                                }
                            }
                        }
                    }
                    else{
                        cell.setGrass(map.grassAt(pos));
                        cell.getChildren().add(cell.getLabel());
                        styleCellWithGrass(cell);
                    }
                }

                cell.setMinSize(28, 28);
                cell.setAlignment(Pos.CENTER);
                GridPane.setHalignment(cell, HPos.CENTER);
                GridPane.setValignment(cell, VPos.CENTER);
                mapGrid.add(cell, gridXPos, gridYPos);
                cellVBoxMap.put(pos, cell);
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
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
        pauseButton.disableProperty().bind(paused);
        playButton.disableProperty().bind(paused.not());
        nextButton.disableProperty().bind(paused.not());
        startButton.setDisable(true);
        mostFrequentGenotypeButton.setDisable(false);
    }

    public void onPlayClicked(){
        simulation.play();
        paused.set(false);
        favourableButton.setDisable(true);
        fadeFavourablePositions();
    }

    public void onPauseClicked(){
        paused.set(true);
        favourableButton.setDisable(false);
        simulation.pause();
    }

    public void onStopClicked() {
        simulation.kill();
        paused.set(false);
        pauseButton.disableProperty().unbind();
        pauseButton.setDisable(true);
    }

    public void onNextClicked() {
        simulation.singleDay();
        fadeFavourablePositions();
    }

    public void highlightFavourablePositions(){
        favourableButton.setText("FADE MOST LIKELY GRASS POSITIONS");
        Set<Vector2d> favourablePositions = map.getPlantGrowing().getFavourablePositions(map);
        favourableButton.setOnAction(e -> fadeFavourablePositions());

        for (Vector2d pos: favourablePositions){
            styleCellWithFavourablePosition(cellVBoxMap.get(pos));
        }
    }

    private void fadeFavourablePositions(){
        favourableButton.setText("SHOW MOST LIKELY GRASS POSITIONS");
        Set<Vector2d> favourablePositions = map.getPlantGrowing().getFavourablePositions(map);
        favourableButton.setOnAction(e -> highlightFavourablePositions());

        for (Vector2d pos: favourablePositions){
            reverseStyleCellWithFavourablePosition(cellVBoxMap.get(pos));
        }
    }

    private void trackAnimal(CellVBox cell){
        if (paused.get()){
            untrackAnimal();
            cell.registerListener(this);
            styleCellWithTrackedAnimal(cell);
            this.trackedAnimal = cell;
            setAnimalStats();
        }
    }
    
    private void untrackAnimal(){
        if (trackedAnimal != null){
            trackedAnimal.removeListener(this);
            reverseStyleCellWithTrackedAnimal(cellVBoxMap.get(trackedAnimal.getAnimal().getPosition()));
            trackedAnimal = null;
        }
    }

    @Override
    public void statsChanged(AnimalStatistics animalStatistics) {
        Platform.runLater(this::setAnimalStats);
    }


    private void resetAnimalStats(){
        Platform.runLater(() -> {
            animalStatsVBox.getChildren().clear();
        });
    }
    
    private void setAnimalStats(){
        resetAnimalStats();
        Platform.runLater(() -> {
            animalStatsVBox.getChildren().add(trackedAnimal.getAnimalStats());
        });
    }

    private void generateSimulationStats(MapStatisticsHandler statisticsHandler){
        Platform.runLater(() -> {
            simulationStatsVBox.getChildren().clear();
            simulationStatsVBox.getChildren().add(SimulationLabel.showStats(statisticsHandler));
        });
    }

    private void generateSimulationStatsChart(MapStatisticsHandler statisticsHandler){
        Platform.runLater(() -> {
            statsChart.updateSeries(statisticsHandler);
        });
    }

    private void setStrongestGenotype(){
        this.strongestGenotype = this.simulation.getMapHandler().getStatisticsHandler().getMostUsedGenome();
    }

    private void setAnimalsWithStrongestGenotype(){
        setStrongestGenotype();
        animalsWithStrongestGenotype.clear();
        for (Animal animal: simulation.getAnimals()){
            if (animal.getGenome().equals(strongestGenotype)){
                animalsWithStrongestGenotype.add(animal);
            }
        }
    }

    public void trackStrongestGenotype(){
        untrackStrongestGenotype();
        this.simulation.getMapHandler().getStatisticsHandler().addListener(this);
        setAnimalsWithStrongestGenotype();
        for (Animal animal : animalsWithStrongestGenotype){
            Platform.runLater(() -> {
                styleCellStrongestGenotype(cellVBoxMap.get(animal.getPosition()));
            });
        }
        Platform.runLater(() -> {
            mostFrequentGenotypeButton.setText("FADE STRONGEST ANIMALS");
            mostFrequentGenotypeButton.setOnAction(e -> untrackStrongestGenotype());
        });
    }

    public void untrackStrongestGenotype(){
        if (strongestGenotype != null){
            strongestGenotype = null;
            this.simulation.getMapHandler().getStatisticsHandler().removeListener(this);
            for (Animal animal : animalsWithStrongestGenotype){
                Platform.runLater(() -> {
                    reverseStyleCellStrongestGenotype(cellVBoxMap.get(animal.getPosition()));
                });
            }
            Platform.runLater(() -> {
                mostFrequentGenotypeButton.setText("SHOW STRONGEST ANIMALS");
                mostFrequentGenotypeButton.setOnAction(e -> trackStrongestGenotype());
            });
            animalsWithStrongestGenotype.clear();
        }
    }

    @Override
    public void strongestGenotypeChanged(MapStatisticsHandler mapStatisticsHandler) {
        trackStrongestGenotype();
    }

}