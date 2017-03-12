package ru.sdp.cli;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DateParamTest {
    private static Date date;
    private static Date dateFrom;
    private static Date dateTo;

    @Test
    public void testGoodDate() throws Exception {
        new Commander().add(GoodDateCommand.class).run(new String[]{"cmd", "--from", "01.01.2016", "--to", "2016-12-31"});
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        assertEquals(format.parse("01.01.2016"), dateFrom);
        assertEquals(format.parse("31.12.2016"), dateTo);
    }

    @Test
    public void testWrongFormat() throws Exception {
        try {
            new Commander().add(GoodDateCommand.class).run(new String[]{"cmd", "--from", "2016-01-01", "--to", "31.12.2016"});
            fail();
        } catch (Exception e) {
            assertEquals("Unparseable date: \"2016-01-01\"", e.getMessage());
        }
    }

    @Test
    public void testEmptyDateFormatDate() throws Exception {
        try {
            new Commander().add(EmptyDateCommand.class).run(new String[]{"cmd", "--from", "01.01.2016", "--to", "2016-12-31"});
            Assert.fail();
        } catch (Exception e) {
            assertEquals("For field date with type Date need format, for example dd.MM.yyyy", e.getMessage());
        }
    }

    @Cmd(name = "cmd")
    public static class GoodDateCommand implements Runnable {
        @Param(names = {"--from"}, format = "dd.MM.yyyy")
        private Date dateFrom;

        @Param(names = {"--to"}, format = "yyyy-MM-dd")
        private Date dateTo;

        Date getDateFrom() {
            return dateFrom;
        }

        Date getDateTo() {
            return dateTo;
        }

        @Override
        public void run() {
            DateParamTest.dateFrom = dateFrom;
            DateParamTest.dateTo = dateTo;
        }
    }

    @Cmd(name = "cmd")
    public static class EmptyDateCommand implements Runnable {
        @Param(names = {"--from"})
        private Date date;

        @Override
        public void run() {
            DateParamTest.date = date;
        }
    }

}
