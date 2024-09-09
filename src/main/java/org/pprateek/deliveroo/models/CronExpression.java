package org.pprateek.deliveroo.models;

import org.pprateek.deliveroo.exceptions.InvalidCronExpressionException;

public class CronExpression {
    // 5-2 = 5 6 0 1 2
    // 2,5,10 - 2/10,5,MON-WED
    private CronField minutes;
    private CronField hours;
    private CronField dayOfMonth;
    private CronField month;
    private CronField dayOfWeek;
    private String command;

    public CronExpression(String arg) throws InvalidCronExpressionException {
        String[] cronMembers = arg.split("\\s+");

        if(cronMembers.length != 6)
            throw new InvalidCronExpressionException(String.format("Expected [minutes] [hours] [day-of-month] [month] [day-of-week], but got %s", arg));

        this.minutes = new MinutesCronField(cronMembers[0]);
        this.hours = new HourCronField(cronMembers[1]);
        this.dayOfMonth = new DayOfMonthCronField(cronMembers[2]);
        this.month = new MonthCronField(cronMembers[3]);
        this.dayOfWeek = new DayOfWeekCronField(cronMembers[4]);
        this.command = cronMembers[5];
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(String.format("%-14s%s\n", "minute", minutes.toString()));
        b.append(String.format("%-14s%s\n", "hour", hours.toString()));
        b.append(String.format("%-14s%s\n", "day of month", dayOfMonth.toString()));
        b.append(String.format("%-14s%s\n", "month", month.toString()));
        b.append(String.format("%-14s%s\n", "day of week", dayOfWeek.toString()));
        b.append(String.format("%-14s%s\n", "command", command));
        return b.toString();
    }
}
