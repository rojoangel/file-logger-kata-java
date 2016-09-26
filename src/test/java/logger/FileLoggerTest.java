package logger;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileLoggerTest {

    public static final String MESSAGE = "this is a log message";
    public static final String CURRENT_DATE = "20160916";

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
        String fileName = "log" + CURRENT_DATE + ".txt";

        context.checking(new Expectations() {{
            oneOf(dateTimeProvider).current();
            will(returnValue(CURRENT_DATE));

            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(true));

            oneOf(fileSystemHandler).append(MESSAGE, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler, dateTimeProvider);
        fileLogger.log(MESSAGE);
    }

    @Test
    public void testLogCreatesAndAppendsIfFileDoesNotExist() throws Exception {
        String fileName = "log" + CURRENT_DATE + ".txt";

        context.checking(new Expectations() {{
            oneOf(dateTimeProvider).current();
            will(returnValue(CURRENT_DATE));

            oneOf(fileSystemHandler).exists(fileName);
            will(returnValue(false));
            oneOf(fileSystemHandler).create(fileName);

            oneOf(fileSystemHandler).append(MESSAGE, fileName);
        }});

        FileLogger fileLogger = new FileLogger(fileSystemHandler, dateTimeProvider);
        fileLogger.log(MESSAGE);
    }

    @After
    public void tearDown() throws Exception {
        context.assertIsSatisfied();
    }
}