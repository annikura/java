package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * Game controller interface. Provides access to the game board and game status.
 */
public interface GameController {
    /**
     * This enum describes the state of the game.
     */
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

    /**
     * Makes move on position (i, j) if possible (if chosen cell is empty).
     * @param x move column
     * @param y move row
     * @return new field
     */
    @NotNull
    GameField makeMove(int x, int y);

    /**
     * Game status getter
     * @return game status
     */
    @NotNull
    GameStatus getStatus();

    /**
     * New game creator
     * @return new empty Game controller with the same parameters
     */
    GameController newGame();

    /**
     * Game board getter
     * @return game board
     */
    @NotNull
    GameField getField();
}
