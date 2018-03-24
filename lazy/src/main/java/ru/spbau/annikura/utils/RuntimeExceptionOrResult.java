package ru.spbau.annikura.utils;

/**
 * Resource or runtime exception holder.
 * @param <T> type of the resource
 */
public class RuntimeExceptionOrResult<T> {
    private final RuntimeException exception;
    private final T resource;

    /**
     * Constructs holder with exception
     * @param exception exception that will be held
     */
    public RuntimeExceptionOrResult(RuntimeException exception) {
        this.exception = exception;
        resource = null;
    }

    /**
     * Constructs holder with resource
     * @param resource resource that will be held
     */
    public RuntimeExceptionOrResult(T resource) {
        this.resource = resource;
        exception = null;
    }

    public boolean isException() {
        return exception != null;
    }

    /**
     * Exception getter
     * @return exception if present, null otherwise
     */
    public RuntimeException getException() {
        return exception;
    }

    /**
     * Throws stored exception if presented, gives stored resource otherwise
     * @return resource
     */
    public T getResource() {
        if (isException()) {
            throw exception;
        }
        return resource;
    }
}
