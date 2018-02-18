package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RunTest {
    static String text1;
    static String text2;

    @Test
    public void testNotFoundCommand() throws Exception {
        try {
            new Commander().run(new String[]{"cmd1"});
            Assert.fail();
        } catch (Exception e) {
            assertEquals("Unknown command 'cmd1'", e.getMessage());
        }
    }

    @Test
    public void testCommands() throws Exception {

        Commander commander = new Commander();
        commander.add(TestCommand1.class);
        commander.add(TestCommand2.class);
        commander.run(new String[]{"cmd1"});
        commander.run(new String[]{"cmd2"});
        assertEquals("text1", text1);
        assertEquals("text2", text2);
    }

    @Test
    public void testWithoutArguments() throws Exception {
        try {
            new Commander().run(new String[]{});
            fail();
        } catch (Exception e) {
            assertEquals("No arguments", e.getMessage());
        }
    }

    @Cmd(name = "cmd1")
    public static class TestCommand1 implements Runnable {
        @Override
        public void run() {
            text1 = "text1";
        }
    }

    @Cmd(name = "cmd2")
    public static class TestCommand2 implements Runnable {
        @Override
        public void run() {
            text2 = "text2";
        }
    }
}
