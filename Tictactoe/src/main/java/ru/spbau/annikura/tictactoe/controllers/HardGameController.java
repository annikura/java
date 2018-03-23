package ru.spbau.annikura.tictactoe.controllers;

public class HardGameController extends AbstractBotGameController {
    public HardGameController(int size, int cnt) {
        super(size, cnt);
        tictactoeBot = new TictactoeBot(4, amountToWin);
    }

    @Override
    public GameController newGame() {
        return new HardGameController(field.getSize(), amountToWin);
    }
}
