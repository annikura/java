import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * IO interface providing socket input/output for some simple types.
 */
public interface SocketIO {
    /**
     * Writes given byte into the socket.
     * @param b byte to be written into the socket.
     * @throws IOException if an error occurs while writing.
     *
     * WARNING: should be flushed manually.
     */
    void writeByte(byte b) throws IOException;

    /**
     * Writes given integer into the socket.
     * @param number integer to be written into the socket.
     * @throws IOException if an error occurs while writing.
     */
    void writeInt(int number) throws IOException;

    /**
     * Writes given string into the socket.
     * @param string string to be written into the socket.
     * @throws IOException if an error occurs while writing.
     */
    void writeString(@NotNull String string) throws IOException;

    /**
     * Writes given long into the socket.
     * @param number long to be written into the socket
     * @throws IOException if an error occurs while writing
     */
    void writeLong(long number) throws IOException;

    /**
     * Writes given boolean into the socket.
     * @param bool boolean to be written into the socket.
     * @throws IOException if an error occurs while writing.
     */
    void writeBoolean(boolean bool) throws IOException;

    /**
     * Reads byte from the socket.
     * @return byte from the socket.
     * @throws IOException if an error occurs while writing.
     */
    byte readByte() throws IOException;

    /**
     * Reads integer from the socket.
     * @return read integer.
     * @throws IOException if an error occurs while reading.
     */
    int readInt() throws IOException;

    /**
     * Reads string of the given length from the socket.
     * @param length string length.
     * @return read string.
     * @throws IOException if an error occurs while reading.
     */
    @NotNull
    String readString(int length) throws IOException;

    /**
     * Reads long from the socket.
     * @return read long.
     * @throws IOException if an error occurs while reading.
     */
    long readLong() throws IOException;

    /**
     * Reads boolean from the socket.
     * @return read boolean.
     * @throws IOException if an error occurs while reading.
     */
    boolean readBoolean() throws IOException;

    /**
     * Closes socket connection.
     * @throws IOException if an error occurred while closing the io stream.
     */
    void close() throws IOException;

    /**
     * Flushes output stream.
     * @throws IOException if an error occurred while flushing.
     */
    void flush() throws IOException;
}
