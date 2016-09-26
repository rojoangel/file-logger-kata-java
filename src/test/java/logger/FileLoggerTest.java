package logger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileLoggerTest {

    private Mockery context;
    private FileSystemHandler fileSystemHandler;
    private DateTimeProvider dateTimeProvider;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        fileSystemHandler = context.mock(FileSystemHandler.class);
        dateTimeProvider = context.mock(DateTimeProvider.class);
    }

    @Test
    public void testLogAppendsToTheEndOfAnExistingFile() throws Exception {
        String message = "this is a log message";
        String currentDate = "20160916";
        String fileName = "log" + currentDate + ".txt";

        context.checking(new Expectations() {{
            oneOf(dateTimeProvider).current();
            will(returnValue(currentDate));

            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(true));

            oneOf(fileSystemHandler).append(message, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler, dateTimeProvider);
        fileLogger.log(message);
    }

    @Test
    public void testLogCreatesAndAppendsIfFileDoesNotExist() throws Exception {
        String message = "this is a log message";
        String currentDate = "20160916";
        String fileName = "log" + currentDate + ".txt";

        context.checking(new Expectations() {{
            oneOf(dateTimeProvider).current();
            will(returnValue(currentDate));

            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(false));
            oneOf(fileSystemHandler).create(fileName);

            oneOf(fileSystemHandler).append(message, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler, dateTimeProvider);
        fileLogger.log(message);
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}