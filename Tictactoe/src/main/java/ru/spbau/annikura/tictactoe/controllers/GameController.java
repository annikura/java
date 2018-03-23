package ru.spbau.annikura.tictactoe.controllers;

public interface GameController {
    enum GameStatus {
        X_WON(true),
        O_WON(true),
        DRAW(true),
        X_TURN(false),
        O_TURN(false);

        private boolean isFinished;
        GameStatus(boolean isFinished) {
            this.isFinished = isFinished;
        }

        public boolean isFinished() {
            return isFinished;
        }
    }

    GameField makeMove(int x, int y);
    GameStatus getStatus();
    GameController newGame();
    GameField getField();
}
