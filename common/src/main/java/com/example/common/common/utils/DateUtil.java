package com.example.common.common.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * 2017
 * 04
 * 2017/4/25
 * wangxiaoer
 * 功能描述：时间转化工具类
 **/
@SuppressWarnings("ResourceType")
public class DateUtil {
    public static String[] WEEK = new String[]{"天", "一", "二", "三", "四", "五",
            "六"};
    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;

    public static Date addDays(Date paramDate, int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.add(5, paramInt);
        return localCalendar.getTime();
    }

    public static Date addMonths(Date paramDate, int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.add(2, paramInt);
        return localCalendar.getTime();
    }

    public static int between(Date paramDate) {
        return (int) ((new Date().getTime() - paramDate.getTime()) / 86400000L);
    }

    public static int between(Date paramDate1, Date paramDate2) {
        return (int) ((paramDate1.getTime() - paramDate2.getTime()) / 86400000L);
    }

    public static Date defaultTargetDate() {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(new Date());
        localCalendar.set(2, 1 + localCalendar.get(2));
        localCalendar.set(5, -1 + localCalendar.get(5));
        return localCalendar.getTime();
    }

    public static String format(Date paramDate) {
        return format(paramDate, "yyyy-MM-dd");
    }

    public static String format(Date paramDate, String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                paramString, Locale.getDefault());
        String str1;
        try {
            String str2 = localSimpleDateFormat.format(paramDate);
            str1 = str2;
            return str1;
        } catch (Exception localException) {
            str1 = localSimpleDateFormat.format(new Date());
        }
        return null;
    }

    public static String formatString(String paramString1, String paramString2) {
        Date localDate = parseFromString(paramString1, "yyyy-MM-dd");
        return new SimpleDateFormat(paramString2, Locale.getDefault())
                .format(localDate);
    }

    public static String getCurrentDateTime() {
        return format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String getCurrentDateTime2() {
        return format(Calendar.getInstance().getTime(), "yyyy-MM-dd");
    }

    public static String getCurrentTimeMills() {
        return format(Calendar.getInstance().getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    public static int getDay(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        return localCalendar.get(5);
    }

    public static Date getFirstDay(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.set(7, localCalendar.getFirstDayOfWeek());
        return localCalendar.getTime();
    }

    public static Date getFirstDay(Date paramDate, int paramInt) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.set(7, paramInt);
        return localCalendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.set(5, 1);
        return localCalendar.getTime();
    }

    public static Date getLastDayOfMonth(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        localCalendar.set(5, localCalendar.getActualMaximum(5));
        return localCalendar.getTime();
    }

    public static int getMonth(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        return 1 + localCalendar.get(2);
    }

    public static String getWeekOfDate(Date paramDate, Context paramContext) {
        String[] arrayOfString = paramContext.getResources().getStringArray(
                2131558406);
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        return arrayOfString[(-1 + localCalendar.get(7))];
    }

    public static int getYear(Date paramDate) {
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(paramDate);
        return localCalendar.get(1);
    }

    public static String getYearMonth(Date paramDate) {
        // StringBuilder localStringBuilder = new StringBuilder(
        // String.valueOf(getYear(paramDate)));
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(getMonth(paramDate));
        return String.format("%02d", arrayOfObject);
    }

    public static String monthDay() {
        return format(new Date(), "MMMdd日");
    }

    public static String monthDay(Date paramDate) {
        return format(paramDate, "MMMdd日");
    }

    public static Date parseFromString(String paramString1, String paramString2) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                paramString2, Locale.getDefault());
        Date localDate1;
        try {
            Date localDate2 = localSimpleDateFormat.parse(paramString1);
            localDate1 = localDate2;
            return localDate1;
        } catch (ParseException localParseException) {
            localDate1 = null;
        }
        return null;
    }

    public static Date parseString(String paramString) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date localDate1;
        try {
            Date localDate2 = localSimpleDateFormat.parse(paramString);
            localDate1 = localDate2;
            return localDate1;
        } catch (ParseException localParseException) {
            localDate1 = null;
        }
        return null;
    }

    public static String timezoneFormat(String paramString1, String paramString2) {
        Date localDate = parseFromString(paramString1, "yyyy-MM-dd'T'HH:mm:ss");
        return new SimpleDateFormat(paramString2, Locale.getDefault())
                .format(localDate);
    }

    // public static int week(Date paramDate) throws Throwable {
    // GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    // localGregorianCalendar.set(paramDate.getYear(), paramDate.getMonth(),
    // paramDate.getDay());
    // return localGregorianCalendar.get(7);
    // }

    private static SimpleDateFormat sf = null;

    /**
     * 时间戳转换成字符窜
     */
    public static String getDateToString(long time) {
        Date d = new Date(time);
//		sf = new SimpleDateFormat("yyyy-MM-dd");
        sf = new SimpleDateFormat("MM-dd HH:mm");
        return sf.format(d);
    }

    public static String getDateToString2(long time) {
//		time = time * 1000;
//		Date d = new Date(time);
//		sf = new SimpleDateFormat("yyyy-MM");
//		return sf.format(d);
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sf.format(d);
    }

    // 一天的毫秒数 86400000 = 24*60*60*1000;
    private static final int millisPerDay = 86400000;
    // 一小时的毫秒数 3600000 = 24*60*60*1000;
    private static final int millisPerHour = 3600000;

    /**
     * 计算时间差 (时间单位,开始时间,结束时间) 调用方法
     * howLong("h","2007-08-09 10:22:26","2007-08-09 20:21:30") ///9小时56分 返回9小时
     */
    public static long howLong(String unit, String time1, String time2)
            throws ParseException {
        // 时间单位(如：不足1天(24小时) 则返回0)，开始时间，结束时间
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time1);
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time2);
        long ltime = date1.getTime() - date2.getTime() < 0 ? date2.getTime()
                - date1.getTime() : date1.getTime() - date2.getTime();
        if (unit.equals("s")) {
            return ltime / 1000;// 返回秒
        } else if (unit.equals("m")) {
            return ltime / 60000;// 返回分钟
        } else if (unit.equals("h")) {
            return ltime / millisPerHour;// 返回小时
        } else if (unit.equals("d")) {
            return ltime / millisPerDay;// 返回天数
        } else {
            return 0;
        }
    }

    /* 将字符串转为时间戳 */
    public static long getStringToDate(String time) {
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static long getStringToDate2(String time) {
        sf = new SimpleDateFormat("yyyy年MM月");
        Date date = new Date();
        try {
            date = sf.parse(time);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String getCurrentHour() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH");
        String date = sdf.format(new java.util.Date());
        return date;
    }

    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String date = sdf.format(new java.util.Date());
        return date;
    }

    public static String getPostMessageTome(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(Long.parseLong(time));
        return date;
    }

    public static String getyyyyMMddHHmm(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(new Date(Long.parseLong(timeStr)));
    }
}
