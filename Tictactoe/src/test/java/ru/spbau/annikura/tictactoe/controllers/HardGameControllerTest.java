package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.EMPTY;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.O;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.X;

public class HardGameControllerTest {
    @Test
    public void createInstance() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
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
        HardGameController controller = new HardGameController(5, 4);
        for (int i = 0; i < 2; i++)
            controller.field.set(0, i, X);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(0, 3));
    }

    /*
    x----           x----
    x----           x----
    x----   |-->    x---- X blocked
    -----           o----
    -----           -----
     */
    @Test
    public void winWithO() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
        for (int i = 0; i < 2; i++) {
            controller.field.set(1, i, O);
        }
        controller.field.set(1, 2, O);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(1, 3));
        assertEquals(EMPTY, controller.getField().get(0, 3));
    }

    /*
    -o---           -o---
    -o---           -o---
    xo---   |-->    xo--- O wins
    -----           -o---
    -----           -----
     */
    @Test
    public void winWithODontBlockX() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
        for (int i = 0; i < 2; i++) {
            controller.field.set(0, i, X);
            controller.field.set(1, i, O);
        }
        controller.field.set(1, 2, O);
        controller.makeMove(0, 2);
        assertEquals(O, controller.getField().get(1, 3));
        assertEquals(EMPTY, controller.getField().get(0, 3));
    }


    /*
    -----           -----
    -o---           -o---
    xo---   |-->    xo---
    -----           -o---
    -----           -----
    */
    @Test
    public void findNotSoEasyWin() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
        for (int i = 1; i < 3; i++) {
            controller.field.set(1, i, O);
        }
        controller.makeMove(0, 2);
        assertEquals(O, controller.field.get(1, 3));
    }

    /*
    -----           -----
    -x---           -x---
    --x--   |-->    --x--
    -----           ---o-
    -----           -----
    */
    @Test
    public void findNotSoEasyBlock() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
        controller.field.set(1, 1, X);

        controller.makeMove(2, 2);
        assertEquals(O, controller.field.get(3, 3));
    }

    @Test
    public void newGameCreation() throws Exception {
        EasyGameController controller = new EasyGameController(5, 3);
        controller.makeMove(1, 1);
        EasyGameController newController = (EasyGameController) controller.newGame();

        assertEquals(5, newController.getField().getSize());
        assertEquals(3, newController.amountToWin);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(EMPTY, newController.field.get(i, j));
            }
        }
    }
}