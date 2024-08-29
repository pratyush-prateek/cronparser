package org.pprateek.deliveroo.models;

public class DayOfMonthCronField extends CronField {
    public DayOfMonthCronField(String incomingText) {
        super(CronFieldType.DAY_OF_MONTH, incomingText);
    }
}
