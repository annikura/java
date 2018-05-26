import org.jetbrains.annotations.NotNull;

public class StateException extends Exception {
    StateException(@NotNull String message, Throwable attached) {
        super(message, attached);
    }
}
