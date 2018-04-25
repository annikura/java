package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

/**
 * class for storing pairs of some objects
 * @param <X> first object type
 * @param <Y> second object type
 */
class Pair<X, Y> {
    private X x;
    private Y y;

    /**
     * Creates pair storing given objects
     * @param x first stored object
     * @param y second stored object
     */
    public Pair(@NotNull X x, @NotNull Y y) {
        this.x = x;
        this.y = y;
    }

    /**
     * First stored object getter
     * @return first stored object.
     */
    @NotNull
    public X getX() {
        return x;
    }


    /**
     * Second stored object getter
     * @return second stored object.
     */
    @NotNull
    public Y getY() {
        return y;
    }
}
