package ru.spbau.annikura.tictactoe.backends;

import java.io.Serializable;

public class Stats implements Serializable {
    private int xWon;
    private int yWon;
    private int draw;

    void incXWon() {
        xWon++;
    }

    void incOWon() {
        yWon++;
    }

    void incDraw() {
        draw++;
    }

    public int getXWon() {
        return xWon;
    }

    public int getOWon() {
        return yWon;
    }

    public int getDraw() {
        return draw;
    }

    public int getTotal() {
        return xWon + yWon + draw;
    }
}
