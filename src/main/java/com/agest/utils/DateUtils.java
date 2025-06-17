package com.agest.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class DateUtils {
    public static LocalDate findDate(int daysAfter, DayOfWeek dayOfWeek) {
        LocalDate today = LocalDate.now();
        LocalDate nextDayOfWeek = today.with(TemporalAdjusters.next(dayOfWeek));
        return nextDayOfWeek.plusDays(daysAfter);
    }
//
//    public static void main(String[] args) {
//        LocalDate date = findDate(10, DayOfWeek.FRIDAY);
//        System.out.println(date);
//    }
}
