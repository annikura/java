import org.jetbrains.annotations.NotNull;

import java.io.*;

class SocketIOImpl implements SocketIO {
    private final DataOutputStream out;
    private final DataInputStream in;

    SocketIOImpl(@NotNull final InputStream in, @NotNull final OutputStream out) {
        this.in = new DataInputStream(new BufferedInputStream(in));
        this.out = new DataOutputStream(out);
    }

    private void afterWrite() throws IOException {
        out.flush();
    }

    public void writeBytes(@NotNull byte[] bytes) {
        out.write(bytes);
        afterWrite();
    }

    public void writeInt(int number) {
        out.writeInt(number);
        afterWrite();
    }

    public void writeString(@NotNull String string) {
        out.writeChars(string);
        afterWrite();
    }

    public void writeLong(long number) {
        out.writeLong(number);
        afterWrite();
    }

    public void writeBoolean(boolean bool) {
        out.writeBoolean(bool);
        afterWrite();
    }

    @NotNull
    public byte[] readBytes(long length) {
        byte[] bytes = new byte[(int) length];
        in.read(bytes);
        return bytes;
    }

    public int readInt() {
        return in.readInt();
    }

    @NotNull
    public String readString(int length) {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = in.readChar();
        }
        return String.copyValueOf(chars);
    }

    public long readLong() {
        return in.readLong();
    }

    public boolean readBoolean() {
        return in.readBoolean();
    }

    public void close() {
        in.close();
        out.close();
    }
}
