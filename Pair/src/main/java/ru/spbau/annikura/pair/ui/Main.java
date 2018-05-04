package ru.spbau.annikura.pair.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.tictactoe.ui.ScenesCollection;

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
        primaryStage.setTitle("Find Pair!");
        primaryStage.setResizable(false);

        String errorMessage = "Wrong input format. If you want size to be specified, pass '--size=<Integer>' as a parameter." +
                "Note that size should be a positive even integer < 100, not containing leading zeroes.";
        //String sizeAsStr = getParameters().getNamed().get("size");
        String sizeAsStr = "5";
        if (!sizeAsStr.matches("\\d+") || sizeAsStr.length() > 2) {
            System.out.println(errorMessage);
            return;
        }
        int size = Integer.valueOf(sizeAsStr);
        if (size % 2 != 0) {
            System.out.println(errorMessage);
            return;
        }

        primaryStage.setScene(ScenesCollection.newGameScene(700, 500, size));
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
