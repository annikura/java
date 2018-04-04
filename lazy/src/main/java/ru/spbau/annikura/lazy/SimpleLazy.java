package ru.spbau.annikura.lazy;

import java.util.Collections;
import java.util.function.Supplier;

/**
 * Thread unsafe, but fast implementation of Lazy
 * @param <T> resource type
 */
class SimpleLazy<T> extends LazyAbstract<T> {
    SimpleLazy(Supplier<T> supplier) {
        super(supplier);
    }

    /**
     * Fast, but thread unsafe implementation of Lazy.get
     * @see Lazy#get()
     */
    @Override
    public T get() {
        if (object.isEmpty()) {
            object = Collections.singletonList(supplier.get());
        }
        return object.get(0);
    }
}
