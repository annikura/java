package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

class MockBoard extends GameField {
    private final ArrayList<Pair<Pair<Integer, Integer>, Cell>> changes = new ArrayList<>();

    public MockBoard(@NotNull GameField field) {
        super(field.getSize());
        for (int i = 0; i < field.getSize(); i++)
            for (int j = 0; j < field.getSize(); j++)
                this.field[i][j] = field.get(i, j);
    }

    @Override
    public void set(int x, int y, @NotNull Cell symbol) {
        changes.add(new Pair<>(new Pair<>(x, y), get(x, y)));
        super.set(x, y, symbol);
    }

    public void undo() {
        if (changes.size() == 0)
            return;
        Pair<Pair<Integer, Integer>, Cell> lastChange = changes.get(changes.size() - 1);
        changes.remove(changes.size() - 1);

        super.set(lastChange.getX().getX(), lastChange.getX().getY(), lastChange.getY());
    }
}
