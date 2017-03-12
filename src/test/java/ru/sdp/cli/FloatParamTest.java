package ru.sdp.cli;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FloatParamTest {
    private static double primitiveDoubleValue;
    private static float primitiveFloatValue;
    private static Double doubleValue;
    private static Float floatValue;

    @Test
    public void testFloatNumbers() throws Exception {
        new Commander().add(FloatCommand.class).run(new String[]{"cmd", "--double", "42.42", "--float", "43.43", "--doubleclass", "44.44", "--floatclass", "45.45"});
        assertEquals(42.42d, primitiveDoubleValue, 0.0001);
        assertEquals(43.43f, primitiveFloatValue, 0.0001);

        assertEquals(Double.valueOf(44.44d), doubleValue);
        assertEquals(Float.valueOf(45.45f), floatValue);
    }

    @Cmd(name = "cmd")
    public static class FloatCommand implements Runnable {

        @Param(names = "--double")
        private double primitiveDoubleValue;

        @Param(names = "--float")
        private float primitiveFloatValue;


        @Param(names = "--doubleclass")
        private Double doubleValue;

        @Param(names = "--floatclass")
        private Float floatValue;

        double getPrimitiveDoubleValue() {
            return primitiveDoubleValue;
        }

        float getPrimitiveFloatValue() {
            return primitiveFloatValue;
        }

        Double getDoubleValue() {
            return doubleValue;
        }

        Float getFloatValue() {
            return floatValue;
        }

        @Override
        public void run() {
            FloatParamTest.primitiveDoubleValue = primitiveDoubleValue;
            FloatParamTest.primitiveFloatValue = primitiveFloatValue;
            FloatParamTest.doubleValue = doubleValue;
            FloatParamTest.floatValue = floatValue;
        }
    }
}
