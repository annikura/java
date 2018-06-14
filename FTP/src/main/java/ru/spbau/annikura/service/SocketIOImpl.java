package ru.spbau.annikura.service;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * SocketIO implementation which uses Data*Streams to provide communication between client and server.
 * An important fact is that this implementation flushes out stream after each write.
 */
class SocketIOImpl implements SocketIO {
    private final DataOutputStream out;
    private final DataInputStream in;

    /**
     * Creates SocketIO instance using given in and out streams as input and output correspondingly.
     * @param in input stream.
     * @param out output stream.
     */
    SocketIOImpl(@NotNull final InputStream in, @NotNull final OutputStream out) {
        this.in = new DataInputStream(new BufferedInputStream(in));
        this.out = new DataOutputStream(new BufferedOutputStream(out));
    }

    private void afterWrite() throws IOException {
        out.flush();
    }

    /**
     * Writes byte to the out stream and flushes it.
     * @param b byte to be written into the socket.
     * @throws IOException if an error occurred while writing into the stream.
     *
     * WARNING: should be flushed manually.
     */
    public void writeByte(byte b) throws IOException {
        out.write(b);
    }


    /**
     * Writes integer to the out stream and flushes it.
     * @param number integer to be written into the socket.
     * @throws IOException if an error occurred while writing into the stream.
     */
    public void writeInt(int number) throws IOException {
        out.writeInt(number);
        afterWrite();
    }

    /**
     * Writes string to the out stream and flushes it.
     * @param string string to be written into the socket.
     * @throws IOException if an error occurred while writing into the stream.
     */
    public void writeString(@NotNull String string) throws IOException {
        out.writeChars(string);
        afterWrite();
    }

    /**
     * Writes long to the out stream and flushes it.
     * @param number long to be written into the socket.
     * @throws IOException if an error occurred while writing into the stream.
     */
    public void writeLong(long number) throws IOException {
        out.writeLong(number);
        afterWrite();
    }

    /**
     * Writes boolean to the out stream and flushes it.
     * @param bool boolean to be written into the socket.
     * @throws IOException if an error occurred while writing into the stream.
     */
    public void writeBoolean(boolean bool) throws IOException {
        out.writeBoolean(bool);
        afterWrite();
    }

    /**
     * Reads byte from the stream.
     * @return byte that was read.
     * @throws IOException if an error occurred while reading from the stream.
     */
    public byte readByte() throws IOException {
        return in.readByte();
    }

    /**
     * Reads given int stream.
     * @return int that was read.
     * @throws IOException if an error occurred while reading from the stream.
     */
    public int readInt() throws IOException {
        return in.readInt();
    }

    /**
     * Reads string of the given length from the stream.
     * @param length string length.
     * @return string that was read.
     * @throws IOException if an error occurred while reading from the stream.
     */
    @NotNull
    public String readString(int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = in.readChar();
        }
        return String.copyValueOf(chars);
    }

    /**
     * Reads long from the stream.
     * @return long that was read.
     * @throws IOException if an error occurred while reading from the stream.
     */
    public long readLong() throws IOException {
        return in.readLong();
    }

    /**
     * Reads boolean from the string.
     * @return boolean that was read.
     * @throws IOException if an error occurred while reading from the stream.
     */
    public boolean readBoolean() throws IOException {
        return in.readBoolean();
    }

    /**
     * Closes communication streams.
     * @throws IOException if an error occurred while closing the streams.
     */
    public void close() throws IOException {
        in.close();
        out.close();
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }
}
