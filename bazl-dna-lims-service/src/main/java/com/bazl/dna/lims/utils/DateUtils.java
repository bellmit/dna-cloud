package com.bazl.dna.lims.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/7.
 */
public class DateUtils {

    public static final Date getCurrentDate() {
        return new Date();
    }

    public static final String YEAR_MM = "yyyyMM";
    public static final String YEAR = "yyyy";
    public static final String MONTH = "M";
    public static final String DAY = "d";
    public static final String HOUR = "H";
    public static final String MINUTES = "m";
    public static final String DATE = "yyyy-MM-dd";
    public static final String YEAR_MINUTES = "yyyy-MM-dd HH:mm";
    public static final String HOUR_MINUTES = "HH:mm";
    public static final String HOUR_MINUTES_C = "HH时mm分";
    public static final String MIN = "yyyy-MM-dd HH:mm:ss";
    public static final String HOUR_MINUTES_SECOND = "yyyyMMddHHmmss";
    /** 不自动补零 */
    public static final String DATE_SING = "yyyy-M-d";
    public static final String DATE_STR = "yyyy年MM月dd日";
    /** 不自动补零 */
    public static final String DATE_SING_STR = "yyyy年M月d日";

    public static final String dateToString(Date date, String fmt) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(date);
    }

    public static final String getCurrentYearStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(0,4);
    }

    public static final String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(0,4);
    }

    public static final String getCurrentMonthStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(5,7);
    }

    public static final String getCurrentDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(8,10);
    }

    public static final String getCurrentMonthDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateStr = sdf.format(getCurrentDate());
        return currentDateStr.substring(4);
    }

    public static Date stringToDate(String dateStr, String fmt) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.parse(dateStr);
        }catch(Exception ex){
            return null;
        }
    }

    //给date 增加或减少  days/天
    public static Date addDays(Date date, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(DateUtils.dateToString(new Date(),"yyyy年M月d日"));
        Date date = lastYearTime("2019");
        System.out.println(date);
    }
    public static Date addDate(int count) {
        try {
            Calendar calendar = Calendar.getInstance ();
            System.out.println (calendar.getTime());
            calendar.add (Calendar.SECOND, count);
            System.out.println (calendar.getTime());
            return calendar.getTime ();
        }catch(Exception ex){
            return null;
        }
    }

    /*
    *   指定年份的一年开始时间
    * */
    public static Date beginningTime(String currentYear) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentYear + "-01-01 00:00:00");
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date lastYearTime(String currentYear) {
        try {
            String currentMonthDayStr = DateUtils.getCurrentMonthDayStr();
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentYear + currentMonthDayStr);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }
    
    /*
     *   去年结束时间
     * */
    public static Date endYearTime(String currentYear) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(currentYear + "-12-31 23:59:59");
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取当前月份
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;

        return month;
    }

    /**
     * 获取当前月第一天
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int month) {
        Calendar calendar = Calendar.getInstance();
        // 设置月份
        calendar.set(Calendar.MONTH, month - 1);
        // 获取某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        calendar.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(calendar.getTime())+" 00:00:00";
    }

    /**
     * 计算相差的小时
     * @param endDate
     * @param nowDate
     * @return
     */
    public static String getDateDifference(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;

        String dateDifference = "";
        if (day > 0) {
            dateDifference += day + "天";
        }
        if (hour > 0) {
            dateDifference += hour + "小时";
        }

        return dateDifference;
    }

}
