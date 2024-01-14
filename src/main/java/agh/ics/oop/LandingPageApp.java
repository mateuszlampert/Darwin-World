package agh.ics.oop;

import agh.ics.oop.model.RectangularMap;
import agh.ics.oop.presenter.LandingPagePresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class LandingPageApp extends Application {
    @Override
    public void start(Stage primaryStage){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("landingPage.fxml"));
        BorderPane viewRoot;
        try {
            viewRoot = loader.load();
        } catch (IOException e){
            System.out.println("Couldn't load the file!");
            return;
        }
        configureStage(primaryStage, viewRoot);

        primaryStage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Simulation app");
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
        primaryStage.setOnCloseRequest(this::handleCloseRequest);
    }

    private void handleCloseRequest(WindowEvent event) {
        System.out.println("Closing the application...");
        Platform.exit();
    }
}
