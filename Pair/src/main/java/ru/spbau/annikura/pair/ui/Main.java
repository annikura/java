package ru.spbau.annikura.pair.ui;

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
        primaryStage.setTitle("Find Pair!");
        primaryStage.setResizable(false);

        String errorMessage = "Wrong input format. If you want size to be specified, pass '--size=<Integer>' as a parameter." +
                "Note that size should be a positive even integer <= 10, not containing leading zeroes." +
                "If no size is specified, the default size is 4.";
        String sizeAsStr = getParameters().getNamed().get("size");
        if (sizeAsStr == null) {
            sizeAsStr = "4";
        }
        if (!sizeAsStr.matches("\\d+")) {
            System.out.println(errorMessage);
            return;
        }
        int size = Integer.valueOf(sizeAsStr);
        if (size % 2 != 0 || size > 10) {
            System.out.println(errorMessage);
            return;
        }

        int width = Integer.max(200, 60 * size + 50);
        int height = Integer.max(200, 60 * size + 70);

        primaryStage.setScene(ScenesCollection.newGameScene(width, height, size));
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
