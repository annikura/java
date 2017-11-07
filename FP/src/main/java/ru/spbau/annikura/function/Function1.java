package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

public interface Function1<T, R> {
    public R apply(T arg);
    public default <U> Function1<T, U> compose(@NotNull final Function1<? super R, ? extends U> composedFunct) {
        return arg -> composedFunct.apply(this.apply(arg));
    }
}
