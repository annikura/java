package ru.spbau.annikura.injection;

import org.jetbrains.annotations.NotNull;

/**
 * Exception to be thrown when the cycle dependency was found while initializing root class with Injection.initialize
 * */
public class InjectionCycleException extends Exception {
    /**
     * Creates an exception with a message in it.
     * @param message message to be shown
     */
    public InjectionCycleException(@NotNull String message) {
        super(message);
    }
}
