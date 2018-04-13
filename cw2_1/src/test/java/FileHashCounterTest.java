import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FileHashCounterTest {
    private final static int BUFFER_SIZE = 10000;

    private String resourceFilePath(@NotNull String name) {
        return Paths.get("src", "test", "resources", name).toString();
    }

    @Test
    public void createDigest() {
        assertNotNull(FileHashCounter.createDigest());
    }

    @Test
    public void hashSimpleFile() throws IOException {
        File file = new File(resourceFilePath("simpleFile"));
        assertEquals(
                Arrays.toString(hashFileContent(file)),
                Arrays.toString(FileHashCounter.hashFile(file))
        );
    }

    @Test
    public void hashSeveralLinesFile() throws IOException {
        File file = new File(resourceFilePath("severalLinesFile"));
        assertEquals(
                Arrays.toString(hashFileContent(file)),
                Arrays.toString(FileHashCounter.hashFile(file))
        );
    }


    private byte[] hashFileContent(@NotNull File file) throws IOException {
        InputStream is = new FileInputStream(file);
        byte[] buffer = new byte[BUFFER_SIZE];
        int len = is.read(buffer);
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) { }
        assert digest != null;

        digest.update(buffer, 0, len);
        return digest.digest();
    }

    private byte[] hashString(@NotNull String str) throws IOException {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) { }
        assert digest != null;

        digest.update(str.getBytes());
        return digest.digest();
    }

    @Test
    public void hashEmptyDir() throws IOException {
        File file = new File(resourceFilePath("dir"));
        file.mkdir();
        assertEquals(
                Arrays.toString(hashString(file.getName())),
                Arrays.toString(FileHashCounter.hashDirectory(file))
        );
    }

}