package agh.ics.oop.presenter;
import agh.ics.oop.RandomVector2dGenerator;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationSettings;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    private WorldMap map;
    private SimulationSettings configuration;
    @FXML
    private GridPane mapGrid;
    @FXML
    private Button startButton;
    @FXML
    private Button playButton;
    @FXML
    private Button stopButton;

    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void setConfiguration(SimulationSettings configuration){
        this.configuration = configuration;
    }

    public void drawMap(){
        clearGrid();
        Boundary bounds = map.getCurrentBounds();

        for (int i = -1; i <= bounds.getXSpan(); i++){
            ColumnConstraints col = new ColumnConstraints(30);
            mapGrid.getColumnConstraints().add(col);
        }

        for (int j = -1; j <= bounds.getYSpan(); j++){
            RowConstraints row = new RowConstraints(30);
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
                    cell.setText(map.objectAt(pos).toString());
                }

                int gridYPos = bounds.getUpY() - y + 1;
                int gridXPos = x - bounds.getLeftX() + 1;

                GridPane.setHalignment(cell, HPos.CENTER);
                GridPane.setValignment(cell, VPos.CENTER);
                mapGrid.add(cell, gridXPos, gridYPos);
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(this::drawMap);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void onSimulationStartClicked(){
        RandomVector2dGenerator generator = new RandomVector2dGenerator(configuration.width(), configuration.height(), configuration.startingAnimals());
        List<Vector2d> positions = new ArrayList<>(configuration.startingAnimals());
        for (Vector2d pos: generator){
            positions.add(pos);
        }

        Thread simulationThread = new Thread(() -> {
            Simulation simulation = new Simulation(map, positions, configuration, 100);
            simulation.run();
        });
        simulationThread.start();
    }

    public void onPlayClicked(){}

    public void onPauseClicked(){}
}
