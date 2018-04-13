import com.sun.istack.internal.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class HashingTask extends RecursiveTask<byte[]> {
    private final File fileToCompute;

    HashingTask(@NotNull File file) {
        fileToCompute = file;
    }

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
        ForkJoinTask.invokeAll(tasks);

        MessageDigest digest = FileHashCounter.createDigest();
        digest.update(fileToCompute.getName().getBytes());

        for (HashingTask task : tasks) {
            byte[] taskResult = task.compute();
            if (taskResult == null) {
                return null;
            }
            digest.update(taskResult);
        }

        return digest.digest();
    }
}
