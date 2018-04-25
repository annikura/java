package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.*;

public class EasyGameControllerTest {
    @Test
    public void createInstance() throws Exception {
        EasyGameController controller = new EasyGameController(5, 4);
    }

    /*
    x----           x----
    x----           x----
    x----   |-->    x---- X blocked
    -----           o----
    -----           -----
     */
    @Test
    public void blockXWin() throws Exception {
        EasyGameController controller = new EasyGameController(5, 4);
        for (int i = 0; i < 2; i++)
            controller.field.set(0, i, X);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(0, 3));
    }

    /*
    -o---           -o---
    -o---           -o---
    xo---   |-->    xo--- O wins
    -----           -o---
    -----           -----
     */
    @Test
    public void winWithO() throws Exception {
        EasyGameController controller = new EasyGameController(5, 4);
        for (int i = 0; i < 2; i++) {
            controller.field.set(1, i, O);
        }
        controller.field.set(1, 2, O);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(1, 3));
        assertEquals(EMPTY, controller.getField().get(0, 3));
    }

    /*
    xo---           xo---
    xo---           xo---
    xo---   |-->    xo--- O wins
    -----           -o---
    -----           -----
    */
    @Test
    public void winWithODontBlockX() throws Exception {
        EasyGameController controller = new EasyGameController(5, 4);
        for (int i = 0; i < 2; i++) {
            controller.field.set(0, i, X);
            controller.field.set(1, i, O);
        }
        controller.field.set(1, 2, O);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(1, 3));
        assertEquals(EMPTY, controller.getField().get(0, 3));
    }

    @Test
    public void newGameCreation() throws Exception {
        HardGameController controller = new HardGameController(5, 3);
        controller.makeMove(1, 1);
        HardGameController newController = (HardGameController) controller.newGame();

        assertEquals(5, newController.getField().getSize());
        assertEquals(3, newController.amountToWin);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(EMPTY, newController.field.get(i, j));
            }
        }
    }

    @Test
    public void checkNothingChangesAfterWin() throws Exception {
        EasyGameController controller = new EasyGameController(5, 1);
        controller.makeMove(0, 0);
        controller.makeMove(3, 3);
        controller.makeMove(3, 4);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j > 0)
                    assertEquals(EMPTY, controller.field.get(i, j));
            }
        }
    }
}