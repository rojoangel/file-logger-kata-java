package logger;

public interface FileSystemHandler {
    void append(String message, String fileName);

    boolean exists(String fileName);

    void create(String fileName);
}
