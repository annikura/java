import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FileDataClientTest {

    @Test
    public void createClient() {
        SocketIO mockSocket = mock(SocketIO.class);
        FileDataClient client = new FileDataClient(mockSocket);
        verifyZeroInteractions(mockSocket);
    }

    @Test
    public void getFiles() throws Exception {
        String path = "path";
        SocketIO mockSocket = mock(SocketIO.class);
        FileDataClient client = new FileDataClient(mockSocket);
        when(mockSocket.readInt()).thenReturn(2).thenReturn(1).thenReturn(4);
        when(mockSocket.readString(1)).thenReturn("f");
        when(mockSocket.readString(4)).thenReturn("zzzz");
        when(mockSocket.readBoolean()).thenReturn(true).thenReturn(false);

        ServerFile[] result = client.getFiles(path);

        verify(mockSocket, new Times(3)).readInt();
        verify(mockSocket).readString(1);
        verify(mockSocket).readString(4);
        verify(mockSocket, new Times(2)).readBoolean();
        verify(mockSocket).writeInt(1);
        verify(mockSocket).writeInt(path.length());
        verify(mockSocket).writeString(path);

        verifyNoMoreInteractions(mockSocket);

        assertEquals(2, result.length);
        assertEquals("f", result[0].getPath());
        assertEquals(true, result[0].isDirectory());
        assertEquals("zzzz", result[1].getPath());
        assertEquals(false, result[1].isDirectory());
    }

    @Test
    public void getFile() throws Exception {
        String path = "path";
        byte[] data = new byte[]{42, 7, 66};
        SocketIO mockSocket = mock(SocketIO.class);
        FileDataClient client = new FileDataClient(mockSocket);
        when(mockSocket.readLong()).thenReturn((long) 3);
        when(mockSocket.readByte()).thenReturn(data[0]).thenReturn(data[1]).thenReturn(data[2]);

        ByteOutputStream out = new ByteOutputStream();
        client.getFile(path, out);
        byte[] result = out.toByteArray();

        verify(mockSocket).readLong();
        verify(mockSocket, new Times(3)).readByte();
        verify(mockSocket).writeInt(2);
        verify(mockSocket).writeInt(path.length());
        verify(mockSocket).writeString(path);

        verifyNoMoreInteractions(mockSocket);

        assertEquals(3, result.length);
        assertEquals(Arrays.toString(data), Arrays.toString(result));
    }

    @Test
    public void closeClient() throws Exception {
        SocketIO mockSocket = mock(SocketIO.class);
        FileDataClient client = new FileDataClient(mockSocket);
        client.closeClient();

        verify(mockSocket).writeInt(3);
        verify(mockSocket).close();

        verifyNoMoreInteractions(mockSocket);
    }
}