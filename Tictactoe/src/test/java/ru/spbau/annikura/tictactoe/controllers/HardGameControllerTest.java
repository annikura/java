package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.EMPTY;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.O;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.X;

public class HardGameControllerTest {

    private void showField(GameField field) {
        for (int i = 0; i < field.getSize(); i++) {
            for (int j = 0; j < field.getSize(); j++) {
                System.out.print(field.get(j, i).toString().charAt(0));
            }
            System.out.println();
        }
    }

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
        showField(controller.field);
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
        showField(controller.field);
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
        showField(controller.field);
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
    public void findNotEasyBlock() throws Exception {
        HardGameController controller = new HardGameController(5, 4);
        controller.field.set(1, 1, X);

        controller.makeMove(2, 2);
        assertEquals(O, controller.field.get(3, 3));
    }
}