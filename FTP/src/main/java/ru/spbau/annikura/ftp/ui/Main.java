package ru.spbau.annikura.ftp.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

/**
 * Main application class. Starts application.
 */
public class Main extends Application {

    /**
     * Starts application stage
     * @param primaryStage stage to palace scenes on
     */
    @Override
    public void start(@NotNull Stage primaryStage) {
        primaryStage.setTitle("File client.");
        primaryStage.setResizable(false);

        primaryStage.setScene(ScenesCollection.newLogInScene(700, 500, primaryStage));
        primaryStage.show();
    }

    /**
     * Runs application
     * @param args input parameters of the application. Not expected here.
     */
    public static void main(@NotNull String[] args) {
        launch(args);
    }
}