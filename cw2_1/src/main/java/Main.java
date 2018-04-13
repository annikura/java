import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Arguments list is empty. At least one argument is required.");
            return;
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("Such a file does not exist.");
            return;
        }
        if (!file.isDirectory()) {
            System.out.println("Given path is not a directory.");
        }

        byte[] simpleResult = null;
        byte[] parallelResult = null;

        long startTime = System.currentTimeMillis();
        try {
            simpleResult = FileHashCounter.hashInstance(file);
        } catch (IOException e) {
            System.out.println("IO error occurred during execution.");
            return;
        }
        long finishTime = System.currentTimeMillis();
        long simpleHashingDuration = finishTime - startTime;

        startTime = System.currentTimeMillis();
        parallelResult = FileHashCounter.hashInstanceInParallel(file);
        if (parallelResult == null) {
            System.out.println("IO error occurred during parallel execution.");
            return;
        }
        finishTime = System.currentTimeMillis();

        long parallelHashingDuration = finishTime - startTime;

        System.out.println("Simple algorithm worked in " + simpleHashingDuration + " milliseconds");
        System.out.println("Parallel algorithm worked in " + parallelHashingDuration + " milliseconds");
    }
}
