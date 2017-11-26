import org.jetbrains.annotations.NotNull;
import ru.spbau.annikura.zipfile.ZipFile;

import java.io.File;
import java.util.regex.Pattern;
import java.util.zip.ZipException;

public class UnzipMatchingFiles {
    /**
     * Given a path and a regular expression, extracts all the files from zip-archives lying under the path.
     * Only the files which ame will match a regular expression will be extracted.
     * @param args must contain a valid path and a regular expression.
     */
    public static void main(@NotNull String[] args) {
        if  (args.length != 2) {
            System.out.println("Expected two arguments: directory path and regular expression. Found " + args.length);
            return;
        }
        File directory = new File(args[0]);
        if (!directory.exists()) {
            System.out.println("Directory '" + args[0] + "' does not exist.");
        }
        System.out.println("Reading archives from directory...");
        Pattern pattern = Pattern.compile(args[1]);
        File[] files = directory.listFiles();
        System.out.println("Unzipping...");
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    continue;
                }
                System.out.println("Unzipping " + file.getAbsolutePath());
                try {
                    ZipFile.unzipFilesIf(file, file.getParent(), s -> pattern.matcher(s).matches());
                } catch (ZipException zipException) {
                    System.out.println("File " + file.getName() + " was found, but it's not an archive.");
                }
                System.out.println("Success: " + file.getAbsolutePath());
            }
            System.out.println("Unzipping completed!");
            System.out.println("Done.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
