package ru.spbau.annikura.tictactoe.controllers;

public abstract class AbstractGameController implements GameController {
    protected GameStatus status = GameStatus.X_TURN;
    protected GameField field;
    protected int amountToWin;

    protected AbstractGameController(int size, int cnt) {
        field = new GameField(size);
        amountToWin = cnt;
    }

    @Override
    public GameField getField() {
        return field;
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public GameField makeMove(int i, int j) {
        if (status.isFinished() || !setCell(i, j)) {
            return field;
        }
        updateStatus();
        finishMove();
        return field;
    }

    protected boolean setCell(int i, int j) {
        if (field.get(i, j) != GameField.Cell.EMPTY) {
            return false;
        }
        GameField.Cell symbol = status.equals(GameStatus.X_TURN) ? GameField.Cell.X : GameField.Cell.O;
        field.set(i, j, symbol);
        return true;
    }

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
        assert false;
    }

    protected abstract void finishMove();
}
