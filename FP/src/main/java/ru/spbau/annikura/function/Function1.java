package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for a Function with one argument.
 * @param <T> argument type
 * @param <R> function result type
 */
public interface Function1<T, R> {
    /**
     * Function to be stored and applied.
     * @param arg an argument for the function application.
     * @return a function result.
     */
    public R apply(/*Nullability is not known, function-defined*/ T arg);

    /**
     * Composition operator. Given a function g will return a function equivalent to g(f(arg)),
     * where f is a function previously stored in the functor.
     * @param composedFunc a function which will wrap a currently stored one.
     * @param <U> a type of a new function argument.
     * @return the result of the composition.
     */
    public default <U> Function1<T, U> compose(@NotNull final Function1<? super R, ? extends U> composedFunc) {
        return arg -> composedFunc.apply(this.apply(arg));
    }
}
