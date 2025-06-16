package com.agest.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static String findDate(int daysAfter, DayOfWeek dayOfWeek, String format) {
        LocalDate today = LocalDate.now();
        LocalDate nextDayOfWeek = today.with(TemporalAdjusters.next(dayOfWeek));
        LocalDate resultDate = nextDayOfWeek.plusDays(daysAfter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return resultDate.format(formatter);
    }
}
