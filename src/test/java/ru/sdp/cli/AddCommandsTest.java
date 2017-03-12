package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

public class AddCommandsTest {

    @Test
    public void addCommand() throws Exception {
        Commander commander = new Commander();
        commander.add(SomeCommand.class);
        Assert.assertEquals("SomeCommand", commander.get("cmd").getSimpleName());
    }

    @Test
    public void addCommandWithoutAnnotationCmd() throws Exception {
        try {
            new Commander().add(NoCmdCommand.class);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("Need @Cmd for class NoCmdCommand", e.getMessage());
        }
    }

    @Cmd(name = "cmd")
    public static class SomeCommand implements Runnable {
        @Override
        public void run() {

        }
    }

    private class NoCmdCommand implements Runnable {
        @Override
        public void run() {

        }
    }
}
