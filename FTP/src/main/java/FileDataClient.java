import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;

public class FileDataClient {
    private final SocketIO io;

    public FileDataClient(SocketIO socketIO) {
        io = socketIO;
    }

    @NotNull
    public static FileDataClient createClient(@NotNull String homeName, int portNumber) throws IOException {
        Socket socket = new Socket(homeName, portNumber);
        return new FileDataClient(new SocketIOImpl(
                socket.getInputStream(),
                socket.getOutputStream()));
    }

    @NotNull
    public ServerFile[] getFiles(@NotNull String path) {
        io.writeInt(1);
        io.writeInt(path.length());
        io.writeString(path);

        int size = io.readInt();
        ServerFile[] files = new ServerFile[size];
        for (int i = 0; i < size; i++) {
            String filename = io.readString(io.readInt());
            files[i] = new ServerFile(filename, io.readBoolean());
        }
        return files;
    }

    @NotNull
    public byte[] getFile(@NotNull String filename){
        io.writeInt(2);
        io.writeInt(filename.length());
        io.writeString(filename);

        return io.readBytes(io.readLong());
    }

    public void closeClient() {
        io.writeInt(3);
        io.close();
    }
}
