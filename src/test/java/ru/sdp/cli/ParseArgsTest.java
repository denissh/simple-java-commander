package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ParseArgsTest {

    @Test
    public void testParseSingleParams() throws Exception {
        Commander commander = new Commander();
        Map<String, String> params = commander.parseSingleParams(Arrays.asList(
                "-o", "param_o",
                "--output", "param_output",
                "-t", "-i"));

        Assert.assertEquals(4, params.size());
        Assert.assertEquals("param_o", params.get("-o"));
        Assert.assertEquals("param_output", params.get("--output"));
        Assert.assertEquals("", params.get("-t"));
        Assert.assertEquals("", params.get("-i"));
    }

    @Test
    public void testParseMultipleParams() throws Exception {
        Commander commander = new Commander();
        Map<String, List<String>> params = commander.parseMultipleParams(Arrays.asList(
                "-o", "param_o_1", "param_o_2",
                "--output", "param_output_1", "--output", "param_output_2",
                "-t", "-i"));

        Assert.assertEquals(4, params.size());
        Assert.assertEquals(2, params.get("-o").size());
        Assert.assertEquals(Arrays.asList("param_o_1", "param_o_2"), params.get("-o"));
        Assert.assertEquals(2, params.get("--output").size());
        Assert.assertEquals(Arrays.asList("param_output_1", "param_output_2"), params.get("--output"));
        Assert.assertEquals(0, params.get("-t").size());
        Assert.assertEquals(0, params.get("-i").size());
    }

}
