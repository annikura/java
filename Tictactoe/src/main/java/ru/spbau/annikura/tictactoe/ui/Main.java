package ru.spbau.annikura.tictactoe.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TicTacToe");
        primaryStage.setScene(ScenesCollection.newMainMenuScene(600, 400, primaryStage));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
