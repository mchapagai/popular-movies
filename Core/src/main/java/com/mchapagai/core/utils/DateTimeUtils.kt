package com.mchapagai.core.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object DateTimeUtils {

    private const val DATE_PATTERN_MONTH_NAME = "MMM dd, yyy"
    private const val DATE_PATTERN_YEAR_ONLY = "yyyy"
    private const val DATE_PATTERN_DEFAULT = "yyyy-MM-dd"

    fun getNameOfMonth(dateString: String): String {
        val inputFormat = SimpleDateFormat(DATE_PATTERN_DEFAULT, Locale.US)
        val outputFormat = SimpleDateFormat(DATE_PATTERN_MONTH_NAME, Locale.US)
        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.localizedMessage
            ""
        }
    }

    fun getYearOnly(dateString: String): String {
        val inputFormat = SimpleDateFormat(DATE_PATTERN_DEFAULT, Locale.US)
        val outputFormat = SimpleDateFormat(DATE_PATTERN_YEAR_ONLY, Locale.US)
        return try {
            val date = inputFormat.parse(dateString)
            outputFormat.format(date)
        } catch (e: Exception) {
            e.localizedMessage
            ""
        }
    }

    fun getCalendar(timestamp: String, pattern: String): Calendar {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
        simpleDateFormat.timeZone = TimeZone.getDefault()
        try {
            val date = simpleDateFormat.parse(timestamp)
            calendar.time = date
        } catch (e: Exception) {
            e.localizedMessage
        }
        return calendar
    }
}