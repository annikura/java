package ru.spbau.annikura.tictactoe.controllers;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.annikura.tictactoe.controllers.GameField.Cell.*;

public class EasyGameControllerTest {
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
}