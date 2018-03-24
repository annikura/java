package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Hot seat controller. Allows to 2 people to play together
 */
public class HotSeatGameController extends AbstractGameController {
    /**
     * HotSeat game controller constructor.
     * @param size size of the game field
     * @param cnt number of cells in a row filled with one cells type required to win
     */
    public HotSeatGameController(int size, int cnt) {
        super(size, cnt);
    }

    /**
     * Does nothing because hotseat controller doesn't have a bot or additional action
     */
    @Override
    protected void finishMove() { }

    /**
     * Creates new game with the same parameters
     * @return new hotseat game controller
     */
    @NotNull
    @Override
    public GameController newGame() {
        return new HotSeatGameController(field.getSize(), amountToWin);
    }
}
