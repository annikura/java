package ru.spbau.annikura.utils;

public class RuntimeExceptionOr<T> {
    private final RuntimeException exception;
    private final T resource;

    public RuntimeExceptionOr(RuntimeException exception) {
        this.exception = exception;
        resource = null;
    }

    public RuntimeExceptionOr(T resource) {
        this.resource = resource;
        exception = null;
    }


    public boolean isException() {
        return exception != null;
    }

    public RuntimeException getException() {
        return exception;
    }

    public T getResource() {
        if (isException()) {
            throw exception;
        }
        return resource;
    }
}
