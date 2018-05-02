import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EndToEndTest extends TestWithFiles {
    private int port = 7777;
    volatile private static FileDataServer server = null;

    @Before
    public void startServer() throws Exception {
        if (server != null)
            return;
        server = new FileDataServer();

        new Thread(new Runnable() {
            public void run() {
                try {
                    server.startServer(port);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(100);
    }

    @Test
    public void startConnection() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        client.closeClient();
    }

    @Test
    public void simpleListFilesRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        ServerFile[] response = client.getFiles(subdir);

        assertEquals(1, response.length);
        assertEquals("file", response[0].getPath());
        assertEquals(false, response[0].isDirectory());
        client.closeClient();
    }

    @Test
    public void listFilesRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        ServerFile[] response = client.getFiles(mainDir);
        assertEquals(3, response.length);

        HashMap<String, Boolean> map = new HashMap<>();
        for (ServerFile file : response) {
            map.put(file.getPath(), file.isDirectory());
        }
        assertEquals(true, map.get("subdir"));
        assertEquals(false, map.get("emptyFile"));
        assertEquals(false, map.get("fileWithText"));

        client.closeClient();
    }

    @Test
    public void listNotExistingFilesRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        ServerFile[] response = client.getFiles("not existing dir");
        assertEquals(0, response.length);
        client.closeClient();
    }


    @Test
    public void getFileRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        byte[] response = client.getFile(fileWithText);
        assertEquals(Arrays.toString(fileContent), Arrays.toString(response));
        client.closeClient();
    }

    @Test
    public void getNotExistingFileRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        byte[] response = client.getFile("not existing file");
        assertEquals("[]", Arrays.toString(response));
        client.closeClient();
    }
}
