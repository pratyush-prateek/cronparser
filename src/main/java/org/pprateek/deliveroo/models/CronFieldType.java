package org.pprateek.deliveroo.models;

public enum CronFieldType {
    MINUTES(0, 59, "[minutes]"),
    HOURS(0, 23, "[hours]"),
    DAY_OF_MONTH(1, 31, "[days]"),
    MONTH(1, 12, "[months]"),
    DAY_OF_WEEK(0, 6, "[day-of-week]");

    private final int min;
    private final int max;
    private final String cronFieldSign;

    CronFieldType(int min, int max, String cronFieldSign) {
        this.min = min;
        this.max = max;
        this.cronFieldSign = cronFieldSign;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getCronFieldSign() {
        return cronFieldSign;
    }
}
