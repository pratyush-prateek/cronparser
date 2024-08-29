package org.pprateek.deliveroo.models;

public class DayOfWeekCronField extends CronField {
    public DayOfWeekCronField(String incomingText) {
        super(CronFieldType.DAY_OF_WEEK, incomingText);
    }
}
