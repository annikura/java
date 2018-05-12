package ru.spbau.annikura.ftp.ui;

import ru.spbau.annikura.service.FileDataServer;

import java.io.IOException;

/**
 * Class that provides a method starting a server.
 */
public class ServerMain {
    public static void main(String[] args) throws IOException {
        new FileDataServer().startServer(7777);
    }
}
