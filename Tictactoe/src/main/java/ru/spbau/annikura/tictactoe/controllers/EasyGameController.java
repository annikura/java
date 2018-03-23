package ru.spbau.annikura.tictactoe.controllers;

public class EasyGameController extends AbstractBotGameController {
    public EasyGameController(int size, int cnt) {
        super(size, cnt);
        tictactoeBot = new TictactoeBot(2, amountToWin);
    }

    @Override
    public GameController newGame() {
        return new EasyGameController(field.getSize(), amountToWin);
    }
}
