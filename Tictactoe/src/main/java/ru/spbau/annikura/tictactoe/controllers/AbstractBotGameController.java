package ru.spbau.annikura.tictactoe.controllers;

public abstract class AbstractBotGameController extends AbstractGameController {
    TictactoeBot tictactoeBot;

    protected AbstractBotGameController(int size, int cnt) {
        super(size, cnt);
    }

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
