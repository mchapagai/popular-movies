package com.mchapagai.library.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    private static SimpleDateFormat simpleDateMonthNameFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    /**
     * Helper method for date utilities
     *
     * @param s date string value
     * @return the date in May, 12, 2018 format
     */
    public static String getNameOfMonth(String s) {
        SimpleDateFormat nameOfMonthFormat = new SimpleDateFormat("MMM dd, yyy", Locale.US);
        Date date = null;
        try {
            date = simpleDateMonthNameFormat.parse(s);
        } catch (ParseException e) {
            e.getMessage();
        }

        return nameOfMonthFormat.format(date);
    }

    /**
     * Helper method for date utilities
     *
     * @param s date string value
     * @return the date in 2018 format
     */
    public static String getYearOnly(String s) {
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.US);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.getMessage();
        }

        return yearFormat.format(date);
    }

    public static Calendar getCalender(String timestamp, String pattern) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            Date date = simpleDateFormat.parse(timestamp);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.getMessage();
        }
        return calendar;
    }

}
