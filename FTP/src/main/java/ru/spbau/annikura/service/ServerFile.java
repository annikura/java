package ru.spbau.annikura.service;

import org.jetbrains.annotations.NotNull;

/**
 * Class storing simple information about file: name and type (directory or file)
 */
public class ServerFile {
    private final String path;
    private final boolean isDirectory;

    /**
     * Constructs ServerFile storing given data.
     * @param path file path.
     * @param isDirectory file type.
     */
    public ServerFile(@NotNull String path, boolean isDirectory) {
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}
