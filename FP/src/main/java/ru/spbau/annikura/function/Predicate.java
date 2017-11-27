package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

/**
 * An interface for a predicate: function that takes one argument and returns boolean.
 * @param <T> type of the predicate argument.
 */
public interface Predicate<T> extends Function1<T, Boolean> {
    /**
     * A predicate that always returns true.
     */
    Predicate ALWAYS_TRUE = arg -> true;

    /**
     * Always true predicate getter.
     * @param <T> type of the predicate parameter.
     * @return a predicate that always returns true.
     */
    @SuppressWarnings("unchecked")
    static <T> Predicate<T> alwaysTrue() {
        return ALWAYS_TRUE;
    }

    /**
     * A predicate that always returns false.
     */
    Predicate ALWAYS_FALSE = arg -> false;


    /**
     * Always false predicate getter.
     * @param <T> type of the predicate parameter.
     * @return a predicate that always returns false.
     */
    @SuppressWarnings("unchecked")
    static <T> Predicate<T> alwaysFalse() {
        return ALWAYS_FALSE;
    }
    /**
     * 'Or' operator for predicates. Given a second predicate, lazily takes 'or' of a stored function and other one.
     * @param other a second predicate to take 'or'.
     * @return false if none of predicates is true. False otherwise.
     */
    public default Predicate<T> or(@NotNull final Predicate<T> other) {
        return arg -> apply(arg) || other.apply(arg);
    }

    /**
     * 'And' operator for predicates. Given a second predicate, lazily takes 'and' of a stored function and other one.
     * @param other a second predicate to take 'and'.
     * @return true if none of predicates is false. True otherwise.
     */
    public default Predicate<T> and(@NotNull final Predicate<T> other) {
        return arg -> apply(arg) && other.apply(arg);
    }

    /**
     * Predicate negation.
     * @return a result opposite to the one that was returned py the predicate.
     */
    public default Predicate<T> not() {
        return arg -> !apply(arg);
    }
}
