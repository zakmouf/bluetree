package com.bluetree.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {

    private static DecimalFormat integerFormatter = new DecimalFormat("#");

    private static DecimalFormat doubleFormatter = new DecimalFormat("#.00");

    private static DecimalFormat percentFormatter = new DecimalFormat("0.00%");

    private static SimpleDateFormat dateFormatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.US);

    private static SimpleDateFormat timeFormatter = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    public static String formatDate(Date value) {
        return value == null ? null : dateFormatter.format(value);
    }

    public static String formatTime(Date value) {
        return value == null ? null : timeFormatter.format(value);
    }

    public static String formatDouble(Double value) {
        return value == null ? null : value.toString();
    }

    public static String formatLong(Long value) {
        return value == null ? null : value.toString();
    }

    public static Date parseDate(String source) throws ParseException {
        return dateFormatter.parse(source);
    }

    public static String format(int i) {
        return integerFormatter.format(i);
    }

    public static String format(double d) {
        return doubleFormatter.format(d);
    }

    public static String percent(double d) {
        return percentFormatter.format(d);
    }

}