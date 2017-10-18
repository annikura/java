package ru.spbau.annikura.maybe;

import java.util.function.Function;

/**
 * Container storing either one value of type T, or nothing.
 * @param <T>
 */
public class Maybe<T> {
    private final T value;
    private final boolean hasValue;

    private Maybe() {
        this.value = null;
        hasValue = false;
    }

    private Maybe(T value) {
        this.value = value;
        hasValue = true;
    }

    /**
     * Maybe factory function.
     * Creates Maybe containing value of the type T.
     * @param t
     * @param <T>
     * @return Maybe containing given value.
     */
    public static <T> Maybe<T> just(T t) {
        return new Maybe<>(t);
    }

    /**
     * Maybe factory finction.
     * Creates an empty Maybe.
     * @param <T>
     * @return empty Maybe.
     */
    public static <T> Maybe<T> nothing() {
        return new Maybe<>();
    }

    /**
     * Maybe value getter.
     * @return a stored value, if existed.
     */
    public T get() throws MaybeException{
        if (hasValue)
            return value;
        throw new MaybeException("Invalid get. Maybe does not contain a value");
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
     * @param mapper
     * @param <U>
     * @return a Maybe containing a result of the function applied to the current Maybe content if it existed.
     * If not, returns new empty Maybe.
     */
    public <U> Maybe<U> map(Function<? super T, U> mapper) throws MaybeException {
        if (!isPresent()) {
            return Maybe.nothing();
        }
        return Maybe.just(mapper.apply(get()));
    }
}
