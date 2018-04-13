public class ServerFile {
    private final String path;
    private final boolean isDirectory;

    public ServerFile(String path, boolean isDirectory) {
        this.path = path;
        this.isDirectory = isDirectory;
    }

    public String getPath() {
        return path;
    }

    public boolean isDirectory() {
        return isDirectory;
    }
}
