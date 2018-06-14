import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.FileDataClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Console utility for downloading specified file from the server.
 */
public class DownloaderClientStarter {
    /**
     * Starts the application.
     * @param args {server_address, server_port, source_file, destination_path}
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 4) {
            System.out.println("Usage:\n\tparams:" +
                    "\n\t\tserver_address" +
                    "\n\t\tport: integer in range 0..65535" +
                    "\n\t\tsource: path to source file" +
                    "\n\t\tdestination: path to save response");
        }
        if (!args[1].matches("\\d{1,5}")) {
            System.out.println("Port is expected to be an integer in range 0 .. 65535");
            return;
        }
        int port = Integer.valueOf(args[1]);
        if (port > 65535) {
            System.out.println("Port is expected to be an integer in range 0 .. 65535");
            return;
        }
        String server = args[0];
        String src = args[2];
        String dest = args[3];
        FileOutputStream destOut;

        try {
            destOut = new FileOutputStream(new File(dest));
        } catch (FileNotFoundException e) {
            System.out.println("Could not create destination file:" + e.getMessage());
            return;
        }

        FileDataClient client;
        try {
            client = FileDataClient.createClient(server, port);
        } catch (IOException e) {
            System.out.println("Failed to establish connection with server: " + e.getMessage());
            return;
        }

        try {
            client.getFile(src, destOut);
        } catch (IOException e) {
            System.out.println("Downloading failure:" + e.getMessage());
        }
        System.out.println("Download completed");
    }
}
