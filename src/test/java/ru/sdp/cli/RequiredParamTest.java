package ru.sdp.cli;

import org.junit.Test;

public class RequiredParamTest {

    @Test
    public void testUnnecessaryParams() throws Exception {
    }

    @Cmd(name = "cmd")
    public static class SomeCommand implements Runnable {
        @Override
        public void run() {

        }
    }
}
