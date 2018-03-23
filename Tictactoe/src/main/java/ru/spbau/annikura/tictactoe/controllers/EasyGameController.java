package ru.spbau.annikura.tictactoe.controllers;

/**
 * Easy game controller. Provides game with a simple bot with small recursion depth
 */
public class EasyGameController extends AbstractBotGameController {

    /**
     * Easy game controller constructor.
     * @param size size of the game field
     * @param cnt number of cells in a row filled with one cells type required to win
     */
    public EasyGameController(int size, int cnt) {
        super(size, cnt);
        tictactoeBot = new TictactoeBot(2, amountToWin);
    }

    /**
     * Creates new game with the same parameters
     * @return new easy game controller
     */
    @Override
    public GameController newGame() {
        return new EasyGameController(field.getSize(), amountToWin);
    }
}
