package ru.spbau.annikura.pair.ui;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.pair.controllers.GameController;

/**
 * Scenes creator class
 */
public class ScenesCollection {
    private final static String MAIN_CSS = "-fx-background-color: #0048ad";

    /**
     * Creates new game scene.
     * @param width width of the new scene
     * @param height height of the new scene
     * @return generated scene with given parameters.
     */
    public static Scene newGameScene(double width, double height, int boardSize) {
        Region board = newGameBoard(boardSize);
        board.setStyle(MAIN_CSS);
        return new Scene(board, width, height);
    }

    /**
     * Creates new game board region.
     * @param size number of the rows/columns on the game field
     * @return new game board ui element
     */
    public static Region newGameBoard(int size) {
        Button[][] buttons = new Button[size][size];
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(5, 10, 15, 10));

        BorderPane field = new BorderPane();
        field.setPadding(new Insets(10));

        Text text = new Text("CHOOSE FIRST TILE");
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

        GameController controller = new GameController(size);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                final int finalI = i;
                final int finalJ = j;
                buttons[i][j] = new Button();
                buttons[i][j].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                buttons[i][j].setMinSize(50, 50);
                buttons[i][j].setOnAction(event -> {
                    updateBoard(buttons, controller.makeMove(finalI, finalJ), size);
                    updateBoard(buttons, controller.getBoard(), size);
                    text.setText(controller.getStatus().toString().replace('_', ' '));
                    if (controller.getStatus().equals(GameController.GameStatus.WRONG_MATCH) ||
                            controller.getStatus().equals(GameController.GameStatus.SUCCESSFUL_MATCH)) {
                        new Thread(() -> {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ignored) { }
                            Platform.runLater(() -> {
                                updateBoard(buttons, controller.makeDecision(), size);
                                text.setText(controller.getStatus().toString().replace('_', ' '));
                            });
                        }).start();
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
     * Given an array of buttons and a game field, sets the buttons into a state corresponding to the state of the field.
     * @param buttons set of buttons that will be modified.
     * @param field state of the field which buttons will be changes to
     * @param size size of the board
     */
    private static void updateBoard(@NotNull Button[][] buttons, @NotNull GameController.GameField field, int size) {
        for (int i1 = 0; i1 < size; i1++) {
            for (int j1 = 0; j1 < size; j1++) {
                GameController.Cell cell = field.get(i1, j1);
                String symbol = cell.getValue();
                if (cell.isDisabled()) {
                    buttons[i1][j1].setDisable(true);
                } else if (cell.isHighlighted()) {
                    buttons[i1][j1].setStyle("-fx-font: 22 arial; -fx-base: #e3f3ff;");
                } else {
                    buttons[i1][j1].setStyle("-fx-font: 22 arial;");
                }
                buttons[i1][j1].setText(symbol);
            }
        }

    }
}
