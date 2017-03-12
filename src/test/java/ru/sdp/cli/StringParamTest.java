package ru.sdp.cli;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StringParamTest {
    private static String fileOut;
    private static String fileIn;

    @Test
    public void testString() throws Exception {
        new Commander().add(StringCommand.class).run(new String[]{"cmd", "-o", "out_file.txt", "-i", "in_file.txt"});
        assertEquals("out_file.txt", fileOut);
        assertEquals("in_file.txt", fileIn);
    }

    @Cmd(name = "cmd")
    public static class StringCommand implements Runnable {
        @Param(names = {"-o", "--output"})
        private String fileOut;

        @Param(names = {"-i", "--input"})
        private String fileIn;


        String getFileOut() {
            return fileOut;
        }

        String getFileIn() {
            return fileIn;
        }

        @Override
        public void run() {
            StringParamTest.fileIn = fileIn;
            StringParamTest.fileOut = fileOut;
        }
    }
}
