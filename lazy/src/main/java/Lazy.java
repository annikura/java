/**
 * Interface for lazy computations.
 * @param <T> stored or evaluated resource type.
 */
public interface Lazy<T> {
    /**
     * Being called for the first time, starts computation of the stored resource.
     * When the computation is finished, saves value into the inner storage and never computes it again.
     *
     * Note: If this method is never called, nothing is done.
     * @return calculated value
     */
    T get();
}
