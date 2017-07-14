package ru.sdp.cli;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

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
        assertEquals(6, lines.size());
        assertEquals("Usage: program.bat command [options]", lines.get(0));
        assertEquals("Commands:", lines.get(1));
        assertEquals("    cmd1 - command #1", lines.get(2));
        assertEquals("        -o, --output - Output file", lines.get(3));
        assertEquals("        -i, --input", lines.get(4));
        assertEquals("    cmd2", lines.get(5));
    }

    @Cmd(name = "cmd1", description = "command #1")
    public static class TestCommand1 implements Runnable {
        @Param(names = {"-o", "--output"}, description = "Output file")
        private String fileOut;

        @Param(names = {"-i", "--input"})
        private String fileIn;

        @Override
        public void run() {

        }
    }

    @Cmd(name = "cmd2")
    public static class TestCommand2 implements Runnable {

        @Override
        public void run() {

        }
    }
}
