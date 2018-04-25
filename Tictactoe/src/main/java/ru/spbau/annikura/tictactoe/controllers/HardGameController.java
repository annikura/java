package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Hard game controller. Provides game with a hard bot with average recursion depth
 */
public class HardGameController extends AbstractBotGameController {
    /**
     * Easy game controller constructor.
     * @param size size of the game field
     * @param cnt number of cells in a row filled with one cells type required to win
     */
    public HardGameController(int size, int cnt) {
        super(size, cnt);
        tictactoeBot = new TictactoeBotImplementation(4, amountToWin);
    }

    /**
     * Creates new game with the same parameters
     * @return new hard game controller
     */
    @NotNull
    @Override
    public GameController newGame() {
        return new HardGameController(field.getSize(), amountToWin);
    }
}
