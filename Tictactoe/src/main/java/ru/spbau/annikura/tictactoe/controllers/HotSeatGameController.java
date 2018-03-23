package ru.spbau.annikura.tictactoe.controllers;

public class HotSeatGameController extends AbstractGameController {
    public HotSeatGameController(int size, int cnt) {
        super(size, cnt);
    }

    @Override
    protected void finishMove() { }

    @Override
    public GameController newGame() {
        return new HotSeatGameController(field.getSize(), amountToWin);
    }
}
