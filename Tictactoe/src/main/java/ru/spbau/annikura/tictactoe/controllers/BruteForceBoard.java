package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Board that copies an original board to play with the copy in the brute force. Allows to roll back the last action.
 */
class BruteForceBoard extends GameField {
    private final ArrayList<Pair<Pair<Integer, Integer>, Cell>> changes = new ArrayList<>();

    /**
     * Board constructor, just coping the original board
     * @param field board to be copied
     */
    public BruteForceBoard(@NotNull GameField field) {
        super(field.getSize());
        for (int i = 0; i < field.getSize(); i++)
            for (int j = 0; j < field.getSize(); j++)
                this.field[i][j] = field.get(i, j);
    }

    /**
     * Sets new value and saves the change
     * @param x column index
     * @param y row index
     * @param symbol new symbol to be set
     */
    @Override
    public void set(int x, int y, @NotNull Cell symbol) {
        changes.add(new Pair<>(new Pair<>(x, y), get(x, y)));
        super.set(x, y, symbol);
    }

    /**
     * Rolls back the last change.
     */
    public void undo() {
        if (changes.size() == 0)
            return;
        Pair<Pair<Integer, Integer>, Cell> lastChange = changes.get(changes.size() - 1);
        changes.remove(changes.size() - 1);

        super.set(lastChange.getX().getX(), lastChange.getX().getY(), lastChange.getY());
    }
}
