package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.*;

public class GameFieldTest {
    @Test
    public void createInstance() {
        GameField gameField = new GameField(5);
        assertEquals(5, gameField.getSize());
    }

    @Test
    public void checkIsEmpty() {
        GameField gameField = new GameField(5);
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                assertEquals(EMPTY, gameField.get(i, j));
            }
        }
    }

    @Test
    public void singleAssignment() {
        GameField gameField = new GameField(10);
        int x = 4, y = 6;
        gameField.set(x, y, X);
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                assertEquals(i != x || j != y ? EMPTY : X, gameField.get(i, j));
            }
        }
    }

    @Test
    public void multipleAssignment() {
        GameField gameField = new GameField(10);
        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = i % 2; j < gameField.getSize(); j += 2) {
                gameField.set(i, j, O);
            }
        }

        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = 0; j < gameField.getSize(); j++) {
                assertEquals((i + j) % 2 == 0 ? O : EMPTY, gameField.get(i, j));
            }
        }
    }

    @Test
    public void checkNumberOfOneEmptyCell() {
        GameField gameField = new GameField(10);
        int x = 4, y = 6;
        gameField.set(x, y, X);
        assertEquals(gameField.getSize() * gameField.getSize() - 1, gameField.getEmptyCellsCount());
    }

    @Test
    public void checkNumberOfEmptyCells() {
        GameField gameField = new GameField(10);
        int cnt = 0;

        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = i % 2; j < gameField.getSize(); j += 2) {
                gameField.set(i, j, O);
                cnt++;
            }
        }
        assertEquals(gameField.getSize() * gameField.getSize() - cnt, gameField.getEmptyCellsCount());
    }

    @Test
    public void checkNumberOfEmptyCellsWithReassignment() {
        GameField gameField = new GameField(10);
        int cnt = 0;

        for (int i = 0; i < gameField.getSize(); i++) {
            for (int j = i % 2; j < gameField.getSize(); j += 2) {
                gameField.set(i, j, O);
                cnt++;
            }
        }
        for (int i = 0; i < gameField.getSize(); i += 2) {
            for (int j = 0; j < gameField.getSize(); j += 2) {
                cnt--;
                gameField.set(i, j, EMPTY);
            }
        }

        assertEquals(gameField.getSize() * gameField.getSize() - cnt, gameField.getEmptyCellsCount());
    }

    @Test
    public void getVerticalRow() {
        GameField gameField = new GameField(5);

        gameField.set(0, 0, O);
        gameField.set(0, 2, O);
        gameField.set(0, 3, O);
        gameField.set(0, 4, O);

        assertEquals(3, gameField.getMaxRow(O));
        assertEquals(0, gameField.getMaxRow(X));
    }

    @Test
    public void getHorizontalRow() {
        GameField gameField = new GameField(5);

        gameField.set(0, 0, X);
        gameField.set(1, 0, O);
        gameField.set(2, 0, O);
        gameField.set(3, 0, O);
        gameField.set(4, 0, O);

        assertEquals(4, gameField.getMaxRow(O));
        assertEquals(1, gameField.getMaxRow(X));
    }

    @Test
    public void getDownRightRow() {
        GameField gameField = new GameField(5);

        gameField.set(0, 0, X);
        gameField.set(1, 1, O);
        gameField.set(2, 2, O);
        gameField.set(3, 3, X);
        gameField.set(4, 4, X);

        assertEquals(2, gameField.getMaxRow(O));
        assertEquals(2, gameField.getMaxRow(X));
    }

    @Test
    public void getUpRightRow() {
        GameField gameField = new GameField(5);

        gameField.set(0, 4, O);
        gameField.set(1, 3, O);
        gameField.set(2, 2, O);
        gameField.set(3, 1, O);
        gameField.set(4, 0, X);

        assertEquals(4, gameField.getMaxRow(O));
        assertEquals(1, gameField.getMaxRow(X));
    }
}