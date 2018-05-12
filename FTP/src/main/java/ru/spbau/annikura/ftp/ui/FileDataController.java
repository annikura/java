package ru.spbau.annikura.ftp.ui;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.FileDataClient;
import ru.spbau.annikura.service.ServerFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

public class FileDataController {
    private final FileDataClient client;
    private String currentDirectory = "";

    public FileDataController(int port, String url) throws IOException {
        client = FileDataClient.createClient(url, port);
    }

    public void closeConnection() throws IOException {
        client.closeClient();
    }

    public void moveOnAbsolutePath(@NotNull String path) {
        currentDirectory = path;
    }

    public void moveOnRelativePath(@NotNull String path) {
        currentDirectory = Paths.get(currentDirectory, path).normalize().toString();
    }

    public ServerFile[] getFiles() throws IOException {
        return client.getFiles(currentDirectory);
    }

    public String getPath() {
        return currentDirectory;
    }

    public void downloadTo(@NotNull String filename, @NotNull File toFile) throws IOException {
        String path = Paths.get(currentDirectory, filename).toString();
        FileOutputStream fos = new FileOutputStream(toFile);
        client.getFile(path, fos);
        fos.close();
    }
}
