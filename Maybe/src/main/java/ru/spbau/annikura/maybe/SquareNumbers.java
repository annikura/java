package ru.spbau.annikura.maybe;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class wrapping the main method reading the numbers from the first file
 * and writing their squares into the second file.
 */
public class SquareNumbers {
    /**
     * Method that will read the one number per line from the first file
     * and write their squares into the second one. If line contains an invalid number,
     * "null" will be printed on the corresponding line of the output file.
     * @param args should containg exactly two strings: name of the file to read from
     *             and the name of the file to write to.
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 2) {
            System.out.println("Arguments are invalid. Input filename and output filename are expected.");
            return;
        }

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);

        if (!inputFile.exists()) {
            System.out.println("Input file does not exist");
            return;
        }

        if (!outputFile.getParentFile().exists() &&
                !outputFile.getParentFile().mkdirs()) {
            System.out.println("An error occurred. Cannot create a destination folder.");
            return;
        }

        List<Maybe<Double>> maybeList = new ArrayList<>();
        try (Scanner scanner = new Scanner(inputFile)) {
            while (scanner.hasNext()) {
                if (scanner.hasNextDouble()) {
                    maybeList.add(Maybe.just(scanner.nextDouble()));
                } else {
                    maybeList.add(Maybe.nothing());
                    scanner.next();
                }
            }
        } catch (IOException ioEx) {
            System.out.println(ioEx.getMessage());
            return;
        }

        for (Maybe<Double> maybe : maybeList) {
            maybe.map(x -> x * x);
        }

        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(outputFile))) {
            for (Maybe<Double> maybe : maybeList) {
                if (maybe.isPresent()) {
                    printWriter.println(maybe.get());
                } else {
                    printWriter.println("null");
                }
            }
            printWriter.flush();
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            return;
        } catch (MaybeInvalidGetException e) {
            System.out.println("Failed =(, Maybe exception was thrown.");
            return;
        }
        System.out.println("Success.");
    }
}
