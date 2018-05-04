package ru.spbau.annikura.pair.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Pair game controller.
 */
public class GameController {
    private final GameField board;
    private GameStatus status = GameStatus.CHOOSE_FIRST_TILE;
    private int chosenValue;
    private final int size;

    public GameStatus getStatus() {
        return status;
    }

    /**
     * Creates new game with given board size.
     * @param size size of the new board
     */
    public GameController(int size) {
        assert size % 2 == 0;
        this.size = size;
        board = GameField.generate(size);
    }

    /**
     * Processes the current state of the board and if it matches a match-try state,
     * changes the state of the highlighted pair according to the status.
     * @return new board state.
     */
    public GameField makeDecision() {
        if (!status.equals(GameStatus.WRONG_MATCH) && !status.equals(GameStatus.SUCCESSFUL_MATCH)) {
            return board;
        }
        boolean notEnd = false;

        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                Cell currentCell = board.get(i, j);
                if (!currentCell.isHighlighted) {

                    board.get(i, j).setHighlight(false);
                    if (status.equals(GameStatus.SUCCESSFUL_MATCH)) {
                        currentCell.disable();
                    }
                }
                if (!currentCell.isDisabled) {
                    notEnd = true;
                }
            }
        status = notEnd ? GameStatus.CHOOSE_FIRST_TILE : GameStatus.THE_END;
        return board;
    }

    /**
     * Makes move, trying to highlight a cell on the given position.
     * If this cell already disabled of highlighted, nothing happens.
     * @param i cell column.
     * @param j cell row.
     * @return new state of the board.
     */
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

    /**
     * Class for indicating the state of the Pair game.
     */
    public enum GameStatus {
        CHOOSE_FIRST_TILE,
        CHOOSE_SECOND_TILE,
        THE_END,
        SUCCESSFUL_MATCH,
        WRONG_MATCH
    }

    /**
     * Game board cell class.
     */
    public static class Cell {
        private final int value;
        private boolean isDisabled = false;
        private boolean isHighlighted = false;

        /**
         * Creates an enabled not highlighted cell holding a given value.
         * @param value stored value.
         */
        public Cell(int value) {
            this.value = value;
        }

        /**
         * Disables the cell.
         */
        void disable() {
            isDisabled = true;
        }

        /**
         * Sets cell highlighting to the given one.
         * @param highlight highlighting to be set.
         */
        void setHighlight(boolean highlight) {
            isHighlighted = highlight;
        }

        /**
         * Processes value to a string format depending on the button state.
         * @return value if cell is highlighted, empty string otherwise.
         */
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

    /**
     * Class storing a Pair game board.
     */
    public static class GameField {
        private Cell[][] board;

        /**
         * Cell getter.
         * @param i cell column
         * @param j cell row
         * @return cell located on the given position.
         */
        public Cell get(int i, int j) {
            return board[i][j];
        }

        /**
         * Creates a board of the given size filled with nulls.
         * @param size new board size.
         */
        GameField(int size) {
            board = new Cell[size][size];
        }

        /**
         * Generates new board of the given size filled with pairs of numbers in range 0..size^2 / 2 distributed randomly on the board.
         * @param size size of the board.
         * @return generated board.
         */
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
