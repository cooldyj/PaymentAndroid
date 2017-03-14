package com.jerry.payment.mobile.utils;

import android.net.ParseException;
import android.text.format.Time;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间转换工具类
 * Created by jerry on 2016/8/11.
 */
public class TimeUtils {
    /**
     * 转换成日期 2016-12-12
     */
    public static String getTimeFromStamp(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return format.format(timeStamp);
    }

    /**
     * 转换成月 12
     */
    public static String getMonth(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("MM", Locale.US);
        return format.format(timeStamp);
    }

    /**
     * 转换成日 02
     */
    public static String getDay(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("dd", Locale.US);
        return format.format(timeStamp);
    }

    /**
     * 转换成时间 12:12
     */
    public static String getHourAndMinute(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.US);
        return format.format(timeStamp);
    }

    /**
     * 转换成带日期时间 2016-12-12 12:12
     */
    public static String getTimeFromStampWithHour(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.US);
        return format.format(timeStamp);
    }

    /**
     * 转换成时间 12:12:12
     */
    public static String getTimeHourMinuteAndsecond(long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss", Locale.US);
        return format.format(timeStamp);
    }


    /**
     *  如：昨天  or今天  or 09十月*
     */
    public static String getDate(long timeStamp) {

        String tag = "";

        SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String paramTime = FORMATTER.format(timeStamp);

        if (paramTime.equals(getYesterday())) {
            // zuotian
            tag = "昨天";
        } else if (paramTime.equals(getToday())) {
            // jintian
            tag = "今天";
        } else {
            tag = "DATE";
        }

        return tag;

    }


    /**
     * string类型转换为long类型
     * strTime的时间格式和formatType的时间格式必须相同
     *
     * @param strTime    要转换的String类型的时间
     * @param formatType 时间格式
     */
    public static long stringToLong(String strTime, String formatType)
            throws ParseException {
        Date date = stringToDate(strTime, formatType); // String类型转成date类型
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    /**
     * string类型转换为date类型
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param strTime    要转换的string类型的时间
     * @param formatType 要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒，
     */
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * long类型转换为String类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的string类型的时间格式
     */
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    /**
     * date类型转换为String类型
     *
     * @param data       Date类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    /**
     * long转换为Date类型
     *
     * @param currentTime 要转换的long类型的时间
     * @param formatType  要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     */
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }

    /**
     * date类型转换为long类型
     *
     * @param date 要转换的date类型的时间
     * @return long
     */
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    /**
     * 用于判断时间为当天时间
     *
     * @param seperateTime 时间戳
     * @return boolean
     */
    public static boolean isCurrentDay(long seperateTime) {
        String day = getTimeDay(seperateTime);
        String today = getToday();
        if (day.equals(today)) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前小时
     */
    public static int getCurrentHour() {
        Time t = new Time();      // or Time t=new Time("GMT+8"); 加上Time Zone资料
        t.setToNow();           // 取得系统时间。
        return t.hour;          // 0-23
    }

    /**
     * 获取某一天日期
     *
     * @param millis 毫秒
     * @return 2014-09-22
     */
    private static String getTimeDay(long millis) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String paramTime = formater.format(millis);
        return paramTime;
    }

    /**
     * 获取昨天 日期
     */
    private static String getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateStr = formater.format(date);// 获取昨天日期
        return dateStr;
    }

    /**
     * 获取前天日期
     */
    private static String getDayBeforeYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateStr = formater.format(date);// 获取昨天日期
        return dateStr;
    }

    /**
     * 获取今天日期
     */
    private static String getToday() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String dateStr = formater.format(date);// 获取今天日期
        return dateStr;
    }

    /**
     * 获取毫秒数，忽略秒数
     */
    public static long getTimeIngoreSecond() {
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        String dateStr = formater.format(date);// 获取今天日期

        return stringToLong(dateStr, "yyyy-MM-dd HH:mm");
    }

}
