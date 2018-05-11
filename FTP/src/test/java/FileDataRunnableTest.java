import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class FileDataRunnableTest extends TestWithFiles {
    @Test
    public void createInstance() {
        SocketIO socketIO = mock(SocketIO.class);
        FileDataRunnable runnable = new FileDataRunnable(socketIO);
        verifyZeroInteractions(socketIO);
    }

    @Test
    public void getFile() throws IOException {
        InputStream in = FileDataRunnable.getFile(fileWithText);
        byte[] result = new byte[in.available()];
        in.read(result);
        assertEquals(Arrays.toString(fileContent), Arrays.toString(result));
    }

    @Test
    public void getNotExistingFile() throws IOException {
        InputStream in = FileDataRunnable.getFile("does not exist");
        assertNull(in);
    }

    @Test
    public void getEmptyFile() throws IOException {
        InputStream in = FileDataRunnable.getFile(emptyFile);
        byte[] result = new byte[in.available()];
        in.read(result);
        assertEquals("[]", Arrays.toString(result));
    }

    @Test
    public void getNotExistingDirContent() {
        assertEquals(0, FileDataRunnable.getDirContent("does not exist").length);
    }

    @Test
    public void getDirContent() {
        ServerFile[] result = FileDataRunnable.getDirContent(subdir);
        assertEquals(1, result.length);
        assertEquals("file", result[0].getPath());
        assertEquals(false, result[0].isDirectory());
    }
}