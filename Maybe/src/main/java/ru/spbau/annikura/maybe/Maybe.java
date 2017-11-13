package ru.spbau.annikura.maybe;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Container storing either one value of type T, or nothing.
 */
public class Maybe<T> {
    private final T value;
    private final boolean hasValue;
    private static Maybe NOTHING = new Maybe();

    private Maybe() {
        this.value = null;
        hasValue = false;
    }

    private Maybe(@Nullable final T value) {
        this.value = value;
        hasValue = true;
    }

    /**
     * Maybe factory function.
     * Creates Maybe containing value of the type T.
     * @param t a value to be contained by new Maybe.
     * @return Maybe containing given value.
     */
    public static <T> Maybe<T> just(@Nullable final T t) {
        return new Maybe<>(t);
    }

    /**
     * Maybe factory finction.
     * Creates an empty Maybe.
     * @return empty Maybe.
     */
    public static <T> Maybe<T> nothing() {
        return NOTHING;
    }

    /**
     * Maybe value getter.
     * @return a stored value, if existed.
     */
    public T get() throws MaybeInvalidGetException {
        if (hasValue)
            return value;
        throw new MaybeInvalidGetException("Invalid get. Maybe does not contain a value");
    }

    /**
     * Checks the state of Maybe
     * @return true if Maybe contains value, false if not.
     */
    public boolean isPresent() {
        return hasValue;
    }


    /**
     * Applies a given function to the Maybe content, if it exists and creates a new Maybe with its result.
     * @param mapper a function to be applied to the old Maybe content.
     * @param <U> a type of the new Maybe content
     * @return a Maybe containing a result of the function applied to the current Maybe content if it existed.
     * If not, returns new empty Maybe.
     */
    public <U> Maybe<U> map(@NotNull Function<? super T, U> mapper) {
        if (!isPresent()) {
            return Maybe.nothing();
        }
        return Maybe.just(mapper.apply(value));
    }
}
