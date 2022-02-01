package me.ethan.bpunishments.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static long getTime(int amount, String unit) {
        try {
            switch (unit.toLowerCase()) {
                case "seconds":
                case "second":
                case "sec":
                case "s":
                    return amount * 1000L;
                case "minutes":
                case "minute":
                case "mins":
                case "min":
                    return amount * 1000L * 60L;
                case "hours":
                case "hour":
                case "hrs":
                case "hr":
                    return amount * 1000L * 60L * 60L;
                case "days":
                case "day":
                case "d":
                    return amount * 1000L * 60L * 60L * 24L;
                case "months":
                case "month":
                case "m":
                    return amount * 1000L * 60L * 60L * 24L * 31L;
                case "years":
                case "year":
                case "yrs":
                case "yr":
                    return amount * 1000L * 60L * 60L * 24L * 365L;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static String getExpiration(long duration) {
        return new Date(duration).toString();
    }

}