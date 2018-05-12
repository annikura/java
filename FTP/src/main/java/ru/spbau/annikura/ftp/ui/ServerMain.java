package ru.spbau.annikura.ftp.ui;

import ru.spbau.annikura.service.FileDataServer;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        new FileDataServer().startServer(7777);
    }
}
