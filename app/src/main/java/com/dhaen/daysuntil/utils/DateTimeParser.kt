package com.dhaen.daysuntil.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.time.Duration.Companion.days

private const val dateFormatNDayFullMonthNYear= "dd MMMM yyyy"

fun convertEpochSecondsToDate(seconds: Long): String {
    val formatter = SimpleDateFormat(dateFormatNDayFullMonthNYear, Locale.getDefault())
    return formatter.format(Date((seconds * 1000)))
}

fun areDatesOnSameDay(epochSeconds1: Long, epochSeconds2: Long): Boolean {
    // Convert epoch seconds to days since Unix epoch
    fun epochSecondsToDays(epochSeconds: Long): Long {
        return epochSeconds / 1.days.inWholeSeconds // 86400 seconds in a day
    }

    // Calculate day values for both epoch times
    val day1 = epochSecondsToDays(epochSeconds1)
    val day2 = epochSecondsToDays(epochSeconds2)

    // Compare the days
    return day1 == day2
}