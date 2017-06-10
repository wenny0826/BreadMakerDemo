package com.wenny.breadmakerdemo;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wenny on 2017/2/15.
 */

public class TimeUtil {
    /**
     * 指定日期格式 yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_1 = "yyyyMMddHHmmss";

    /**
     * 指定日期格式 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

    /**
     * 指定日期格式 yyyy-MM-dd'T'HH:mm:ssZ
     */
    public static final String DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * 指定日期格式 yyyy-MM-dd
     */
    public static final String DATE_FORMAT_4 = "yyyy-MM-dd";

    /**
     * 指定日期格式 yyyy.M.d
     */
    public static final String DATE_FORMAT_5 = "yyyy.M.d";

    /**
     * 指定日期格式yyyy-MM-dd HH:mm
     */
    public static final String DATE_FORMAT_6 = "yyyy-MM-dd HH:mm";

    /**
     * 指定日期格式HH:mm
     */
    public static final String DATE_FORMAT_7 = "HH:mm";

    /**
     * 指定日期格式MM-dd HH:mm
     */
    public static final String DATE_FORMAT_8 = "MM-dd HH:mm";

    /**
     * 指定日期格式HH:MM:SS
     */
    public static final String DATE_FORMAT_9 = "HH:MM:SS";

    /**
     * 指定日期格式HH:mm
     */
    public static final String DATE_FORMAT_10 = "MM-dd";

    /**
     * 指定日期格式yy-MM-dd HH:mm
     */
    public static final String DATE_FORMAT_11 = "yy-MM-dd HH:mm";

    /**
     * 指定日期格式MM月dd日
     */
    public static final String DATE_FORMAT_12 = "M月d日";
    /**
     * 日期排序类型-升序
     */
    public final static int DATE_ORDER_ASC = 0;

    /**
     * 日期排序类型-降序
     */
    public final static int DATE_ORDER_DESC = 1;

    public static String[] WEEK = new String[]{"天", "一", "二", "三", "四", "五", "六"};

    private static final long ONE_SECOND = 1000;
    private static final long ONE_MINUTE = ONE_SECOND * 60;
    private static final long ONE_HOUR = ONE_MINUTE * 60;
    private static final long ONE_DAY = ONE_HOUR * 24;
    //判断是否为同一天
    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 根据时间返回日期，今年返回 HH月mm日
     * 往年返回 yyyy.MM.dd
     *
     * @param time
     * @return
     */
    public static String getDates(long time){
        Date now = new Date();
        Date date = new Date(time);
        if(now.getYear() == date.getYear()){
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_12);
            return sdf.format(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_5);
            return sdf.format(date);
        }
    }

    /**
     * String 转换 Date
     *
     * @param str
     * @param format
     * @return
     */
    public static Date string2Date(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * Date（long） 转换 String
     *
     * @param time
     * @param format
     * @return
     */
    public static String date2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String s = sdf.format(time);
        return s;
    }

    /**
     * long 去除 时分秒
     * 时分秒全部为0
     *
     * @param date
     * @return
     */
    public static long getYearMonthDay(long date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }


    /**
     * Date 转换 HH
     *
     * @param date
     * @return
     */
    public static String date2HH(Date date) {
        return new SimpleDateFormat("HH").format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmm(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    /**
     * Date 转换 HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String date2HHmmss(Date date) {
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }

    /**
     * Date 转换 MM.dd
     *
     * @param date
     * @return
     */
    public static String date2MMdd(Date date) {
        return new SimpleDateFormat("MM.dd").format(date);
    }

    /**
     * Date 转换 yyyy.MM.dd
     *
     * @param date
     * @return
     */
    public static String date2yyyyMMdd(Date date) {
        return new SimpleDateFormat("yyyy.MM.dd").format(date);
    }

    /**
     * Date 转换 MM月dd日 星期
     *
     * @param date
     * @return
     */
    public static String date2MMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }

    /**
     * Date 转换 yyyy年MM月dd日 星期
     *
     * @param date
     * @return
     */
    public static String date2yyyyMMddWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        return new SimpleDateFormat("yyyy年MM月dd日 星期").format(date) + WEEK[dayOfWeek - 1];
    }
    /**
     * 转化时间输入时间与当前时间的间隔
     *
     * @param timestamp
     * @return
     */
    public static String converTime(long timestamp) {
        long currentSeconds = System.currentTimeMillis() / 1000;
        long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
        String timeStr = null;
        if (timeGap > 24 * 60 * 60) {// 1天以上
            timeStr = timeGap / (24 * 60 * 60) + "天前";
        } else if (timeGap > 60 * 60) {// 1小时-24小时
            timeStr = timeGap / (60 * 60) + "小时前";
        } else if (timeGap > 60) {// 1分钟-59分钟
            timeStr = timeGap / 60 + "分钟前";
        } else {// 1秒钟-59秒钟
            timeStr = "刚刚";
        }
        return timeStr;
    }
    /**
     * 获得天数差
     * @param begin
     * @param end
     * @return
     */
    public static int getDayDiff(long begin, long end){
        int day = 1;
        if(end < begin){
            day = -1;
        }else if(end== begin){
            day = 1;
        }else {
            day += (end- begin)/(24 * 60 * 60 * 1000) ;
        }
        return day;
    }
    /**
     * 返回美国时间格式 26 Apr 2006
     *
     * @param str
     * @return
     */
    public static String getEDate(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(  0);
        Date strtodate = formatter.parse(str, pos);
        String j = strtodate.toString();
        String[] k = j.split(" ");
        return k[  2] + k[  1].toUpperCase() + k[  5].substring(  2,   4);
    }

    /**
     * 产生周序列,即得到当前时间所在的年度是第几周
     *
     * @return
     */
    public static String getSeqWeek() {
        Calendar c = Calendar.getInstance(Locale.CHINA);
        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
        if (week.length() == 1)
            week = "0" + week;
        String year = Integer.toString(c.get(Calendar.YEAR));
        return year + week;
    }

}
