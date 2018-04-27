import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class EndToEndTest {
    private int port = 7777;
    volatile private static FileDataServer server = null;

    private String mainDir;
    private String fileWithText;
    private byte[] fileContent = {42, 34};
    private String subdir;

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

    @Before
    public void setUpFiles() throws Exception {
        File mainDir = new File(Paths.get("src", "main", "resources", "dir").toString());
        assert mainDir.exists() || mainDir.mkdirs();
        File emptyFile = new File(mainDir, "emptyFile");
        assert emptyFile.exists() || emptyFile.createNewFile();
        File fileWithText = new File(mainDir, "fileWithText");

        FileOutputStream fos = new FileOutputStream(fileWithText);
        fos.write(fileContent);

        File subdir = new File(mainDir, "subdir");
        assert subdir.exists() || subdir.mkdir();
        File fileInSubdir = new File(subdir, "file");
        assert fileInSubdir.exists() || fileInSubdir.createNewFile();

        this.mainDir = mainDir.getAbsolutePath();
        this.subdir = subdir.getAbsolutePath();
        this.fileWithText = fileWithText.getAbsolutePath();
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
    public void getFileRequest() throws Exception {
        FileDataClient client = FileDataClient.createClient("localhost", port);
        byte[] response = client.getFile(fileWithText);
        assertEquals(Arrays.toString(fileContent), Arrays.toString(response));
        client.closeClient();
    }


}
