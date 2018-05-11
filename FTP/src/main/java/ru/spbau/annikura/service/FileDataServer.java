package ru.spbau.annikura.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class FileDataServer {
    private boolean listening = true;

    void startServer(int portNumber) throws IOException {
        Logger.getAnonymousLogger().info("Server is running");
        ServerSocket socket = new ServerSocket(portNumber);
        Logger.getAnonymousLogger().info("Server socket is created");
        while (listening) {
            new Thread(new FileDataRunnable(socket.accept())).start();
        }
        Logger.getAnonymousLogger().info("Server was stopped");
    }

    void stopListening() {
        listening = false;
    }
}
