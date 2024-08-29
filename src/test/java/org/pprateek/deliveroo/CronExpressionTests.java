package org.pprateek.deliveroo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.pprateek.deliveroo.exceptions.InvalidCronExpressionException;
import org.pprateek.deliveroo.models.*;

public class CronExpressionTests {

    @Test
    public void testValidRanges() throws InvalidCronExpressionException {
        CronField cronField = new MinutesCronField("1-5");
        Assertions.assertEquals("1 2 3 4 5", cronField.toString());
        cronField = new DayOfMonthCronField("1-15");
        Assertions.assertEquals("1 2 3 4 5 6 7 8 9 10 11 12 13 14 15", cronField.toString());
        cronField = new HourCronField("0-10");
        Assertions.assertEquals("0 1 2 3 4 5 6 7 8 9 10", cronField.toString());
    }

    @Test
    public void testInvalidRanges() {
        assertException(() -> {
            new MinutesCronField("10-100");
        }, "[minutes] start and end ranges must be within 0 and 59");
        assertException(() -> {
            new HourCronField("1-25");
        }, "[hours] start and end ranges must be within 0 and 23");
        assertException(() -> {
            new DayOfMonthCronField("0-15");
        }, "[days] start and end ranges must be within 1 and 31");
        assertException(() -> {
            new DayOfWeekCronField("1-4-1");
        }, "[day-of-week] range values must only contain start and end ranges");
    }

    @Test
    public void testValidCommaValues() throws InvalidCronExpressionException {
        CronField cronField = new MinutesCronField("1,5");
        Assertions.assertEquals("1 5", cronField.toString());
        cronField = new HourCronField("12,17");
        Assertions.assertEquals("12 17", cronField.toString());
    }

    @Test
    public void testInvalidCommaValues() {
        assertException(() -> {
            new MinutesCronField("12,");
        }, "[minutes] comma separated values are expected");
        assertException(() -> {
            new HourCronField("0,AA");
        },"[hours] comma separated values should be numeric.");
        assertException(() -> {
            new HourCronField("28,60");
        }, "[hours] must be between 0 and 23");
    }

    @Test
    public void testValidStarValues() {
        CronField cronField = new HourCronField("*");
        Assertions.assertEquals("0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23", cronField.toString());
        cronField = new MonthCronField("*");
        Assertions.assertEquals("1 2 3 4 5 6 7 8 9 10 11 12", cronField.toString());
    }

    @Test
    public void testInvalidStarValues() {
        assertException(() -> {
            new HourCronField("**");
        }, "[hours] single star value is allowed");
        assertException(() -> {
            new MinutesCronField("4*");
        }, "[minutes] single star value is allowed");
    }

    @Test
    public void testValidStepValues() {
        CronField cronField = new MinutesCronField("*/15");
        Assertions.assertEquals("0 15 30 45", cronField.toString());
        cronField = new MonthCronField("4/3");
        Assertions.assertEquals("4 7 10", cronField.toString());
    }

    @Test
    public void testInvalidStepValues() {
        assertException(() -> {
            new MinutesCronField("4/5/6");
        }, "[minutes] expected start and a step value");
        assertException(() -> {
            new MinutesCronField("60/5");
        }, "[minutes] start value must be between 0 and 59");
        assertException(() -> {
            new MinutesCronField("AA/6");
        }, "[minutes] start must be valid in step values.");
        assertException(() -> {
            new MinutesCronField("23/AA");
        }, "[minutes] step value must be an integer in step values.");
    }

    private void assertException(Runnable runnable, String expectedMessage) {
        try {
            runnable.run();
        }
        catch (InvalidCronExpressionException ex) {
            Assertions.assertEquals(ex.getMessage(), expectedMessage);
        }
    }
}
