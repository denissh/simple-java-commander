package ru.sdp.cli;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IntegerParamTest {
    private static int primitiveIntValue;
    private static Integer integerValue;
    private static long primitiveLongValue;
    private static Long longValue;

    @Test
    public void testIntegerNumbers() throws Exception {
        new Commander().add(IntegerCommand.class).run(new String[]{"cmd2", "--int", "42", "--long", "43", "--integer", "44", "--longlong", "45"});
        assertEquals(42, primitiveIntValue);
        assertEquals(43L, primitiveLongValue);
        assertEquals(Integer.valueOf(44), integerValue);
        assertEquals(Long.valueOf(45), longValue);
    }

    @Cmd(name = "cmd2", description = "command #2")
    public static class IntegerCommand implements Runnable {
        @Param(names = "--int")
        private int primitiveIntValue;

        @Param(names = "--integer")
        private Integer integerValue;

        @Param(names = "--long")
        private long primitiveLongValue;

        @Param(names = "--longlong")
        private Long longValue;

        int getPrimitiveIntValue() {
            return primitiveIntValue;
        }

        long getPrimitiveLongValue() {
            return primitiveLongValue;
        }

        Integer getIntegerValue() {
            return integerValue;
        }

        Long getLongValue() {
            return longValue;
        }

        @Override
        public void run() {
            IntegerParamTest.primitiveIntValue = primitiveIntValue;
            IntegerParamTest.integerValue = integerValue;
            IntegerParamTest.primitiveLongValue = primitiveLongValue;
            IntegerParamTest.longValue = longValue;

        }
    }
}
