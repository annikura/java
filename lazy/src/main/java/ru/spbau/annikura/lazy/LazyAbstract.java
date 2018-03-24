package ru.spbau.annikura.lazy;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Stored data abstraction.
 */
abstract class LazyAbstract<T> implements Lazy<T> {
    Supplier<T> supplier;
    volatile List<T> object = Collections.emptyList();

    /**
     * Saves supplier to call it when SimpleLazy.get is called.
     * @param supplier stored supplier
     */
    LazyAbstract(@NotNull final Supplier<T> supplier) {
        this.supplier = supplier;
    }
}
