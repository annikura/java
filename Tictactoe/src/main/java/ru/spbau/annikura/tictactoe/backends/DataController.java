package ru.spbau.annikura.tictactoe.backends;

import ru.spbau.annikura.tictactoe.controllers.GameController;

import java.io.*;

public class DataController {
    private static String STATS_FILE = "stats";

    public static Stats getStats() {
        File file = new File(STATS_FILE);
        Stats stats;
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            stats = (Stats) ois.readObject();
        } catch (Exception e) {
            stats = new Stats();
        }

        return stats;
    }

    private static void writeStats(Stats stats) {
        File file = new File(STATS_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stats);
        } catch (IOException e) {
            // Unable to write stats on disk
        }
    }

    public static void updateStats(GameController.GameStatus result) {
        Stats stats = getStats();
        switch (result) {
            case O_WON:
                stats.incOWon();
                break;
            case X_WON:
                stats.incXWon();
                break;
            case DRAW:
                stats.incDraw();
                break;
        }
        writeStats(stats);
    }
}

