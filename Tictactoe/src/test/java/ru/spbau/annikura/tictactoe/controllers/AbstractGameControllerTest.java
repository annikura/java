package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameController.GameStatus.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.*;

public class AbstractGameControllerTest {
    private AbstractGameController controller;
    private int size = 3;
    private int toWin = 2;

    @Before
    public void beforeTest() {
        controller = new AbstractGameController(size, toWin) {
            @Override
            protected void finishMove() {
            }

            @Override
            public GameController newGame() {
                return null;
            }
        };
    }

    @Test
    public void createInstance() throws Exception {
        assertEquals(X_TURN, controller.status);
        assertEquals(size, controller.getField().getSize());

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                assertEquals(EMPTY, controller.getField().get(i, j));
        }
    }


    /*
    Final result:

    xo-
    x--
    ---
     */
    @Test
    public void checkXWinStatusChange() throws Exception {
        assertEquals(X_TURN, controller.status);
        controller.makeMove(0, 0);
        assertEquals(O_TURN, controller.status);
        controller.makeMove(1, 0);
        assertEquals(X_TURN, controller.status);
        controller.makeMove(0, 1);
        assertEquals(X_WON, controller.status);

        assertEquals(X, controller.getField().get(0, 0));
        assertEquals(X, controller.getField().get(0, 1));
        assertEquals(O, controller.getField().get(1, 0));
    }

    /*
    Final result:

    xo-
    -o-
    --x
     */
    @Test
    public void checkOWinStatusChange() throws Exception {
        assertEquals(X_TURN, controller.status);
        controller.makeMove(0, 0);
        assertEquals(O_TURN, controller.status);
        controller.makeMove(1, 0);
        assertEquals(X_TURN, controller.status);
        controller.makeMove(2, 2);
        assertEquals(O_TURN, controller.status);
        controller.makeMove(1, 1);
        assertEquals(O_WON, controller.status);

        assertEquals(X, controller.getField().get(0, 0));
        assertEquals(X, controller.getField().get(2, 2));
        assertEquals(O, controller.getField().get(1, 0));
        assertEquals(O, controller.getField().get(1, 1));
    }

    @Test
    public void checkDrawStatusChange() throws Exception {
        controller = new AbstractGameController(size, 4) {
            @Override
            protected void finishMove() {
            }

            @Override
            public GameController newGame() {
                return null;
            }
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                controller.makeMove(i, j);
        }
        assertEquals(DRAW, controller.status);
    }

    @Test
    public void checkNothingChangesWhenDoubleSet() throws Exception {
        assertEquals(X_TURN, controller.status);
        controller.makeMove(0, 0);
        assertEquals(O_TURN, controller.status);
        controller.makeMove(0, 0);
        assertEquals(O_TURN, controller.status);
        assertEquals(X, controller.getField().get(0, 0));
    }
}