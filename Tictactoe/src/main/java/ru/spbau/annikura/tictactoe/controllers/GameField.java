package ru.spbau.annikura.tictactoe.controllers;

public class GameField {
    protected Cell[][] field;
    private int emptyCounter = 0;

    public enum Cell {
        X,
        O,
        EMPTY
    }

    public Cell get(int column, int row) {
        return field[column][row];
    }

    public int getSize() {
        return field.length;
    }

    public boolean inRange(int x, int y) {
        return x >= 0 && y >= 0 && x < getSize() && y < getSize();
    }

    GameField(int size) {
        field = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = Cell.EMPTY;
            }
        }
        emptyCounter = size * size;
    }

    int getEmptyCellsCount() {
        return emptyCounter;
    }

    int getMaxRow(Cell o) {
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

    void set(int column, int row, Cell newValue) {
        if (field[column][row].equals(Cell.EMPTY)  && !newValue.equals(Cell.EMPTY)) {
            emptyCounter--;
        }
        if (!field[column][row].equals(Cell.EMPTY) && newValue.equals(Cell.EMPTY)) {
            emptyCounter++;
        }
        field[column][row] = newValue;
    }

    private int getRowInDirection(Cell symbol, int x, int y, int dx, int dy) {
        int cnt = 0;
        while (inRange(x, y) && get(x, y).equals(symbol)) {
            cnt++;
            x += dx;
            y += dy;
        }
        return cnt;
    }
}

