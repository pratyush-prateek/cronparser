package org.pprateek.deliveroo.utils;

import java.util.HashMap;
import java.util.Map;

public class ExtensionUtils {
    public static Map<String, String> getDayOfWeekNumericToExtensionMapping() {
        Map<String, String> numericToExtensionMapping = new HashMap<>();
        numericToExtensionMapping.put("0", "SUN");
        numericToExtensionMapping.put("1", "MON");
        numericToExtensionMapping.put("2", "TUES");
        numericToExtensionMapping.put("3", "WED");
        numericToExtensionMapping.put("4", "THURS");
        numericToExtensionMapping.put("5", "FRI");
        numericToExtensionMapping.put("6", "SAT");
        return numericToExtensionMapping;
    }

    public static Map<String, String> getDayOfWeekExtensionToNumericMapping() {
        Map<String, String> extensionToNumericMapping = new HashMap<>();
        extensionToNumericMapping.put("SUN", "0");
        extensionToNumericMapping.put("MON", "1");
        extensionToNumericMapping.put("TUE", "2");
        extensionToNumericMapping.put("WED", "3");
        extensionToNumericMapping.put("THURS", "4");
        extensionToNumericMapping.put("FRI", "5");
        extensionToNumericMapping.put("SAT", "6");
        return extensionToNumericMapping;
    }
}
