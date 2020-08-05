/**
 * 
 */
package com.bazl.dna.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能：时间处理工具类
 * 
 * @author zhaoyong
 * 
 *         mobile enterprise application platform Version 0.1
 */
public class DateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public static final long SECOND = 1000L;
	public static final long MINUTE = 60000L;
	public static final long HOUR = 3600000L;
	public static final long DAY = 86400000L;
	public static final long WEEK = 604800000L;

	/**
	 * 
	 */
	public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 */
	public static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

	/**
	 * 东8区
	 */
	public static final String DATE_TIMEZONE = "GMT+8";

	/**
	 * 格式：年－月－日 小时：分钟：秒
	 */
	public static final String FULL_TIME_FORMAT = DEFAULT_FORMAT;

	/**
	 * 格式：年－月－日 小时：分钟
	 */
	public static final String FULL_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";

	/**
	 * 格式：年－月－日
	 */
	public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";

	private DateUtil() {

	}

	/**
	 * 格式化日期 字符串型转换成日期型
	 * 
	 * @param strDate 字符串型日期
	 * @param fm      格式化类型
	 * @return 日期型日期
	 */
	public static Date stringToDate(String strDate, String fm) {
		Date date = null;
		DateFormat df = new SimpleDateFormat(fm);
		df.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
		try {
			date = df.parse(strDate);
			return date;
		} catch (ParseException e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取当前的日期 yyyy-MM-dd
	 */
	public static String getCurrentDate() {
		return dateToString(new Date(), DEFAULT_FORMAT);
	}

	/**
	 * 格式化日期 日期型转换为字符串型
	 * 
	 * @param date 日期型日期
	 * @param fm   格式化类型
	 * @return 字符串型日期
	 */
	public static String dateToString(Date date, String fm) {
		String result = "";
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(fm);
			dateFmt.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
			result = dateFmt.format(date);
			return result;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 获得当前天开始时间 yyyy-MM-dd 00:00:00
	 * 
	 * @param date 当前时间
	 * @return Date
	 */
	public static Date getDateStartTime(String date) {
		Date d = stringToDate(date, DEFAULT_FORMAT);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 获得昨天的时间
	 * 
	 * @return
	 */
	public static Date getYestoday() {
		return truncate(getDateAgo(1), Calendar.DATE);
	}

	/**
	 * 获得前days天的这个时候
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateAgo(int days) {
		return getDateAgo(new Date(), days);
	}

	/**
	 * 获得某时间之前days天的时间
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateAgo(Date date, int days) {
		Calendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(Calendar.DATE, -days);
		return c.getTime();
	}

	/**
	 * 获得days天后的时间
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDateAfter(int days) {
		return getDateAgo(new Date(), -days);
	}

	/**
	 * 获得某个时间之后days天的时间
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getDateAfter(Date date, int days) {
		return getDateAgo(date, -days);
	}

	/**
	 * 获得本周第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfThisWeek() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK, 2);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得本周最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfThisWeek() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK, 7);
		c.add(Calendar.DATE, 1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得本月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfThisMonth() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH, 1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得本月最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfThisMonth() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		c.add(Calendar.MONTH, 1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得上周的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastWeek() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK, 1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得上周的第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfLastWeek() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_WEEK, 1);
		c.add(Calendar.DATE, -6);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得上个月的最后一天
	 * 
	 * @return
	 */
	public static Date getLastDayOfLastMonth() {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 获得上个月第一天
	 * 
	 * @return
	 */
	public static Date getFirstDayOfLastMonth() {
		Calendar c = new GregorianCalendar();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return truncate(c.getTime(), Calendar.DATE);
	}

	/**
	 * 查看两个日期是否是同一天
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean isSameDate(Date d1, Date d2) {
		return org.apache.commons.lang3.time.DateUtils.isSameDay(d1, d2);
	}

	/**
	 * 查看d1是否在d2之后
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean afterDate(Date d1, Date d2) {
		d1 = org.apache.commons.lang3.time.DateUtils.truncate(d1, Calendar.DATE);
		d2 = org.apache.commons.lang3.time.DateUtils.truncate(d2, Calendar.DATE);
		return d1.after(d2);
	}

	/**
	 * 查看d1是否在d2之前
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static boolean beforeDate(Date d1, Date d2) {
		d1 = org.apache.commons.lang3.time.DateUtils.truncate(d1, Calendar.DATE);
		d2 = org.apache.commons.lang3.time.DateUtils.truncate(d2, Calendar.DATE);
		return d1.before(d2);
	}

	/**
	 * 在两个日期之间
	 * 
	 * @param d
	 * @param from
	 * @param to
	 * @return
	 */
	public static boolean isBetweenDate(Date d, Date from, Date to) {
		return (DateUtil.afterDate(d, from) && DateUtil.beforeDate(d, to))
				|| (isSameDate(d, from) || isSameDate(d, to));
	}

	/**
	 * 获得某个日期的零值
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDate(Date date) {
		date = (date == null ? new Date() : date);
		return truncate(date, Calendar.DATE);
	}

	/**
	 * 截断日期
	 * 
	 * @param d1
	 * @param i  java.util.Calendar.DATE
	 * @return
	 */
	public static Date truncate(Date d1, int i) {
		return org.apache.commons.lang3.time.DateUtils.truncate(d1, i);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_FORMAT, Locale.CHINESE);
		df.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
		return df.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern, Locale.CHINESE);
		df.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
		return df.format(date);
	}

	/**
	 * 解析时间字符串
	 * 
	 * @param dateString
	 * @param format
	 * @return
	 */
	public static Date parse(String dateString, String format) {
		return parse(dateString, format, Locale.CHINESE, TimeZone.getDefault());
	}

	/**
	 * 解析时间字符串
	 * 
	 * @param dateString
	 * @param format
	 * @param locale
	 * @param timeZone
	 * @return
	 */
	public static Date parse(String dateString, String format, Locale locale, TimeZone timeZone) {
		SimpleDateFormat formatter = (SimpleDateFormat) DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
				locale);
		formatter.applyPattern(format);
		formatter.setTimeZone(timeZone);
		Date date = null;
		try {
			date = formatter.parse(dateString);
		} catch (Exception e) {
			date = null;
		}
		return date;
	}

	/**
	 * 获得当前时间的格式
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getCurrentTime(String pattern) {
		SimpleDateFormat ilIlI11I1 = new SimpleDateFormat(pattern);
		ilIlI11I1.setTimeZone(TimeZone.getTimeZone(DATE_TIMEZONE));
		return ilIlI11I1.format(new Date());
	}

	/**
	 * 通过时间戳得到当前时间
	 * 
	 * @param currentTimeMillis
	 * @return
	 */
	public static Date getDateByCurrentTimeMillis(long currentTimeMillis) {
		return new Timestamp(currentTimeMillis);
	}

	/**
	 * 获得两个时间的间隔天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getIntervalDays(Date begin, Date end) {
		if (begin == null || end == null)
			return 0;
		long between = (end.getTime() - begin.getTime()) / 1000;// 除以1000是为了转换成秒
		return (int) (between / (24 * 3600));
	}

	/**
	 * 获得两个时间的间隔小时
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getIntervalHours(Date begin, Date end) {
		if (begin == null || end == null)
			return 0;
		long between = (end.getTime() - begin.getTime()) / 1000;
		return (int) between / 3600;
	}

	/**
	 * 获得两个时间的间隔分钟
	 * 
	 * @param begin
	 * @param end
	 * @return
	 */
	public static int getIntervalMinites(Date begin, Date end) {
		if (begin == null || end == null)
			return 0;
		long between = (end.getTime() - begin.getTime()) / 1000;
		return (int) between / 60;
	}

	/**
	 * 获得两个时间的间隔秒
	 * 
	 * @param date
	 * @param date2
	 * @return
	 */
	public static int getIntervalSecond(Date begin, Date end) {
		if (begin == null || end == null)
			return 0;
		long between = (end.getTime() - begin.getTime()) / 1000;
		return (int) between;
	}

	/**
	 * 获得当前距离1970年的秒数
	 * 
	 * @return
	 */
	public static int getCurrentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000L);
	}

	/**
	 * 获得SQL时间
	 * 
	 * @param date
	 * @return
	 */
	public static java.sql.Date getSqlDate(Date date) {
		if (date == null)
			return null;
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 获得SQL时间戳
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp getSqlTimestamp(Date date) {
		if (date == null)
			return null;
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * Date转换Timestamp(此方法与getSqlTimestamp重复)
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * Timestamp转换Date
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Date timestampToDate(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	/**
	 * 功能描述:实现了时间加号和减号 条块: y: year m: month d: date h:hour f: minute s:second
	 * 
	 * @param date         要操作的事件
	 * @param type         y: year m: month d: date h:hour f: minute s:second
	 * @param timeInterval 时间周期
	 * @return
	 */
	public static Date computeDate(Date date, char type, int timeInterval) {
		if (date == null) {
			date = new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int timeYear = cal.get(Calendar.YEAR);
		int timeMonth = cal.get(Calendar.MONTH);
		int timeDay = cal.get(Calendar.DAY_OF_MONTH);
		int timeHour = cal.get(Calendar.HOUR_OF_DAY);
		int timeMinute = cal.get(Calendar.MINUTE);
		int timeSecond = cal.get(Calendar.SECOND);

		switch (type) {
		case 'y':
			timeYear = timeYear + timeInterval;
			cal.set(Calendar.YEAR, timeYear);
			break;

		case 'm':
			timeMonth = timeMonth + timeInterval;
			cal.set(Calendar.MONTH, timeMonth);
			break;

		case 'd':
			timeDay = timeDay + timeInterval;
			cal.set(Calendar.DAY_OF_MONTH, timeDay);
			break;

		case 'h':
			timeHour = timeHour + timeInterval;
			cal.set(Calendar.HOUR_OF_DAY, timeHour);
			break;

		case 'f':
			timeMinute = timeMinute + timeInterval;
			cal.set(Calendar.MINUTE, timeMinute);
			break;

		case 's':
			timeSecond = timeSecond + timeInterval;
			cal.set(Calendar.SECOND, timeSecond);
			break;
		default:
			break;
		}
		date = cal.getTime();
		return date;
	}

	/**
	 * 获取东八区时间
	 * 
	 * @return
	 */
	public static Date getGMT8Time() {
		Date gmt8 = null;
		try {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(DATE_TIMEZONE), Locale.CHINESE);
			Calendar day = Calendar.getInstance();
			day.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			day.set(Calendar.MONTH, cal.get(Calendar.MONTH));
			day.set(Calendar.DATE, cal.get(Calendar.DATE));
			day.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY));
			day.set(Calendar.MINUTE, cal.get(Calendar.MINUTE));
			day.set(Calendar.SECOND, cal.get(Calendar.SECOND));
			gmt8 = day.getTime();
		} catch (Exception e) {
			LOGGER.error("Error getGMT8Time:", e);
		}
		return gmt8;
	}

	/**
	 * 在某时间之后的时间
	 * 
	 * @param date   指定时间
	 * @param amount 指定数值 推后1单位
	 * @param unit   指定推后单位(Calendar.MONTH 月、Calendar.WEEK_OF_YEAR 周、Calendar.DATE
	 *               日)
	 * @return
	 */
	public static Date after(Date date, int amount, int unit) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.add(unit, amount);
		return gc.getTime();
	}

	/**
	 * 转换时间
	 * 
	 * @param date
	 * @return
	 */
	public static LocalDateTime dateToLocalDateTime(Date date) {
		if (date != null) {
			LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
			LocalDateTime time = LocalDateTime.of(1800, 7, 12, 15, 12, 12);
			if (localDateTime.isBefore(time)) { // 判断是否在time时间之前
				localDateTime = localDateTime.withYear(2015);
			}
			return localDateTime;
		}
		return null;
	}

	/**
	 * 转换时间
	 * 
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		if (localDateTime != null) {
			return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		}
		return null;
	}

}