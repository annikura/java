package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Game board logic.
 */
public class GameField {
    protected Cell[][] field;
    private int emptyCounter = 0;

    /**
     * Cell possible states enum.
     */
    public enum Cell {
        X,
        O,
        EMPTY
    }

    /**
     * Gets symbol on the corresponding coordinates
     * @param column board column index
     * @param row row column index
     * @return symbol in the cell
     */
    public Cell get(int column, int row) {
        return field[column][row];
    }

    /**
     * Field size getter.
     * @return field size.
     */
    public int getSize() {
        return field.length;
    }

    /**
     * Checks that given coordinates are in range of the field
     * @param x column index
     * @param y row index
     * @return true if the cell is in field, false otherwise
     */
    public boolean inRange(int x, int y) {
        return x >= 0 && y >= 0 && x < getSize() && y < getSize();
    }

    /**
     * GameField constructor. Creates a game field of the given size.
     * @param size board size
     */
    GameField(int size) {
        field = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = Cell.EMPTY;
            }
        }
        emptyCounter = size * size;
    }

    /**
     * Number of EMPTY cells on the board counter
     * @return number of EMPTY cells
     */
    int getEmptyCellsCount() {
        return emptyCounter;
    }

    /**
     * Looks through the board in 4 directions and finds the length of the biggest row consisting of the given symbol.
     * @param o symbol to look at
     * @return maximal length of the row
     */
    int getMaxRow(@NotNull Cell o) {
        int max = 0;

        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                max = Math.max(max, getRowInDirection(o, i, j, 0, 1)); // down direction
                max = Math.max(max, getRowInDirection(o, i, j, 1, 0)); // right direction
                max = Math.max(max, getRowInDirection(o, i, j, 1, 1)); // diagonal(down-right) direction
                max = Math.max(max, getRowInDirection(o, i, j, 1, -1    )); // diagonal(up-right) direction
            }
        }
        return max;
    }

    /**
     * Cell setter
     * @param column coordinate
     * @param row coordinate
     * @param newValue symbol to be places at the coordinate
     */
    void set(int column, int row, Cell newValue) {
        if (field[column][row].equals(Cell.EMPTY)  && !newValue.equals(Cell.EMPTY)) {
            emptyCounter--;
        }
        if (!field[column][row].equals(Cell.EMPTY) && newValue.equals(Cell.EMPTY)) {
            emptyCounter++;
        }
        field[column][row] = newValue;
    }

    /**
     * Counts number of cells equal to a symbol in a row in the given direction
     * @param symbol checked symbol
     * @param x column index
     * @param y row index
     * @param dx x delta
     * @param dy y delta
     * @return number of consequent cells equals tp symbol in row in the given direction
     */
    private int getRowInDirection(@NotNull Cell symbol, int x, int y, int dx, int dy) {
        int cnt = 0;
        while (inRange(x, y) && get(x, y).equals(symbol)) {
            cnt++;
            x += dx;
            y += dy;
        }
        return cnt;
    }
}

