package logger;

public class FileLogger {

    public static final String FILE_NAME = "log.txt";
    private FileSystemHandler fileSystemHandler;

    public FileLogger(FileSystemHandler fileSystemHandler) {
        this.fileSystemHandler = fileSystemHandler;
    }

    public void log(String message) {
        if (!fileSystemHandler.exists(FILE_NAME)) {
            fileSystemHandler.create(FILE_NAME);
        }
        fileSystemHandler.append(message, FILE_NAME);
    }
}
