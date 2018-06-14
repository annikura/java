import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.service.FileDataServer;

import java.io.IOException;

/**
 * Class for starting FileDataServer
 */
public class ServerStarter {
    /**
     * Starts the server.
     * @param args {server_port}
     */
    public static void main(@NotNull String[] args) {
        if (args.length != 1) {
            System.out.println("Usage:\n\tparams: server_port");
            return;
        }
        if (!args[0].matches("\\d{0,5}")) {
            System.out.println("Port is expected to be an integer in range 0 .. 65535");
            return;
        }
        int port = Integer.valueOf(args[0]);
        if (port > 65535) {
            System.out.println("Port is expected to be an integer in range 0 .. 65535");
            return;
        }
        try {
            new FileDataServer().startServer(port);
        } catch (IOException e) {
            System.out.println("Failed to start server: " + e.getMessage());
        }
    }
}
