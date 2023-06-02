package com.example.stuffmanagerapp.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Calculations {
    public static List<Integer> convertStringToIntegerList(String string) {
        var integerList = new ArrayList<Integer>();
        String errLog = "";
        String[] array;
        array = string.split(" ");
        for (String s: array) {
            integerList.add(Integer.valueOf(s));
        }
        return integerList;
    }

    public static boolean isCurrentDateNearQuarter() {
        var check = false;
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        var month = cal.get(Calendar.MONTH) + 1;
        var day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        if ((month % 4 == 3) && (day > 20)) {
            check = true;
        }
        return check;
    }

    public static Date getCurrentTime() {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        return new Date(cal.getTimeInMillis());
    }

    public static Date addWeek() {
        var cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DAY_OF_MONTH, 7);
        return new Date(cal.getTimeInMillis());
    }

    public static Integer getDaysBetween(Date from, Date to) {
        int days;
        long first = from.getTime();
        long second = to.getTime();
        var substract = (second - first)/1000/3600/24;
        days = Math.toIntExact(substract + 1);
        return days;
    }

    public static Date calculateNextInspectionDate(Integer inspectionTerm) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.MONTH, inspectionTerm);
        return new Date(cal.getTimeInMillis());
    }

    public static Date calculateUsageEndDate(Integer usageTerm) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.YEAR, usageTerm);
        return new Date(cal.getTimeInMillis());
    }
}
