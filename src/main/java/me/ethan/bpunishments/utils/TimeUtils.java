package me.ethan.bpunishments.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    private static int dateDiff(int type, Calendar fromDate, Calendar toDate, boolean future) {
        int diff = 0;

        long savedDate;

        for(savedDate = fromDate.getTimeInMillis(); future && !fromDate.after(toDate) || !future && !fromDate.before(toDate); ++diff) {
            savedDate = fromDate.getTimeInMillis();
            fromDate.add(type, future?1:-1);
        }

        --diff;
        fromDate.setTimeInMillis(savedDate);
        return diff;
    }
    public static String formatDateDiff(Calendar fromDate, Calendar toDate) {
        boolean future = false;

        if (toDate.equals(fromDate)) {
            return "now";
        }
        else {
            if (toDate.after(fromDate)) {
                future = true;
            }

            StringBuilder sb = new StringBuilder();
            int[] types = new int[]{1, 2, 5, 11, 12, 13};
            String[] names = new String[]{"year", "years", "month", "months", "day", "days", "hour", "hours", "minute", "minutes", "second", "seconds"};
            int accuracy = 0;

            for (int i = 0; i < types.length && accuracy <= 2; ++i) {
                int diff = dateDiff(types[i], fromDate, toDate, future);

                if (diff > 0) {
                    ++accuracy;
                    sb.append(" ").append(diff).append(" ").append(names[i * 2 + (diff > 1 ? 1 : 0)]);
                }
            }

            return sb.length() == 0?"now":sb.toString().trim();
        }
    }

}