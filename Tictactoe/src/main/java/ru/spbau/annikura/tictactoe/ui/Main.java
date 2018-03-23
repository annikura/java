package ru.spbau.annikura.tictactoe.ui;

import org.jetbrains.annotations.NotNull;
import javafx.application.Application;
import javafx.stage.Stage;

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
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(ScenesCollection.newMainMenuScene(600, 400, primaryStage));
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
