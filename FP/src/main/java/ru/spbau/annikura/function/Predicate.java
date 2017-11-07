package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

public interface Predicate<T> extends Function1<T, Boolean> {
    public static final Predicate<?> ALWAYS_TRUE = arg -> true;
    public static final Predicate<?> ALWAYS_FALSE = arg -> false;

    public default Predicate<T> or(@NotNull final Predicate<T> other) {
        return arg -> apply(arg) || other.apply(arg);
    }

    public default Predicate<T> and(@NotNull final Predicate<T> other) {
        return arg -> apply(arg) && other.apply(arg);
    }

    public default Predicate<T> not() {
        return arg -> !apply(arg);
    }
}
