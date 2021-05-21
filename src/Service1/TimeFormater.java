package Service1;

public class TimeFormater {
    public static final int MS_IN_SECOND = 1000;
    public static final int SECONDS_IN_MINUTE = 60;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int DAYS_IN_WEEK = 7;

    public static int getMinute(long time) {
        int minute = (int) (time / MS_IN_SECOND / SECONDS_IN_MINUTE % MINUTES_IN_HOUR);
        return minute;
    }

    public static int getHour (long time) {
        int hour = (int) (time / MS_IN_SECOND / (SECONDS_IN_MINUTE * MINUTES_IN_HOUR) % MINUTES_IN_HOUR);
        return hour;
    }

    public static int getHours (long time) {
        int hours = (int) (time / MS_IN_SECOND / (SECONDS_IN_MINUTE * MINUTES_IN_HOUR));
        return hours;
    }

    public static int getDay (long time) {
        int day = (int) (time / (HOURS_IN_DAY * SECONDS_IN_MINUTE * MINUTES_IN_HOUR * MS_IN_SECOND));
        return day;
    }

    public static int getMinutesInWeek () {
        int minutesInWeek = MINUTES_IN_HOUR * HOURS_IN_DAY * DAYS_IN_WEEK;
        return minutesInWeek;
    }
}
