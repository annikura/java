import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Such a file does not exist.");
            return;
        }
        if (!file.isDirectory()) {
            System.out.println("Given path is not a directory.");
        }

        try {
            FileHashCounter.hashInstance(file);
        } catch (IOException e) {
            System.out.println("IO error occurred during execution.");
            return;
        }
    }
}
