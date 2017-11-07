package ru.spbau.annikura.function;

import org.jetbrains.annotations.NotNull;

public interface Function2<A, B, R> {
    public R apply(A arg1, B arg2);
    public default <U> Function2<A, B, U> compose(@NotNull Function1<? super R, ? extends U> composedFunct) {
        return (arg1, arg2) -> composedFunct.apply(apply(arg1, arg2));
    }
    public default <T extends A> Function1<B, R> bind1(T arg) {
        return arg1 -> apply(arg, arg1);
    }
    public default <T extends B> Function1<A, R> bind2(T arg) {
        return arg1 -> apply(arg1, arg);
    }
    public default Function1<B, Function1<A, R>> curry() {
        return this::bind2;
    }
}
