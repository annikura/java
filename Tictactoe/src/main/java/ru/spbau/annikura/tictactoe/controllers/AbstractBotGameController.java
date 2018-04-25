package ru.spbau.annikura.tictactoe.controllers;

/**
 * Abstracting class for bot controllers: contains bot and always finishes move with bot move.
 */
public abstract class AbstractBotGameController extends AbstractGameController {
    TictactoeBot tictactoeBot;

    /**
     * Abstract bot controller constructor.
     * @param size size of the game field
     * @param cnt number of cells in a row filled with one cells type required to win
     */
    AbstractBotGameController(int size, int cnt) {
        super(size, cnt);
    }

    /**
     * Makes bot move after the player's move was made.
     */
    @Override
    protected void finishMove() {
        if (status.isFinished()) {
            return;
        }
        GameField.Cell nextSymbol = status.equals(GameStatus.X_TURN) ? GameField.Cell.X : GameField.Cell.O;
        Pair<Integer, Integer> step = tictactoeBot.suggestMove(field, nextSymbol);
        field.set(step.getX(), step.getY(), nextSymbol);
        updateStatus();
    }
}
