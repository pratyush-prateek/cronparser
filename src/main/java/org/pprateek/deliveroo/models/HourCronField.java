package org.pprateek.deliveroo.models;

public class HourCronField extends CronField {
    public HourCronField(String incomingText) {
        super(CronFieldType.HOURS, incomingText);
    }
}
