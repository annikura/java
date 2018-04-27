import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * A client which can request directory content and download files from server.
 */
public class FileDataClient {
    private final SocketIO io;

    /**
     * Creates file data client which will use a given SocketIO to communicate with the server.
     * @param socketIO server io class
     */
    public FileDataClient(SocketIO socketIO) {
        io = socketIO;
        Logger.getAnonymousLogger().info("FileDataClient was created");
    }

    /**
     * FileDataClient factory method
     * @param homeName name of the server to connect to.
     * @param portNumber server port to connect to.
     * @return FileDataClient instance that will connect to the given host on the given port.
     * @throws IOException if an error occurs while creating the connection socket.
     */
    @NotNull
    public static FileDataClient createClient(@NotNull String homeName, int portNumber) throws IOException {
        Socket socket = new Socket(homeName, portNumber);
        Logger.getAnonymousLogger().info("Successfully ran client socket.");
        return new FileDataClient(new SocketIOImpl(
                socket.getInputStream(),
                socket.getOutputStream()));
    }

    /**
     * Sends get directory content request to the server and gets the response.
     *
     * Request format: (1 :Int) [(path length :Int) (path :String)]
     * Response expected format: (number of files :Int) [(file name length :Int) (file name :String) (is directory :boolean)]*
     *
     * @param path directory path which content is being requested
     * @return response from the server.
     * @throws IOException can be thrown if an IO error occurs while reading/writing from/to the client-server stream.
     */
    @NotNull
    public ServerFile[] getFiles(@NotNull String path) throws IOException {
        Logger.getAnonymousLogger().info("Sending getFiles request");

        io.writeInt(1);
        io.writeInt(path.length());
        io.writeString(path);

        Logger.getAnonymousLogger().info("GetFiles request has been sent");
        Logger.getAnonymousLogger().info("Reading reply size");
        int size = io.readInt();
        Logger.getAnonymousLogger().info("Reply size is " + size);

        ServerFile[] files = new ServerFile[size];
        for (int i = 0; i < size; i++) {
            Logger.getAnonymousLogger().info("Reading file...");

            String filename = io.readString(io.readInt());
            files[i] = new ServerFile(filename, io.readBoolean());

            Logger.getAnonymousLogger().info("New file was read: " + filename + ". Is it a directory? " + files[i].isDirectory());
        }
        return files;
    }

    /**
     * Sends file download request to the server.
     *
     * Request format (2 :Int) [(path length :Int) (path :String)]
     * Response format expected: (file size :Int) (content :byte[])
     *
     * @param filename path to the file that will be downloaded.
     * @return response from the server.
     * @throws IOException can be thrown if an IO error occurs while reading/writing from/to the client-server stream.
     */
    @NotNull
    public byte[] getFile(@NotNull String filename) throws IOException {
        io.writeInt(2);
        io.writeInt(filename.length());
        io.writeString(filename);

        return io.readBytes(io.readLong());
    }

    /**
     * Sends termination request to the server and closes the IO stream.
     *
     * Request format: (3 :Int)
     * @throws IOException can be thrown if an IO error occurs while reading/writing from/to the client-server stream.
     */
    public void closeClient() throws IOException {
        io.writeInt(3);
        io.close();
    }
}
