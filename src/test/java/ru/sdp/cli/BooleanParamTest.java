package ru.sdp.cli;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BooleanParamTest {
    private static boolean boolValue;
    private static Boolean booleanValue;

    @Test
    public void testBooleanWithTrueOrFalse() throws Exception {
        new Commander().add(BooleanCommand.class).run(new String[]{"cmd", "--bool", "true", "--boolean", "false"});
        assertTrue(boolValue);
        assertFalse(booleanValue);
    }

    @Test
    public void testBooleanWithoutTrueOrFalse() throws Exception {
        new Commander().add(BooleanCommand.class).run(new String[]{"cmd", "--bool", "--boolean"});
        assertTrue(boolValue);
        assertTrue(booleanValue);
    }

    @Test
    public void testBooleanWithoutparams() throws Exception {
        new Commander().add(BooleanCommand.class).run(new String[]{"cmd"});
        assertFalse(boolValue);
        assertFalse(booleanValue);
    }

    @Cmd(name = "cmd")
    public static class BooleanCommand implements Runnable {

        @Param(names = "--bool")
        private boolean boolValue;

        @Param(names = "--boolean")
        private Boolean booleanValue;

        public boolean getBoolValue() {
            return boolValue;
        }

        public boolean getBooleanValue() {
            return booleanValue;
        }

        @Override
        public void run() {
            BooleanParamTest.booleanValue = booleanValue;
            BooleanParamTest.boolValue = boolValue;
        }
    }
}
