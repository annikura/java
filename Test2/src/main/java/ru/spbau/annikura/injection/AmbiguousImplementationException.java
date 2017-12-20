package ru.spbau.annikura.injection;

import org.jetbrains.annotations.NotNull;

/**
 * Exception to be thrown if some of the required instance can be created ambiguously while using Injector.initialize
 */
public class AmbiguousImplementationException extends Exception {
    /**
     * Creates an exception with a message in it.
     * @param message message to be shown
     */
    public AmbiguousImplementationException(@NotNull String message) {
        super(message);
    }
}
