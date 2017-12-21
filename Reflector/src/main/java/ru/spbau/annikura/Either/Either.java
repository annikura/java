package ru.spbau.annikura.Either;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Either<L, R> {
    private L left = null;
    private R right = null;
    private final boolean isLeft;

    private Either(@NotNull L l, boolean isLeft) {
        left = l;
        this.isLeft = true;
    }

    private Either(@NotNull R r) {
        right = r;
        isLeft = false;
    }

    @NotNull
    public static <L, R> Either<L, R> Left(@NotNull L l) {
        return new Either<L, R>(l, true);
    }

    @NotNull
    public static <L, R> Either<L, R> Right(@NotNull R r) {
        return new Either<L, R>(r);
    }

    public boolean isLeft() {
        return isLeft;
    }

    public boolean isRight() {
        return !isLeft;
    }

    @Nullable
    public L left() {
        return left;
    }

    @Nullable
    public R right() {
        return right;
    }


}
