package ru.spbau.annikura.maybe;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SquareNumbers {
    public static void main(String[] args) {
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
        } catch (MaybeException e) {
            System.out.println("Failed =(, Maybe exception was thrown.");
            return;
        }
        System.out.println("Success.");
    }
}
