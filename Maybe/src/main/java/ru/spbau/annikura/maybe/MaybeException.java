package ru.spbau.annikura.maybe;

import com.sun.istack.internal.NotNull;
/**
 * Maybe exception class. Will be thrown in case of attempting to access data in Maybe storing nothing.
 */
public class MaybeException extends Exception {
    /**
     * Constructs an exception with message
     * @param message
     */
    public  MaybeException(@NotNull String message) {
        super(message);
    }
}
