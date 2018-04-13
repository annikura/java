import org.jetbrains.annotations.NotNull;

public interface SocketIO {
    void writeBytes(@NotNull byte[] bytes);
    void writeInt(int number);
    void writeString(@NotNull String string);
    void writeLong(long number);
    void writeBoolean(boolean bool);

    @NotNull
    byte[] readBytes(long length);
    int readInt();
    @NotNull
    String readString(int length);
    long readLong();
    boolean readBoolean();

    void close();
}
