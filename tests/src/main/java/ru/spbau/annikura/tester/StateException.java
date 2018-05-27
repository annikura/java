package ru.spbau.annikura.tester;

import org.jetbrains.annotations.NotNull;

/**
 * Exception whcih is thrown if test class state is broken: if any of before/any methods caused error.
 */
public class StateException extends Exception {
    /**
     * Constructs StateException containing message and the exception which occurred while executing method.
     * @param message exception message
     * @param attached attached throwable which occurred during execution
     */
    StateException(@NotNull String message, Throwable attached) {
        super(message, attached);
    }
}
