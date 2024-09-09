package org.pprateek.deliveroo.models;

import org.pprateek.deliveroo.utils.ExtensionUtils;

public class DayOfWeekCronField extends CronField {
    public DayOfWeekCronField(String incomingText) {
        super(CronFieldType.DAY_OF_WEEK, incomingText, ExtensionUtils.getDayOfWeekNumericToExtensionMapping(), ExtensionUtils.getDayOfWeekExtensionToNumericMapping());
    }
}
