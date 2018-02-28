import java.util.Collections;
import java.util.function.Supplier;

/**
 * Thread safe implementation of Lazy
 */
class ThreadSafeLazy<T> extends LazyAbstract<T> {
    ThreadSafeLazy(Supplier<T> supplier) {
        super(supplier);
    }

    /**
     * Captures 'this' to evaluate the resource and evaluates it.
     * @return evaluated resource
     */
    private synchronized T safeGet() {
            if (object.isEmpty()) {
                object = Collections.singletonList(supplier.get());
            }
            return object.get(0);
    }

    /**
     * Provides fast thread-safe access to the evaluated resource.
     * If it wasn't calculated yet, runs thread-safe computation.
     * @return evaluated resource
     */
    @Override
    public T get() {
        if (!object.isEmpty()) {
            return object.get(0);
        } else {
            return safeGet();
        }
    }
}
