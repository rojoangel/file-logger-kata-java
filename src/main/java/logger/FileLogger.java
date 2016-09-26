package logger;

public class FileLogger {

    public static final String FILE_NAME = "log.txt";
    private final DateTimeProvider dateTimeProvider;
    private FileSystemHandler fileSystemHandler;

    public FileLogger(FileSystemHandler fileSystemHandler, DateTimeProvider dateTimeProvider) {
        this.fileSystemHandler = fileSystemHandler;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void log(String message) {
        String currentDateTime = dateTimeProvider.current();
        String fileName = "log" + currentDateTime + ".txt";
        if (!fileSystemHandler.exists(fileName)) {
            fileSystemHandler.create(fileName);
        }
        fileSystemHandler.append(message, fileName);
    }
}
