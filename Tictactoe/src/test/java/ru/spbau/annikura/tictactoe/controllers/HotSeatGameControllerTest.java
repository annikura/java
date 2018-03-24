package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameController.GameStatus.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.*;

public class HotSeatGameControllerTest {
    @Test
    public void createInstance() throws Exception {
        HotSeatGameController controller = new HotSeatGameController(5, 5);
    }

    @Test
    public void checkPlayerChange() throws Exception {
        HotSeatGameController controller = new HotSeatGameController(5, 5);
        assertEquals(X_TURN, controller.getStatus());
        controller.makeMove(1, 1);
        assertEquals(O_TURN, controller.getStatus());
    }

    @Test
    public void checkSymbolChange() throws Exception {
        HotSeatGameController controller = new HotSeatGameController(5, 5);
        controller.makeMove(1, 1);
        controller.makeMove(1, 2);
        controller.makeMove(1, 3);
        assertEquals(X, controller.getField().get(1, 1));
        assertEquals(O, controller.getField().get(1, 2));
        assertEquals(X, controller.getField().get(1, 3));
    }

    @Test
    public void checkSymbolDoesntChangeOnBadMove() throws Exception {
        HotSeatGameController controller = new HotSeatGameController(5, 5);
        controller.makeMove(1, 1);
        controller.makeMove(1, 1);
        controller.makeMove(1, 3);
        assertEquals(X, controller.getField().get(1, 1));
        assertEquals(O, controller.getField().get(1, 3));
    }

    @Test
    public void newGameCreation() throws Exception {
        HotSeatGameController controller = new HotSeatGameController(5, 3);
        controller.makeMove(1, 1);
        HotSeatGameController newController = (HotSeatGameController) controller.newGame();

        assertEquals(X_TURN, newController.getStatus());
        assertEquals(5, newController.getField().getSize());
        assertEquals(3, newController.amountToWin);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(EMPTY, newController.field.get(i, j));
            }
        }

        newController.makeMove(4, 4);
        newController.makeMove(4, 3);

        assertEquals(X, newController.field.get(4, 4));
        assertEquals(O, newController.field.get(4, 3));
    }
}