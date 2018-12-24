package com.mchapagai.movies.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

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
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.getMessage();
        }

        return nameOfMonthFormat.format(date);
    }

}
