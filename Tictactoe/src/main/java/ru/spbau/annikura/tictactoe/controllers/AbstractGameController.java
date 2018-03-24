package ru.spbau.annikura.tictactoe.controllers;

/**
 * Tictactoe controller abstraction.
 * Provides set of simple operations which are common for any controller like:
 * knows how to make a move,
 * updates the state after each move.
 */
public abstract class AbstractGameController implements GameController {
    protected GameStatus status = GameStatus.X_TURN;
    protected GameField field;
    protected int amountToWin;

    /**
     * Abstract game controller constructor.
     * @param size size of the game field
     * @param cnt number of cells in a row filled with one cells type required to win
     */
    protected AbstractGameController(int size, int cnt) {
        field = new GameField(size);
        amountToWin = cnt;
    }

    /**
     * Game field getter
     * @return game field
     */
    @Override
    public GameField getField() {
        return field;
    }

    /**
     * Game status getter
     * @return game status
     */
    @Override
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Makes move on position (i, j) if possible.
     * @param i move column
     * @param j move row
     * @return new field
     */
    @Override
    public GameField makeMove(int i, int j) {
        if (status.isFinished() || !setCell(i, j)) {
            return field;
        }
        updateStatus();
        finishMove();
        return field;
    }

    /**
     * Makes move if possible (if the cell is empty)
     * @param i move column
     * @param j move row
     * @return true if the move was made successfully, false otherwise
     */
    protected boolean setCell(int i, int j) {
        if (field.get(i, j) != GameField.Cell.EMPTY) {
            return false;
        }
        GameField.Cell symbol = status.equals(GameStatus.X_TURN) ? GameField.Cell.X : GameField.Cell.O;
        field.set(i, j, symbol);
        return true;
    }

    /**
     * Updates status to relevant after the move was made.
     */
    protected void updateStatus() {
        if (field.getMaxRow(GameField.Cell.X) >= amountToWin) {
            status = GameStatus.X_WON;
            return;
        }
        if (field.getMaxRow(GameField.Cell.O) >= amountToWin) {
            status = GameStatus.O_WON;
            return;
        }
        if (field.getEmptyCellsCount() == 0) {
            status = GameStatus.DRAW;
            return;
        }
        if (status.equals(GameStatus.X_TURN)) {
            status = GameStatus.O_TURN;
            return;
        }
        if (status.equals(GameStatus.O_TURN)) {
            status = GameStatus.X_TURN;
            return;
        }
    }

    protected abstract void finishMove();
}
