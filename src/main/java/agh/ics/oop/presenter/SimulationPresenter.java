package agh.ics.oop.presenter;

import agh.ics.oop.OptionsParser;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.ArrayList;
import java.util.Arrays;

public class SimulationPresenter implements MapChangeListener {
    private static final double CELL_WIDTH = 25;
    private static final double CELL_HEIGHT = 25;
    @FXML
    private TextField inputMoves;
    @FXML
    private Label movesLabel;
    @FXML
    private GridPane mapGrid;
    private WorldMap map;
    private final ArrayList<Vector2d> positions = new ArrayList<>(Arrays.asList(new Vector2d(2, 2), new Vector2d(3, 4)));
    public void setWorldMap(WorldMap map){
        this.map = map;
    }

    public void drawMap(){
        clearGrid();
        Boundary mapBounds = map.getCurrentBounds();
        int xStart = mapBounds.lowerLeft().getX();
        int xEnd = mapBounds.upperRight().getX();
        int yStart = mapBounds.lowerLeft().getY();
        int yEnd = mapBounds.upperRight().getY();


        for(int y = yEnd; y >= yStart; y--){
            for(int x = xStart; x <= xEnd; x++){
                String cellString;
                WorldElement objectAtCell = map.objectAt(new Vector2d(x,y));
                if(objectAtCell != null){
                    cellString = objectAtCell.toString();
                }else{
                    cellString = " ";
                }
                Label label = new Label(cellString);
                label.setStyle("-fx-border-color: black; -fx-border-width: 1 1 1 1;");
                mapGrid.add(label, x-xStart, yEnd-y);
                GridPane.setHalignment(label, HPos.CENTER);
            }
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_WIDTH));
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_HEIGHT));
        }

    }

    private void clearGrid() {
        mapGrid.getChildren().clear();
        //mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
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
        String[] inputMoves = this.inputMoves.getText().split(" ");
        ArrayList<MoveDirection> directions;
        try {
            directions = OptionsParser.Parse(inputMoves);
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
            return;
        }
        Simulation simulation = new Simulation(map, directions, positions);
        SimulationEngine engine = new SimulationEngine(simulation);
        engine.runAsync();
    }

}
