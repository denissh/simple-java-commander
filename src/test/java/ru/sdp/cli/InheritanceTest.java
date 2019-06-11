package ru.sdp.cli;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InheritanceTest {
    private static String fileOut;
    private static String fileIn;

    @Test
    public void testString() throws Exception {
        new Commander().add(StringCommand.class).run(new String[]{"cmd", "-o", "out_file.txt", "-i", "in_file.txt"});
        assertEquals("out_file.txt", fileOut);
        assertEquals("in_file.txt", fileIn);
    }

    @Cmd(name = "cmd")
    public static class StringCommand extends Command1 implements Runnable {

        String getFileOut() {
            return fileOut;
        }

        String getFileIn() {
            return fileIn;
        }

        @Override
        public void run() {
            InheritanceTest.fileIn = fileIn;
            InheritanceTest.fileOut = fileOut;
        }
    }

    public static class Command1 extends Command {
        @Param(names = {"-o", "--output"})
        protected String fileOut;
    }

    public static abstract class Command {
        @Param(names = {"-i", "--input"})
        protected String fileIn;
    }
}
