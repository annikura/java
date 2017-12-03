package ru.spbau.annikura.calculator;

import org.jetbrains.annotations.NotNull;

import java.util.Stack;

/**
 * An entry for the console utility program that takes an arithmetic
 * expression as an input argument, evaluates it and outputs the result into the console.
 */

public class Evaluator {
    /**
     * Expression evaluator.
     * @param args should contain a single arithmetical expression.
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 1) {
            System.out.println(String.format(
                    "Exactly one argument was expected, found: %d.", args.length));
            return;
        }
        String expression = args[0];
        expression = expression.replaceAll("\\s+", "");
        try {
            System.out.printf(new Calculator(new Stack<>()).evaluate(expression).toString());
        } catch (IllegalArgumentException exception) {
            System.out.println(String.format(
                    "An error occurred while calculating expression: %s",
                    exception.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
