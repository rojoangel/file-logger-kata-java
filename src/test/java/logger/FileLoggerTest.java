package logger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileLoggerTest {

    private Mockery context;
    private LogFileHandler logFileHandler;

    @Before
    public void setUp() throws Exception {
        context = new Mockery();
        logFileHandler = context.mock(LogFileHandler.class);
    }

    @Test
    public void testLogAppendsToTheEndOfLogFile() throws Exception {
        String message = "this is a log message";
        context.checking(new Expectations() {{
            oneOf(logFileHandler).append(message);
        }});

        FileLogger fileLogger = new FileLogger(logFileHandler);
        fileLogger.log(message);
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}