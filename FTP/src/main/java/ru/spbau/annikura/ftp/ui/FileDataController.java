package ru.spbau.annikura.ftp.ui;

import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.FileDataClient;
import ru.spbau.annikura.service.ServerFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

/**
 * FileDataClient wrapper that stores the state: current location in directory hierarchy.
 */
public class FileDataController {
    private final FileDataClient client;
    private String currentDirectory = "";

    /**
     * Creates FileDataController connected to the given url on the specified port.
     * Initial directory state is an empty relative path.
     * @param port server port to connect to.
     * @param url server url.
     * @throws IOException if an error occurs while establing the connection.
     */
    public FileDataController(int port, String url) throws IOException {
        client = FileDataClient.createClient(url, port);
    }

    /**
     * Closes server connection.
     * @throws IOException if
     */
    public void closeConnection() throws IOException {
        client.closeClient();
    }

    /**
     * Changes state to the one at the new absolute path.
     * @param path new path.
     */
    public void moveOnAbsolutePath(@NotNull String path) {
        currentDirectory = path;
    }

    /**
     * Changes state to the one at the new relative path. If given path contains "..", it will be normalized if possible.
     * @param path relative path which will be appended at the top of the current state path.
     */
    public void moveOnRelativePath(@NotNull String path) {
        currentDirectory = Paths.get(currentDirectory, path).normalize().toString();
    }

    /**
     * Gets list of files from the server stored in the current state directory.
     * @return list of received files
     * @throws IOException is thrown if an error occurs during the data transmission.
     */
    public ServerFile[] getFiles() throws IOException {
        return client.getFiles(currentDirectory);
    }

    /**
     * Current path getter.
     * @return current directory path.
     */
    public String getPath() {
        return currentDirectory;
    }

    /**
     * Downloads chosen file in the current directory into the given File location.
     * @param filename name of the file that should be downloaded
     * @param toFile destination file descriptor
     * @throws IOException if download is interrupted because of reading from server or writing to the disk error.
     */
    public void downloadTo(@NotNull String filename, @NotNull File toFile) throws IOException {
        String path = Paths.get(currentDirectory, filename).toString();
        FileOutputStream fos = new FileOutputStream(toFile);
        client.getFile(path, fos);
        fos.close();
    }
}
