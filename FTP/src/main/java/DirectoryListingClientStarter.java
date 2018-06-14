import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.FileDataClient;
import ru.spbau.annikura.service.ServerFile;

import java.io.IOException;

/**
 * Console utility to list files in the specified server directory.
 */
public class DirectoryListingClientStarter {
    /**
     * Starts the application.
     * @param args {server_address, port, source_dir}"
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 3) {
            System.out.println("Usage:\n\tparams:" +
                    "\n\t\tserver_address" +
                    "\n\t\tport: integer in range 0..65535" +
                    "\n\t\tsource: path to source directory");
            return;
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

        FileDataClient client;
        try {
            client = FileDataClient.createClient(server, port);
        } catch (IOException e) {
            System.out.println("Failed to establish connection with server: " + e.getMessage());
            return;
        }

        ServerFile[] files;
        try {
            files = client.getFiles(src);
        } catch (IOException e) {
            System.out.println("Receiving data failure:" + e.getMessage());
            return;
        }
        for (ServerFile file : files) {
            if (file.isDirectory())
                System.out.println(" dir: " + file.getPath());
            else
                System.out.println("file: " + file.getPath());
        }

        System.out.println("Done");
    }
}
