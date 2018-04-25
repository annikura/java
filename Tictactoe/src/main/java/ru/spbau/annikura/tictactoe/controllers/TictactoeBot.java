package ru.spbau.annikura.tictactoe.controllers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bot for playing TicTacToe
 */
public interface TictactoeBot {

    /**
     * Supposing that the next move should be done by moveWith symbol,
     * decides which cell is optimal to make a move according to the bot strategy
     * (see class description for more information).
     * @param field field to make moves on
     * @param moveWith symbol to make next move with
     * @return suggested move or null if game already finished and no possible move exists
     */
    @Nullable
    Pair<Integer, Integer> suggestMove(@NotNull GameField field,
                                       @NotNull GameField.Cell moveWith);
}
