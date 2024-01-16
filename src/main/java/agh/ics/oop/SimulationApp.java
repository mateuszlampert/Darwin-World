package agh.ics.oop;

import agh.ics.oop.model.AbstractWorldMap;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
public class SimulationApp extends Application {
    private final AbstractWorldMap map;
    private final SimulationSettings configuration;

    public SimulationApp(AbstractWorldMap map, SimulationSettings configuration) {
        this.map = map;
        this.configuration = configuration;
    }

    @Override
    public void start(Stage primaryStage) {
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
        configureStage(primaryStage, viewRoot);

        SimulationPresenter presenter = loader.getController();

        presenter.setWorldMap(map);
        presenter.setConfiguration(configuration);

        //map.addListener(presenter);

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
