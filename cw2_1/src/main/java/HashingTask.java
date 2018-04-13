import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Task used to execute directory hashing recursively.
 */
public class HashingTask extends RecursiveTask<byte[]> {
    private final File fileToCompute;

    /**
     * Creates new hashing task that will hash given file according to the hashing rules written in FileHashCounter.
     * @see FileHashCounter
     * @param file file to be hashed
     */
    HashingTask(@NotNull File file) {
        fileToCompute = file;
    }

    /**
     * Method used to start hash computation.
     * @return calculated hash
     */
    @Override
    protected byte[] compute() {
        if (fileToCompute.isFile()) {
            byte[] result = null;
            try {
                result = FileHashCounter.hashFile(fileToCompute);
            } catch (IOException ignored) { }
            return result;
        }
        // Then it's directory

        File[] listOfFiles = fileToCompute.listFiles();
        assert listOfFiles != null;

        ArrayList<HashingTask> tasks = new ArrayList<>();

        for (File file : listOfFiles) {
            tasks.add(new HashingTask(file));
        }
        Collection<HashingTask> results = ForkJoinTask.invokeAll(tasks);

        MessageDigest digest = FileHashCounter.createDigest();
        digest.update(fileToCompute.getName().getBytes());

        for (HashingTask result : results) {
            byte[] taskResult = null;
            try {
                taskResult = result.get();
            } catch (Exception ignored) { }
            if (taskResult == null) {
                return null;
            }
            digest.update(taskResult);
        }

        return digest.digest();
    }
}
