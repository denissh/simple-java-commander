package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class UsageTest {

    @Test
    public void testString() throws Exception {
        Commander commander = new Commander();
        commander.add(TestCommand1.class);
        commander.add(TestCommand2.class);
        commander.setProgramName("program.bat");
        List<String> lines = commander.getUsageLines();
        for (String s : lines) {
            System.out.println(s);
        }
        Assert.assertEquals(4, lines.size());
        Assert.assertEquals("Usage: program.bat command [options]", lines.get(0));
        Assert.assertEquals("Commands:", lines.get(1));
        Assert.assertEquals("    cmd1 command #1", lines.get(2));
        Assert.assertEquals("    cmd2 command #2", lines.get(3));
    }

    @Cmd(name = "cmd1", description = "command #1")
    public static class TestCommand1 implements Runnable {
        @Param(names = {"-o", "--output"})
        private String fileOut;

        @Param(names = {"-i", "--input"})
        private String fileIn;

        @Override
        public void run() {

        }
    }

    @Cmd(name = "cmd2", description = "command #2")
    public static class TestCommand2 implements Runnable {

        @Override
        public void run() {

        }
    }
}
