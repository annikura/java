package ru.spbau.annikura.tictactoe.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static java.lang.Math.abs;

public class TictactoeBot {
    private final int calcluationDepth;
    private final int amountToWin;

    public TictactoeBot(int depth, int amountToWin) {
        calcluationDepth = depth;
        this.amountToWin = amountToWin;
    }

    public Pair<Integer, Integer> suggestMove(GameField field,
                                              GameField.Cell moveWith) {
        MockBoard board = new MockBoard(field);
        if (
                board.getMaxRow(GameField.Cell.X) >= amountToWin ||
                board.getMaxRow(GameField.Cell.O) >= amountToWin) {
            return null;
        }
        HashMap<Pair<Integer, Integer>, Pair<Integer, Integer>> variants =
                calculateResultsOfSteps(calcluationDepth, board, moveWith, 1024);
        double biggestWinChance = 0;
        ArrayList<Pair<Integer, Integer>> bestSteps = new ArrayList<>();
        for (Pair<Integer, Integer> step : variants.keySet()) {
            Pair<Integer, Integer> results = variants.get(step);
            double winChance = (double) (results.getX() + 1) / (double) (results.getY() + 1);
            if (biggestWinChance < winChance) {
                biggestWinChance = winChance;
                bestSteps = new ArrayList<>();
            }
            if (biggestWinChance == winChance) {
                bestSteps.add(step);
            }
        }
        return bestSteps.get(abs(new Random().nextInt()) % bestSteps.size());
    }

    private HashMap<Pair<Integer,Integer>,Pair<Integer,Integer>> calculateResultsOfSteps(int calcluationDepth,
                                                                                         MockBoard board,
                                                                                         GameField.Cell symbol,
                                                                                         int weight) {
        HashMap<Pair<Integer,Integer>, Pair<Integer,Integer>>  map = new HashMap<>();
        boolean oWon = board.getMaxRow(GameField.Cell.O) >= amountToWin;
        boolean xWon = board.getMaxRow(GameField.Cell.X) >= amountToWin;
        if (oWon || xWon) {
            int wins =
                    (oWon && symbol.equals(GameField.Cell.O)) ||
                    (xWon && symbol.equals(GameField.Cell.X)) ? 1 : 0;
            map.put(new Pair<>(-1, -1), new Pair<>(wins * weight, (1 - wins) * weight));
            return map;
        }
        if (calcluationDepth == 0) {
            map.put(new Pair<>(-1, -1), new Pair<>(0, 0));
            return map;
        }

        GameField.Cell nextSymbol =
                symbol.equals(GameField.Cell.X) ?
                        GameField.Cell.O :
                        GameField.Cell.X;
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++) {
                if (board.get(i, j).equals(GameField.Cell.EMPTY)) {
                    board.set(i, j, symbol);
                    HashMap<Pair<Integer,Integer>,Pair<Integer,Integer>> results =
                            calculateResultsOfSteps(
                                    calcluationDepth - 1, board, nextSymbol, weight / 4);
                    int wins = 0;
                    int losses = 0;
                    for (Pair<Integer, Integer> result : results.keySet()) {
                        Pair<Integer, Integer> winRate = results.get(result);

                        wins += winRate.getY();
                        losses += winRate.getX();
                    }
                    map.put(new Pair<>(i, j), new Pair<>(wins , losses));
                    board.undo();
                }
            }
        return map;
    }
}

