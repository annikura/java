package ru.spbau.annikura.zipfile;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystemException;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A class working with zip-formatted files. Allows to unzip file to some directory on the disk.
 */
public class ZipFile {
    private final static Logger LOGGER;
    private final static int BUFFER_SIZE = 2048;

    static {
        LOGGER = Logger.getLogger(ZipFile.class.getName());
        LOGGER.setLevel(Level.OFF);
        if (java.lang.management.ManagementFactory.getRuntimeMXBean().  // If debug mode is on.
                getInputArguments().toString().contains("jdwp")) {
            LOGGER.setLevel(Level.INFO);
        }
    }

    /**
     * Given a name of the folder, will check its existence. If it doesn't exist, will create it.
     * @param folderName name of the directory
     * @throws FileSystemException will be thrown if an attempt to create a folder will fail due to any reason.
     */
    private static void checkThatFolderExistsOrCreate(@NotNull final String folderName) throws FileSystemException {
        LOGGER.info("Checking folder for existence: " + folderName);
        File folder = new File(folderName);
        if (!folder.exists()) {
            LOGGER.info("Folder wasn't found: " + folderName);
            LOGGER.info("Trying to create folder: " + folderName);
            if (!folder.mkdirs()) {
                LOGGER.info("Failed to create folder: " + folderName);
                throw new FileSystemException(folderName, "", "Failed creating folder");
            } else {
                LOGGER.info("Created successfully: " + folderName);
            }
        }
    }

    /**
     * Creates a file called fileName extracted from the top of zipInputStream in a resultFolder.
     * Not to waste RAM, it writes and reads data from the disk simultaneously, processing it through the buffer.
     * If the buffer is null, new one will be allocated.
     * @param zipInputStream is a stream to read input stream from.
     * @param fileName is a name of a new file.
     * @param resultFolder is a folder where new file will be saved to.
     * @param buffer is a buffer where new data from the stream will be read to.
     * @throws IOException will be thrown if the file already exists.
     */
    private static void unzipFile(@NotNull ZipInputStream zipInputStream,
                                  @NotNull final String fileName, @NotNull final String resultFolder,
                                  @Nullable byte[] buffer) throws IOException {
        if (buffer == null) {
            buffer = new byte[BUFFER_SIZE];
        }
        LOGGER.info("Started working with archive: " + fileName);
        File file = new File(resultFolder + File.separator + fileName);
        if (file.exists()) {
            LOGGER.info("File " + file.getAbsolutePath() + " already exists. Wasn't touched.");
            throw new FileAlreadyExistsException("File " + file.getAbsolutePath() + " already exists.");
        }
        checkThatFolderExistsOrCreate(file.getParent());
        LOGGER.info("Unzipping archive " + file.getAbsolutePath() + " to folder " + resultFolder);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            int inputLength;
            while ((inputLength = zipInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, inputLength);
            }
        }
        LOGGER.info("Successfully unzipped: " + file.getAbsolutePath());
    }

    /**
     * Unzips an archive leaving only files which names were matched by the Predicate.
     * Directories structure is unzipped as it was in the archive.
     * @param file is an archive file.
     * @param resultFolder is a folder where all the files from the archive will be placed to.
     * @param fileNamePredicate allows to filter files. If the result of Predicate.test is true, file will be unzipped.
     * @throws IOException if IO fails
     */
    public static void unzipFilesIf(@NotNull final File file, @NotNull final String resultFolder,
                                    @NotNull Predicate<String> fileNamePredicate) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        LOGGER.info("Started unzipping archive " + file + " to folder " + resultFolder);
        checkThatFolderExistsOrCreate(resultFolder);
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file))) {
            for (ZipEntry zipEntry = zipInputStream.getNextEntry();
                 zipEntry != null;
                 zipEntry = zipInputStream.getNextEntry()) {
                if (zipEntry.isDirectory()) {
                    checkThatFolderExistsOrCreate(resultFolder + File.separator + zipEntry.getName());
                    continue;
                }
                if (!fileNamePredicate.test(zipEntry.getName())) {
                    LOGGER.info("Found file " + zipEntry.getName() + ", but it didn't match the pattern.");
                    continue;
                }
                unzipFile(zipInputStream, zipEntry.getName(), resultFolder, buffer);
            }
            zipInputStream.closeEntry();
        }
        LOGGER.info("Unzipping archive " + file + " was completed successfully");
    }
}
