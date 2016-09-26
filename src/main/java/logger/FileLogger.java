package logger;

public class FileLogger {

    private LogFileHandler logFileHandler;

    public FileLogger(LogFileHandler logFileHandler) {
        this.logFileHandler = logFileHandler;
    }

    public void log(String message) {
        this.logFileHandler.append(message);
    }
}
