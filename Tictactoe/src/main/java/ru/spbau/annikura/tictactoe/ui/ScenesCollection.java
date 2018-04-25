package ru.spbau.annikura.tictactoe.ui;

import com.sun.prism.paint.Paint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.tictactoe.backends.FileDataController;
import ru.spbau.annikura.tictactoe.backends.Stats;
import ru.spbau.annikura.tictactoe.controllers.*;

/**
 * Scenes creator class
 */
public class ScenesCollection {
        private final static String MAIN_CSS = "-fx-background-color: #0048ad";
    private final static int BUTTONS_WIDTH = 150;
    private final static int BUTTONS_HEIGHT = 25;

    /**
     * Creates new main menu scene.
     * @param width width of the new scene
     * @param height height of the new scene
     * @param stage a stage that will be switched to new scene when the button is pressed
     * @return generated scene with given parameters.
     */
    public static Scene newMainMenuScene(double width, double height, @NotNull Stage stage) {
        Button newGameButton = new Button("New Game");
        Button statsButton = new Button("Show Stats");
        resizeElements(BUTTONS_WIDTH, BUTTONS_HEIGHT, newGameButton, statsButton);

        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(newGameButton, statsButton);

        newGameButton.setOnAction(event -> stage.setScene(
                ScenesCollection.newChooseModeScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage)));
        statsButton.setOnAction(event -> stage.setScene(
                ScenesCollection.newStatsScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage)));



        vBox.setStyle(MAIN_CSS);
        return new Scene(vBox, width, height);
    }

    /**
     * Creates menu scene, where player can choose mode of the game ans size of the field.
     * @param width width of the new scene
     * @param height height of the new scene
     * @param stage a stage that will be switched to new scene when the button is pressed
     * @return generated scene with given parameters.
     */
    public static Scene newChooseModeScene(double width, double height, @NotNull Stage stage) {
        Button easyModeButton = new Button("Easy Computer");
        Button hardModeButton = new Button("Hard Computer");
        Button hotSeatButton = new Button("Hot Seat");
        Button backToMainMenu = new Button("To Main Menu");

        ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(3, 5, 7);
        comboBox.setValue(3);
        resizeElements(BUTTONS_WIDTH, BUTTONS_HEIGHT, easyModeButton, hardModeButton, hotSeatButton, backToMainMenu, comboBox);

        VBox buttonBox = new VBox(10);
        VBox menuBox = new VBox(100);
        buttonBox.setAlignment(Pos.CENTER);
        menuBox.setAlignment(Pos.CENTER);

        Text modeText = new Text("Mode");
        Text boardSizeText = new Text("Board size");
        boardSizeText.setFill(Color.WHITE);
        modeText.setFill(Color.WHITE);

        buttonBox.getChildren().addAll(boardSizeText, comboBox, modeText, easyModeButton, hardModeButton, hotSeatButton);
        menuBox.getChildren().addAll(buttonBox, backToMainMenu);

        easyModeButton.setOnAction(e -> stage.setScene(
                ScenesCollection.newGameScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage,
                        new EasyGameController(comboBox.getValue(), Math.min(comboBox.getValue(), 5)))));

        hardModeButton.setOnAction(e -> stage.setScene(
                ScenesCollection.newGameScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage,
                        new HardGameController(comboBox.getValue(), Math.min(comboBox.getValue(), 5)))));
        hotSeatButton.setOnAction(e -> stage.setScene(
                ScenesCollection.newGameScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage,
                        new HotSeatGameController(comboBox.getValue(), Math.min(comboBox.getValue(), 5)))));
        backToMainMenu.setOnAction(e -> stage.setScene(
                ScenesCollection.newMainMenuScene(width, height, stage)));

        menuBox.setStyle(MAIN_CSS);
        return new Scene(menuBox, width, height);
    }


    /**
     * Creates new game scene.
     * @param width width of the new scene
     * @param height height of the new scene
     * @param stage a stage that will be switched to new scene when the corresponding button is pressed
     * @param gameController game logic owning class
     * @return generated scene with given parameters.
     */
    public static Scene newGameScene(double width, double height,
                                     @NotNull Stage stage,
                                     @NotNull GameController gameController) {
        int gameSize = gameController.getField().getSize();

        Button restartButton = new Button("Restart");
        Button newGameButton = new Button("Start New Game");
        Button backToMenuButton = new Button("Back To Menu");
        resizeElements(BUTTONS_WIDTH, BUTTONS_HEIGHT, restartButton, newGameButton, backToMenuButton);

        VBox buttonsBox = new VBox(10);
        buttonsBox.getChildren().addAll(restartButton, newGameButton, backToMenuButton);
        buttonsBox.setPadding(new Insets(30, 20, 30, 30));

        Region gameBoard = newGameBoard(gameSize, gameController);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(createFixedSizedPane(0,20));
        borderPane.setBottom(createFixedSizedPane(0,20));
        borderPane.setRight(createFixedSizedPane(20,0));
        borderPane.setLeft(buttonsBox);
        borderPane.setCenter(gameBoard);

        restartButton.setOnAction(e -> stage.setScene(
                newGameScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage, gameController.newGame())));
        newGameButton.setOnAction(e -> stage.setScene(
                newChooseModeScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage)));
        backToMenuButton.setOnAction(e -> stage.setScene(
                newMainMenuScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage)));


        borderPane.setStyle(MAIN_CSS);
        return new Scene(borderPane, width, height);
    }

    /**
     * Creates new game board region.
     * @param size number of the rows/columns on the game field
     * @param controller game controller
     * @return new game board ui element
     */
    public static Region newGameBoard(int size, @NotNull GameController controller) {
        Button[][] buttons = new Button[size][size];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 10, 15, 10));

        BorderPane field = new BorderPane();
        field.setPadding(new Insets(10));

        Text text = new Text("Game status: X TURN");
        text.setFill(Color.WHITE);

        VBox vBox = new VBox(text);
        vBox.setAlignment(Pos.CENTER);

        BorderPane wrappingBoard = new BorderPane();
        wrappingBoard.setCenter(field);
        wrappingBoard.setStyle("-fx-background-color: #000000;");

        ColumnConstraints[] columnConstraints = new ColumnConstraints[size];
        RowConstraints[] rowConstraints= new RowConstraints[size];
        for (int i = 0; i < size; i++) {
            columnConstraints[i] = new ColumnConstraints();
            columnConstraints[i].setFillWidth(true);
            columnConstraints[i].setPercentWidth(100);

            rowConstraints[i] = new RowConstraints();
            rowConstraints[i].setFillHeight(true);
            rowConstraints[i].setPercentHeight(100);
        }

        grid.getRowConstraints().addAll(rowConstraints);
        grid.getColumnConstraints().addAll(columnConstraints);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j] = new Button();
                buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttons[i][j].setMinSize(50, 50);
                buttons[i][j].setOnAction(event -> {
                    if (controller.getStatus().isFinished()) {
                        return;
                    }
                    GameField newField = controller.makeMove(finalI, finalJ);
                    for (int i1 = 0; i1 < size; i1++) {
                        for (int j1 = 0; j1 < size; j1++) {
                            String symbol = "";
                            switch (newField.get(i1, j1)) {
                                case X:
                                    symbol = "X";
                                    break;
                                case O:
                                    symbol = "O";
                                    break;
                                case EMPTY:
                                    break;
                            }
                            if (!buttons[i1][j1].getText().equals(symbol)) {
                                buttons[i1][j1].setStyle("-fx-font: 22 arial; -fx-base: #e3f3ff;");
                            } else {
                                buttons[i1][j1].setStyle("-fx-font: 22 arial;");
                            }
                            buttons[i1][j1].setText(symbol);
                        }
                    }
                    text.setText("Game status: " + controller.getStatus().toString().replace('_', ' '));
                    if (controller.getStatus().isFinished()) {
                        FileDataController.updateStats(controller.getStatus());
                    }
                });
                grid.add(buttons[i][j], i, j);
            }
        }

        grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        field.setCenter(grid);
        field.setTop(vBox);
        return wrappingBoard;
    }

    /**
     * Creates a siple pane with the fixed size.
     * @param width width the pane will be created with
     * @param height height the pane will be created with
     * @return created pane
     */
    private static Pane createFixedSizedPane(int width, int height) {
        Pane pane = new Pane();
        pane.setPrefSize(width, height);
        pane.setMaxSize(width, height);
        pane.setMinSize(width, height);
        return pane;
    }

    /**
     * Takes a bunch of Regions and sets them new fixed sizes
     * @param newWidth width to set
     * @param newHeight height to set
     * @param regions Regions to resize
     */
    private static void resizeElements(double newWidth, double newHeight, @NotNull Region... regions) {
        for (Region region : regions) {
            region.setPrefWidth(newWidth);
            region.setPrefHeight(newHeight);
        }
    }


    /**
     * Creates menu scene, showing number of wins for each type of player.
     * @param width width of the new scene
     * @param height height of the new scene
     * @param stage a stage that will be switched to new scene when the button is pressed
     * @return generated scene with given parameters.
     */
    public static Scene newStatsScene(double width, double height, @NotNull Stage stage) {
        Stats stats = FileDataController.getStats();
        Text totalsText = new Text("Games in total: " + Integer.toString(stats.getTotal()));
        Text xWinsText = new Text("X won: " + Integer.toString(stats.getXWon()));
        Text oWinsText = new Text("O won: " + Integer.toString(stats.getOWon()));
        Text draws = new Text("Draws: " + Integer.toString(stats.getDraw()));

        totalsText.setFill(Color.WHITE);
        xWinsText.setFill(Color.WHITE);
        oWinsText.setFill(Color.WHITE);
        draws.setFill(Color.WHITE);

        Button backToMenuButton = new Button("Back To Menu");
        backToMenuButton.setOnAction(e -> stage.setScene(
                newMainMenuScene(
                        stage.getScene().getWidth(),
                        stage.getScene().getHeight(), stage)));

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);
        box.getChildren().addAll(totalsText, xWinsText, oWinsText, draws, backToMenuButton);

        box.setStyle(MAIN_CSS);
        return new Scene(box, width, height);
    }
}
