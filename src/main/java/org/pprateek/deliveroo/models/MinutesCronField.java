package org.pprateek.deliveroo.models;

public class MinutesCronField extends CronField {
    public MinutesCronField(String incomingText) {
        super(CronFieldType.MINUTES, incomingText);
    }
}
