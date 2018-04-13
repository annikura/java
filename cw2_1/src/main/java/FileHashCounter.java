import com.sun.istack.internal.NotNull;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ForkJoinPool;

public class FileHashCounter {
    private final static int BUFFER_SIZE = 1024;

    @NotNull
    static MessageDigest createDigest() {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) { }
        assert digest != null;
        return digest;
    }

    @NotNull
    public static byte[] hashInstance(@NotNull File file) throws IOException {
        if (file.isDirectory()) {
            return hashDirectory(file);
        } else {
            return hashFile(file);
        }
    }


    @NotNull
    public static byte[] hashInstanceInParallel(@NotNull final File file) throws IOException {
        ForkJoinPool fjp = new ForkJoinPool();
        HashingTask task = new HashingTask(file);
        fjp.submit(task);
        return task.compute();
    }

    @NotNull
    static byte[] hashFile(@NotNull File file) throws IOException {
        assert !file.isDirectory();

        InputStream is = new BufferedInputStream(new FileInputStream(file));
        MessageDigest digest = createDigest();
        byte[] buffer = new byte[BUFFER_SIZE];
        while (is.available() > 0) {
            int len = is.read(buffer);
            digest.update(buffer, 0, len);
        }

        return digest.digest();
    }

    @NotNull
    static byte[] hashDirectory(@NotNull File dir) throws IOException {
        assert dir.isDirectory();

        MessageDigest digest = createDigest();
        digest.update(dir.getName().getBytes());

        File[] listOfFiles = dir.listFiles();
        assert listOfFiles != null;

        for (File file : listOfFiles) {
            digest.update(hashInstance(file));
        }

        return digest.digest();
    }
}