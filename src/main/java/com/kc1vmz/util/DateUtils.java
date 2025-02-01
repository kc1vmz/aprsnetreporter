package com.kc1vmz.util;

import java.time.LocalDateTime;

public class DateUtils {
    /**
     * get date string from LocalDateTime
     *
     * @param dt
     * @return Date in MM/DD/YYYY format
     */
    public static String getDateStr(LocalDateTime dt) {
        return String.format("%02d/%02d/%d", dt.getMonthValue(),dt.getDayOfMonth(), dt.getYear());
    }

    /**
     * get time string from LocalDateTime
     *
     * @param dt
     * @return hour in HH:MM format
     */
    public static String getTimeStr(LocalDateTime dt) {
        return String.format("%02d:%02d", dt.getHour(),dt.getMinute());
    }
}
