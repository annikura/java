import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

class FileDataRunnable implements Runnable {
    private final SocketIO io;
    FileDataRunnable(@NotNull Socket socket) throws IOException {
        io = new SocketIOImpl(socket.getInputStream(), socket.getOutputStream());
    }

    FileDataRunnable(@NotNull SocketIO io) {
        this.io = io;
    }

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
                        io.writeBytes(fileContent);
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

    @NotNull
    private byte[] getFile(@NotNull String filename) throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            return new byte[0];
        }
        FileInputStream fis = new FileInputStream(file);
        byte[] result = new byte[fis.available()];
        fis.read(result);
        return result;
    }

    @NotNull
    private static ServerFile[] getDirContent(@NotNull String dir) {
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
