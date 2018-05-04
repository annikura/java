package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class GameController {
    private final GameField board;
    private GameStatus status = GameStatus.CHOOSE_FIRST_TILE;
    private int chosenValue;
    private final int size;

    public GameStatus getStatus() {
        return status;
    }

    public GameController(int size) {
        assert size % 2 == 0;
        this.size = size;
        board = GameField.generate(size);
    }

    public GameField makeDecision() {
        if (!status.equals(GameStatus.WRONG_MATCH) && !status.equals(GameStatus.SUCCESSFUL_MATCH)) {
            return board;
        }
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                Cell currentCell = board.get(i, j);
                if (!currentCell.isHighlighted) {
                    continue;
                }
                board.get(i, j).setHighlight(false);
                if (status.equals(GameStatus.SUCCESSFUL_MATCH)) {
                    currentCell.disable();
                }
            }
        status = GameStatus.CHOOSE_FIRST_TILE;
        return board;
    }
    
    public GameField makeMove(int i, int j) {
        if (board.get(i, j).isDisabled || board.get(i, j).isHighlighted) {
            return board;
        }
        assert status.equals(GameStatus.CHOOSE_FIRST_TILE) || status.equals(GameStatus.CHOOSE_SECOND_TILE);
        if (status.equals(GameStatus.CHOOSE_FIRST_TILE)) {
            chosenValue = board.get(i, j).value;
            board.get(i, j).setHighlight(true);
            status = GameStatus.CHOOSE_SECOND_TILE;
        } else if (status.equals(GameStatus.CHOOSE_FIRST_TILE)) {
            assert chosenValue != -1;
            chosenValue = -1;
            board.get(i, j).setHighlight(true);
            if (chosenValue == board.get(i, j).value) {
                status = GameStatus.SUCCESSFUL_MATCH;
            } else {
                status = GameStatus.WRONG_MATCH;
            }
        }
        return board;
    }

    public enum GameStatus {
        CHOOSE_FIRST_TILE,
        CHOOSE_SECOND_TILE,
        THE_END,
        SUCCESSFUL_MATCH,
        WRONG_MATCH
    }

    public static class Cell {
        private final int value;
        private boolean isDisabled = false;
        private boolean isHighlighted = false;

        public Cell(int value) {
            this.value = value;
        }

        void disable() {
            isDisabled = true;
        }

        void setHighlight(boolean highlight) {
            isHighlighted = highlight;
        }

        @NotNull
        public String getValue() {
            if (isHighlighted) {
                return String.valueOf(value);
            }
            return "";
        }

        public boolean isDisabled() {
            return isDisabled;
        }

        public boolean isHighlighted() {
            return isHighlighted;
        }
    }

    public static class GameField {
        private Cell[][] board;

        public Cell get(int i, int j) {
            return board[i][j];
        }

        GameField(int size) {
            board = new Cell[size][size];
        }

        public static GameField generate(int size) {
            GameField newBoard = new GameField(size);

            int[] values = new int[size * size];
            for (int i = 0; i < size * size; i += 2) {
                // filling an array with pairs of numbers in range [0, size^2 / 2]
                values[i] = i / 2;
                values[i + 1] = i / 2;

            }

            for (int i = 0, cnt = 0; i < size; i++) {
                for (int j = 0; j < size; j++, cnt++) {
                    newBoard.board[i][j] = new Cell(values[cnt]);
                }
            }
            return newBoard;
        }
    }
}
