package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

/**
 * Two argument function holder interface.
 * @param <A> type of the first argument.
 * @param <B> type of the second argument.
 * @param <R> return value type.
 */
public interface Function2<A, B, R> {
    /**
     * A function holder method.
     * @param arg1 first function argument.
     * @param arg2 second function argument.
     * @return the result of the function appliсation.
     */
    public R apply(/*Nullability is not known, function-defined*/ A arg1,
                   /*Nullability is not known, function-defined*/ B arg2);

    /**
     * Composition operator.  Given a function g will return a function equivalent to g(f(arg1, arg2)),
     * where f is a function previously stored in the functor.
     * @param composedFunc a function which will wrap a currently stored one.
     * @param <U> the result type of the wrapper function.
     * @return the result of the composition.
     */
    public default <U> Function2<A, B, U> compose(@NotNull final Function1<? super R, ? extends U> composedFunc) {
        return (arg1, arg2) -> composedFunc.apply(apply(arg1, arg2));
    }

    /**
     * First argument binder. Puts a given value on the first argument place.
     * @param arg a value to be binded.
     * @param <T> value type.
     * @return one-argument function, the result of the binding.
     */
    public default <T extends A> Function1<B, R> bind1(/*Nullability is not known, function-defined*/ final T arg) {
        return arg1 -> apply(arg, arg1);
    }


    /**
     * Second argument binder. Puts a given value on the second argument place.
     * @param arg a value to be binded.
     * @param <T> value type.
     * @return one-argument function, the result of the binding.
     */
    public default <T extends B> Function1<A, R> bind2(/*Nullability is not known, function-defined*/ T arg) {
        return arg1 -> apply(arg1, arg);
    }

    /**
     * Turns two-argument function into one-argument function of the first
     * argument that returns a function of the second argument.
     * @return result of currying.
     */
    public default Function1<B, Function1<A, R>> curry() {
        return this::bind2;
    }
}
