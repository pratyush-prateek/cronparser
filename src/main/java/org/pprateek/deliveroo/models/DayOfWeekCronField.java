package org.pprateek.deliveroo.models;

import java.util.Map;
import java.util.HashMap;

public class DayOfWeekCronField extends CronField {
    // SUN, MON, TUES
    // 0    1,   2 ...
    // MON/2 TUES/2
    public DayOfWeekCronField(String incomingText) {
        super(CronFieldType.DAY_OF_WEEK, incomingText, DayOfWeekCronField.getExtensionToNumericMapping());
    }

    private static Map<String, Integer> getExtensionToNumericMapping() {
        Map<String, Integer> extensionToNumericMapping = new HashMap<>();
        extensionToNumericMapping.put("SUN", 0);
        extensionToNumericMapping.put("MON", 1);
        extensionToNumericMapping.put("TUES", 2);
        extensionToNumericMapping.put("WED", 3);
        extensionToNumericMapping.put("THURS", 4);
        extensionToNumericMapping.put("FRI", 5);
        extensionToNumericMapping.put("SAT", 6);
        return extensionToNumericMapping;
    }
}
