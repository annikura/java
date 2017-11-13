package ru.spbau.annikura.maybe;


import org.jetbrains.annotations.NotNull;

/**
 * Maybe exception class. Will be thrown in case of attempt to access data in Maybe storing nothing.
 */
public class MaybeInvalidGetException extends Exception {
    /**
     * Constructs an exception with message.
     * @param message description of the occurred problem that caused the exception.
     */
    public MaybeInvalidGetException(@NotNull String message) {
        super(message);
    }
}
