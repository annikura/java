import com.sun.istack.internal.NotNull;

import java.util.function.Supplier;

/**
 * Factory functions provider for Lazy interface.
 */
public class LazyFactory {
    /**
     * Creates thread-unsafe implementation of Lazy.
     * @param supplier supplier of the resource
     * @param <T> resource type
     * @return thread-unsafe Lazy instance.
     */
    static public <T> Lazy<T> createSimpleLazy(@NotNull Supplier<T> supplier) {
        return new SimpleLazy<>(supplier);
    }

    /**
     * Creates thread-safe implementation of Lazy.
     * @param supplier supplier of the resource
     * @param <T> resource type
     * @return thread-safe Lazy instance.
     */
    static public <T> Lazy<T> createThreadSafeLazy(@NotNull Supplier<T> supplier) {
        return new ThreadSafeLazy<>(supplier);
    }
}
