import org.jetbrains.annotations.NotNull;

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
        while (true) {
            int type = io.readInt();
            if (type == 3) {
                break;
            }
            if (type == 2) {
                sendFile();
                continue;
            }
            if (type == 1) {
                sendDirContent();
                continue;
            }
        }
        Logger.getAnonymousLogger().info("Shutting down thread...");
    }

    private void sendFile() {

    }

    private void sendDirContent() {

    }
}
