package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

public class UnnecessaryParamTest {

    @Test
    public void testUnnecessaryParams() throws Exception {
        try {
            new Commander().add(SomeCommand.class).run(new String[]{"cmd", "--file", "file", "--dir", "dir"});
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Unnecessary params for command cmd: --file file, --dir dir", e.getMessage());
        }
    }

    @Cmd(name = "cmd")
    public static class SomeCommand implements Runnable {
        @Override
        public void run() {

        }
    }
}
