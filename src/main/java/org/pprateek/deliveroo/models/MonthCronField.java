package org.pprateek.deliveroo.models;

public class MonthCronField extends CronField {
    public MonthCronField(String incomingText) {
        super(CronFieldType.MONTH, incomingText);
    }
}
