package com.younchen.younsampleproject.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 龙泉 on 2016/11/2.
 */

public class DateUtils {

    private static final String TAG = "DateUtils";


    private static String simpleDateFormatStr = "yyyy/MM/dd";
    private static String simpleDateFormat2Str = "yyyy-MM-dd";

    private static final long MINUTES_SECONDS = 60;
    private static final long HOUR_SECONDS = 3600;
    private static final long DAY_SECONDS = HOUR_SECONDS * 24;
    private static final long MONTH_SECONDS = DAY_SECONDS * 30;
    private static final long YEAR_SECONDS = DAY_SECONDS * 365;


    public static final int TIME_UNIT_YEAR = 1;
    public static final int TIME_UNIT_MONTH = 2;
    public static final int TIME_UNIT_DAY = 3;
    public static final int TIME_UNIT_HOUR = 4;
    public static final int TIME_UNIT_MINUTE = 5;
    public static final int TIME_UNIT_SECONDS= 6;
    /**
     * @param seconds seconds form 1970
     * @return
     */
    public static String formatToYYMMDD(long seconds) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(simpleDateFormatStr);
        return simpleDateFormat.format(new Date(seconds * 1000));
    }


    /**
     *
     * @param seconds1 seconds from 1970
     * @param seconds2 seconds from 1970
     * @return
     */
    public static boolean isNotSameDay(long seconds1, long seconds2) {
        Date data1 = new Date(seconds1 * 1000);
        Date data2 = new Date(seconds2 * 1000);
        return data1.getDay() != data2.getDay() || data1.getMonth() != data2.getMonth() || data1.getYear() != data2.getYear();
    }

    public static DateBean getTimeDeff(long timeDeff) {
        long duringYears = timeDeff / YEAR_SECONDS;
        if (duringYears > 0) {
            return new DateBean(true, duringYears, TIME_UNIT_YEAR);
        }
        long duringMonths = timeDeff / MONTH_SECONDS;
        if (duringMonths > 0) {
            return new DateBean(true, duringMonths, TIME_UNIT_MONTH);
        }
        long duringDays = timeDeff / DAY_SECONDS;
        if (duringDays > 0) {
            return new DateBean(true, duringDays, TIME_UNIT_DAY);
        }

        long duringHours = timeDeff / HOUR_SECONDS;
        if (duringHours > 0) {
            return new DateBean(true, duringHours, TIME_UNIT_HOUR);
        }

        long duringMinutes = timeDeff / MINUTES_SECONDS;
        if (duringMinutes > 0) {
            return new DateBean(true, duringMinutes, TIME_UNIT_MINUTE);
        } else {
            return new DateBean(TIME_UNIT_SECONDS);
        }
    }

    public static DateBean getBetweenTime(long tarGetTimeSeconds) {
        long targetTimeMiliseconds = tarGetTimeSeconds * 1000;


        Calendar nowTime = Calendar.getInstance();//获取当前日历对象
        Calendar otherTime = Calendar.getInstance();

        //long targetUnixTime = targetTimeMiliseconds + TimeZone.getDefault().getRawOffset();
        otherTime.setTimeInMillis(targetTimeMiliseconds);
//        long unixTime = nowTime.getTimeInMillis();//获取当前时区下日期时间对应的时间戳
//        long unixTimeGMT = System.currentTimeMillis() - TimeZone.getDefault().getRawOffset();//获取标准格林尼治时间下日期时间对应的时间戳

//        System.out.println("nowTime : " + nowTime.getTime().toString());
//        System.out.println("targetTime:" + otherTime.getTime().toString());
//        System.out.println("targetTimem:" + targetTimeMiliseconds + "       nowTimeM:" +unixTimeGMT);
//        System.out.println("offset:" + TimeZone.getDefault().getRawOffset());

        if (nowTime.get(Calendar.YEAR) != otherTime.get(Calendar.YEAR)) {
            int yearBetween = TimeBetween(nowTime, otherTime, Calendar.YEAR);
            return new DateBean(yearBetween > 0, Math.abs(yearBetween), TIME_UNIT_YEAR);
        }
        if (nowTime.get(Calendar.MONTH) != otherTime.get(Calendar.MONTH)) {
            int monthBetween = TimeBetween(nowTime, otherTime, Calendar.MONTH);
            return new DateBean(monthBetween > 0, Math.abs(monthBetween), TIME_UNIT_MONTH);
        }
        if (nowTime.get(Calendar.DAY_OF_MONTH) != otherTime.get(Calendar.DAY_OF_MONTH)) {
            int dayBetween = TimeBetween(nowTime, otherTime, Calendar.DAY_OF_MONTH);
            return new DateBean(dayBetween > 0, Math.abs(dayBetween), TIME_UNIT_DAY);
        }
        if (nowTime.get(Calendar.HOUR_OF_DAY) != otherTime.get(Calendar.HOUR_OF_DAY)) {
            int hourBetween = TimeBetween(nowTime, otherTime, Calendar.HOUR_OF_DAY);
            return new DateBean(hourBetween > 0, Math.abs(hourBetween), TIME_UNIT_HOUR);
        }
        if (nowTime.get(Calendar.MINUTE) != otherTime.get(Calendar.MINUTE)) {
            int hourBetween = TimeBetween(nowTime, otherTime, Calendar.MINUTE);
            return new DateBean(hourBetween > 0, Math.abs(hourBetween), TIME_UNIT_MINUTE);
        } else {
            return new DateBean(TIME_UNIT_SECONDS);
        }
    }

    public static long hhmmssSSToMilliSeconds(String time) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss.SSS");
        Date date = sdf.parse(time);
        Date date1 = sdf.parse("00:00:00.00");
        return date.getTime() - date1.getTime();
    }

    private static int TimeBetween(Calendar c1, Calendar c2, int filed){
        return  c1.get(filed) - c2.get(filed);
    }

    public static String formatTimeYYYYMMDD(long time) {
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(simpleDateFormat2Str);
        return simpleDateFormat2.format(new Date(time));
    }


   public static class DateBean {
       public int timeUnits;
       public long duringTime;
       public boolean isBefore;

        public DateBean(boolean isBefore, long duringTime, int timeUnits) {
            this.isBefore = isBefore;
            this.duringTime = duringTime;
            this.timeUnits = timeUnits;
        }

        public DateBean(int timeUnitMinute) {
            this.timeUnits = timeUnitMinute;
            this.isBefore = true;
        }
    }
}
