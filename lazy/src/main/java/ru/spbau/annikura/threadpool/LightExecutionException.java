package ru.spbau.annikura.threadpool;

/**
 * Exception class that is thrown when an error occurres during LightFuture tsk execution.
 */
public class LightExecutionException extends Exception {
    /**
     * Standard exception with the message constructor.
     * @param message message that will be attached to the exception.
     */
    public LightExecutionException(String message) {
        super(message);
    }
}
