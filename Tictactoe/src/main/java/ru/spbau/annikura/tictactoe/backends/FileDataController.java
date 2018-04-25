package ru.spbau.annikura.tictactoe.backends;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.tictactoe.controllers.GameController;

import java.io.*;

/**
 * Class controlling reading a writing data from disk
 */
public class FileDataController {
    private static String STATS_FILE = "stats";

    /**
     * Reads stats file from disk. If the file storing it is damaged
     * or doesn't exist, will return empty stats object.
     * @return received stats class
     */
    @NotNull
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

    /**
     * Writes stats object on disk.
     * @param stats stats to be written
     */
    private static void writeStats(@NotNull Stats stats) {
        File file = new File(STATS_FILE);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(stats);
        } catch (IOException e) {
            // Unable to write stats on disk
        }
    }

    /**
     * Updates stats object stored on disk according to the new known result.
     * @param result new game result that should be recorded
     */
    public static void updateStats(@NotNull GameController.GameStatus result) {
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

