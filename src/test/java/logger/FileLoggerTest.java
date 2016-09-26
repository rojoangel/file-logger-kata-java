package logger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileLoggerTest {

    private Mockery context;
    private FileSystemHandler fileSystemHandler;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        fileSystemHandler = context.mock(FileSystemHandler.class);
    }

    @Test
    public void testLogAppendsToTheEndOfLogFile() throws Exception {
        String message = "this is a log message";
        String fileName = "log.txt";
        context.checking(new Expectations() {{
            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(true));

            oneOf(fileSystemHandler).append(message, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler);
        fileLogger.log(message);
    }

    @Test
    public void testLogCreatesFileIfItDoesNotExist() throws Exception {
        String message = "this is a log message";
        String fileName = "log.txt";
        context.checking(new Expectations() {{
            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(false));

            oneOf(fileSystemHandler).create(fileName);
            oneOf(fileSystemHandler).append(message, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler);
        fileLogger.log(message);
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}