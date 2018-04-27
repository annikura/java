import org.jetbrains.annotations.NotNull;

import java.io.*;

class SocketIOImpl implements SocketIO {
    private final DataOutputStream out;
    private final DataInputStream in;

    /**
     * Creates So
     * @param in
     * @param out
     */
    SocketIOImpl(@NotNull final InputStream in, @NotNull final OutputStream out) {
        this.in = new DataInputStream(new BufferedInputStream(in));
        this.out = new DataOutputStream(out);
    }

    private void afterWrite() throws IOException {
        out.flush();
    }

    public void writeBytes(@NotNull byte[] bytes) throws IOException {
        out.write(bytes);
        afterWrite();
    }

    public void writeInt(int number) throws IOException {
        out.writeInt(number);
        afterWrite();
    }

    public void writeString(@NotNull String string) throws IOException {
        out.writeChars(string);
        afterWrite();
    }

    public void writeLong(long number) throws IOException {
        out.writeLong(number);
        afterWrite();
    }

    public void writeBoolean(boolean bool) throws IOException {
        out.writeBoolean(bool);
        afterWrite();
    }

    @NotNull
    public byte[] readBytes(long length) throws IOException {
        byte[] bytes = new byte[(int) length];
        in.read(bytes);
        return bytes;
    }

    public int readInt() throws IOException {
        return in.readInt();
    }

    @NotNull
    public String readString(int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = in.readChar();
        }
        return String.copyValueOf(chars);
    }

    public long readLong() throws IOException {
        return in.readLong();
    }

    public boolean readBoolean() throws IOException {
        return in.readBoolean();
    }

    public void close() throws IOException {
        in.close();
        out.close();
    }
}
