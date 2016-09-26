package logger;

public class FileLogger {

    private final DateTimeProvider dateTimeProvider;
    private FileSystemHandler fileSystemHandler;

    public FileLogger(FileSystemHandler fileSystemHandler, DateTimeProvider dateTimeProvider) {
        this.fileSystemHandler = fileSystemHandler;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void log(String message) {
        String fileName = getFileName();
        if (!fileSystemHandler.exists(fileName)) {
            fileSystemHandler.create(fileName);
        }
        fileSystemHandler.append(message, fileName);
    }

    private String getFileName() {
        String currentDateTime = dateTimeProvider.current();
        return "log" + currentDateTime + ".txt";
    }
}
