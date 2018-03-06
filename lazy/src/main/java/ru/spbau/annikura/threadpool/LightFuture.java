package ru.spbau.annikura.threadpool;

import com.sun.istack.internal.NotNull;

import java.util.function.Function;

/**
 * Async task holder.
 * @param <T> return type of the task.
 */
public interface LightFuture<T> {
    /**
     * Progress indicating method
     * @return true if task was completed and the result is known, false if it is still in progress.
     */
    boolean isReady();

    /**
     * Waits until task is completed and returns results as soon as it is known.
     * @return result returned by supplier
     * @throws LightExecutionException if the exception occurred while supplier was executing.
     */
    T get() throws LightExecutionException;

    /**
     * Creates new LightFuture out of function that will receive result of the current LightFuture as the parameter.
     * Will be put into the tasks pool as soon as current task is completed.
     * @param function will be executed in the new LightFuture with given result of current LightFuture as the parameter.
     * @param <Y> new LightFuture return type.
     * @return new task that will be immediately put into the execution queue as soon as the result of the current LightFuture is known.
     */
    <Y> LightFuture<Y> thenApply(final @NotNull Function<? super T, Y> function);
}
