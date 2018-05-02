import org.junit.Test;

import java.io.IOException;
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
        assertEquals(Arrays.toString(fileContent), Arrays.toString(FileDataRunnable.getFile(fileWithText)));
    }

    @Test
    public void getNotExistingFile() throws IOException {
        assertEquals("[]", Arrays.toString(FileDataRunnable.getFile("does not exist")));
    }

    @Test
    public void getEmptyFile() throws IOException {
        assertEquals("[]", Arrays.toString(FileDataRunnable.getFile(emptyFile)));
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