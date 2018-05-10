import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Runnable for client-server interaction by FileData protocol.
 */

class FileDataRunnable implements Runnable {
    private final SocketIO io;

    /**
     * Creates new FileDataRunnable that uses given socket for interaction with the client.
     * @param socket a socket to interact with new client.
     * @throws IOException if an error occurred while connecting to the client.
     */
    FileDataRunnable(@NotNull Socket socket) throws IOException {
        this(new SocketIOImpl(socket.getInputStream(), socket.getOutputStream()));
    }

    /**
     * Creates new FileDataRunnable that uses given io instance for interaction with the client.
     * @param io a SocketIO instance to interact with a new client.
     */
    FileDataRunnable(@NotNull SocketIO io) {
        this.io = io;
    }

    /**
     * Method implementing server-client interaction.
     *
     * Supported requests:
     *  File content request -- given a path to a file, returns its content as bytes[]. If no such a file exists, returns empty array.
     *  File listing request -- given a path to the directory, returns files located directly in the given directory as list of pairs:
     *      (name of file, whether it is a directory). If no such directory exists, returns empty list.
     *  Exit request -- returns from this method.
     * Communication protocol:
     *  File content request:
     *      Client request format expected: (2 :Int) [(path length :Int) (path :String)]
     *      Response format: (file size :Int) (content :byte[])
     *
     *  File listing request:
     *      Client request format expected: (1 :Int) [(path length :Int) (path :String)]
     *      Response format: (number of files :Int) [(file name length :Int) (file name :String) (is directory :boolean)]*
     *  Exit request:
     *      Client request format expected: (3: Int)
     */
    public void run() {
        Logger.getAnonymousLogger().info("New thread has started");
        try {
            while (true) {
                int type = io.readInt();
                if (type == 3) {
                    break;
                }
                switch (type) {
                    case 2:
                        byte[] fileContent = getFile(io.readString(io.readInt()));
                        io.writeLong(fileContent.length);
                        for (byte aFileContent : fileContent) io.writeByte(aFileContent);
                        break;
                    case 1:
                        ServerFile[] content = getDirContent(io.readString(io.readInt()));
                        io.writeInt(content.length);
                        for (ServerFile file : content) {
                            io.writeInt(file.getPath().length());
                            io.writeString(file.getPath());
                            io.writeBoolean(file.isDirectory());
                        }
                        break;
                }
            }
        } catch (IOException e) {
            Logger.getAnonymousLogger().severe("Some error has occurred while execution: " + e.getMessage());
        }
        Logger.getAnonymousLogger().info("Shutting down server thread...");
    }

    /**
     * Given the name of the file, returns its content as bytes[].
     * @param filename name of the file to get the contents of.
     * @return contents of the file.
     * @throws IOException if an error occurred while reading the file.
     */
    @NotNull
    static byte[] getFile(@NotNull String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new byte[0];
        }
        FileInputStream fis = new FileInputStream(file);
        byte[] result = new byte[fis.available()];
        fis.read(result);
        return result;
    }

    /**
     * Given the name of the directory, returns list of files located in it.
     * @param dir path to a directory which content will be listed.
     * @return list of ServerFile instances, describing files in the given directory.
     */
    @NotNull
    static ServerFile[] getDirContent(@NotNull String dir) {
        File fileDir = new File(dir);
        File[] dirContent = fileDir.listFiles();
        if (!fileDir.exists() || dirContent == null) {
            // File does not exist or it's not a directory. Sending 0.
            return new ServerFile[0];
        }

        ServerFile[] result = new ServerFile[dirContent.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new ServerFile(dirContent[i].getName(), dirContent[i].isDirectory());
        }
        return result;
    }
}
