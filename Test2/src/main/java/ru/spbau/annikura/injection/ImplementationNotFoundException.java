package ru.spbau.annikura.injection;

import org.jetbrains.annotations.NotNull;

/**
 * Exception to be thrown if its impossible to create a head class instance with only used of the given set of classes
 * while using Injector.initialize
 */
public class ImplementationNotFoundException extends Exception {
    /**
     * Creates an exception with a message in it.
     * @param message message to be shown
     */
    public ImplementationNotFoundException(@NotNull String message) {
        super(message);
    }
}
