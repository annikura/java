package ru.spbau.annikura.tictactoe.backends;

import java.io.Serializable;

/**
 * Game results statistics storing class
 */
public class Stats implements Serializable {
    private int xWon;
    private int yWon;
    private int draw;

    /**
     * X wins counter increment
     */
    void incXWon() {
        xWon++;
    }


    /**
     * O wins counter increment
     */
    void incOWon() {
        yWon++;
    }


    /**
     * Draws counter increment
     */
    void incDraw() {
        draw++;
    }

    /**
     * X wins getter
     */
    public int getXWon() {
        return xWon;
    }

    /**
     * O wins getter
     */
    public int getOWon() {
        return yWon;
    }

    /**
     * Draws getter
     */
    public int getDraw() {
        return draw;
    }

    /**
     * Number of games played in total getter.
     * @return sum of X wins, O wins and draws.
     */
    public int getTotal() {
        return xWon + yWon + draw;
    }
}
