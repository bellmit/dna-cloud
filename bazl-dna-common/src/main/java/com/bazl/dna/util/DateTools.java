package com.bazl.dna.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * 日期工具类
 * @author lt
 */
public class DateTools {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DateTools.class);
	
	/**
	 * 毫秒
	 */
	public static final int MILLI_SECOND = 1000;
	
	/**
	 * 秒
	 */
	public static final int SECOND = 1;
	
	/**
	 * 分的秒数
	 */
	public static final int MINUTE_SECONDS = SECOND * 60;
	
	/**
	 * 小时的秒数
	 */
	public static final int HOUR_SECONDS = MINUTE_SECONDS * 60;
	
	/**
	 * 天的小时
	 */
	public static final int DAY_HOUR = 24;
	
	/**
	 * 天的秒数
	 */
	public static final int DAY_SECONDS = HOUR_SECONDS * DAY_HOUR;
	
	/**
	 * 年的秒数
	 */
	public static final int YEAR_SECONDS = DAY_SECONDS * 365;
	
	/**
	 * 最大 小时
	 */
	public static final int MAX_HOUR = 23;
	
	/**
	 * 最大 分
	 */
	public static final int MAX_MINUTE = 59;
	
	/**
	 * 最大 秒
	 */
	public static final int MAX_SECOND = 59;
	
	
	
	/**
	 * 数字处理
	 */
	private static final int NUMBER2 = 2;
	private static final int NUMBER4 = 4;
	private static final int NUMBER5 = 5;
	private static final int NUMBER7 = 7;
	private static final int NUMBER8 = 8;
	private static final int NUMBER9 = 9;
	private static final int NUMBER10 = 10;
	private static final int NUMBER19 = 19;
	private static final int NUMBER20 = 20;
	private static final int NUMBER21 = 21;
	private static final int NUMBER22 = 22;
	private static final int NUMBER23 = 23;
	
	private static final int YEAR1900 = 1900;
	
	/**
     * 格式：年月日小时分钟秒
     */
	public static final String FULL_TIME = "yyyyMMddHHmmss";
	
	/**
     * 格式：年－月－日 小时：分钟：秒
     */
    public static final String FULL_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 格式：年－月－日 小时：分钟
     */
    public static final String FULL_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    
    /**
     * 格式：年－月－日
     */
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd";
    
    /**
     * 格式：年－月－日 小时:分钟:秒:毫秒
     */
    public static final String FULL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    
    /**
     * 格式：年－月－日 23:59:59:999
     */
    public static final String FULL_DATE_END_TIME_FORMAT = "yyyy-MM-dd 23:59:59:999";

    /**
     * 格式：小时：分钟：秒
     */
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    
    /**
     * 星期名称
     */
    private static final String[] WEEK_NAME = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

    private DateTools() {
    }

	/**
	 * 格式化日期 字符串型转换成日期型
	 * 
	 * @param strDate 	字符串型日期
	 * @param fm			格式化类型
	 * @return 日期型日期
	 */
	public static Date stringToDate(String strDate, String fm) {
		Date date = null;
		DateFormat df = new SimpleDateFormat(fm);
		try {
			date = df.parse(strDate);
		} catch (ParseException e) {
			LOGGER.error("Error stringToDate:", e);
		}
		return date;
	}

	/**
	 * 格式化日期 日期型转换为字符串型
	 * 
	 * @param date 	日期型日期
	 * @param fm 	格式化类型
	 * @return 字符串型日期
	 */
	public static String dateToString(Date date, String fm) {
		String result = "";
		try {
			SimpleDateFormat dateFmt = new SimpleDateFormat(fm);
			result = dateFmt.format(date);
		} catch (Exception e) {
			LOGGER.error("Error dateToString:", e);
		}
		return result;
	}
	
	/**
     * 获取当前时间的指定格式
     * 
     * @param format
     * @return
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }
    
    /**
     * 比较两个日期的年差
     * 
     * @param startDay
     * @param endDay
     * @return 年数
     */
    public static int subYear(String startDate, String endDate) {
        Date beforeDay = stringToDate(startDate, FULL_DATE_FORMAT);
        Date afterDay = stringToDate(endDate, FULL_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }
    
    /**
     * 比较两个日期的月差
     * @param startDate
     * @param endDate
     * @return 月数
     */
    public static int subMonth(String startDate, String endDate) {
    	int result = 0;    
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateTools.stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(DateTools.stringToDate(endDate, FULL_DATE_FORMAT)); 
        while (!c1.after(c2)) {  
        	result++;
        	c1.add(Calendar.MONTH, 1);
        }
        result = result-1;
		return result;
	}
    
    /**
	 * 比较两个日期的日差
	 * 
	 * @param startDate 	yyyy-MM-dd 开始日期
	 * @param endDate 	yyyy-MM-dd 结束日期
	 * @return 日期天数
	 */
	public static long subDay(String startDate, String endDate) {
		Date dt1 = stringToDate(startDate, FULL_DATE_FORMAT);
		Date dt2 = stringToDate(endDate, FULL_DATE_FORMAT);
		if (dt1 != null && dt2 != null) {
			long l = dt2.getTime() - dt1.getTime();
			return l / MINUTE_SECONDS / MINUTE_SECONDS / MILLI_SECOND / DAY_HOUR;
		}
		return 0L;
	}
	
	/**
     * 计算日期时间差
     * 
     * @param startTime
     * @param endTime
     * @return 日期秒数
     */
    public static long subTime(String startTime, String endTime) {
    		Date dt1 = stringToDate(startTime, FULL_TIME_FORMAT);
		Date dt2 = stringToDate(endTime, FULL_TIME_FORMAT);
		if (dt1 != null && dt2 != null) {
			long l = dt2.getTime() - dt1.getTime();
			return l / MILLI_SECOND;
		}
		return 0L;
    }
    
    /**
     * 计算日期时间差
     * @param millSeconds
     * @param format
     * @return
     */
    public static String subTimeString(long millSeconds, String format) {
    	String result = null;
		if (millSeconds <= 0) {
			result = StringUtils.EMPTY;
		} else {
			long day = millSeconds / (DAY_SECONDS * MILLI_SECOND);
			long hour = millSeconds / (HOUR_SECONDS * MILLI_SECOND) - day * DAY_HOUR;
			long minute = (millSeconds / (MINUTE_SECONDS * MILLI_SECOND)) - day * DAY_HOUR * MINUTE_SECONDS - hour * MINUTE_SECONDS;
			long second = millSeconds / MILLI_SECOND - day * DAY_SECONDS - hour * HOUR_SECONDS - minute * MINUTE_SECONDS;
			
			result = subTimeString(day, hour, minute, second, format);
		}
		return result;
    }
    
    /**
     * 计算日期时间差
     * @param day
     * @param hour
     * @param min
     * @param sec
     * @param format
     * @return
     */
    private static String subTimeString(long day, long hour, long minute, long second, String format) {
    	StringBuilder result = new StringBuilder();
		if (day > 0 && format.contains("dd")) {
			result.append(day + "天");
		}
		if (hour > 0 && format.contains("HH")) {
			result.append(hour + "小时");
		}
		if (minute > 0 && format.contains("mm")) {
			result.append(minute + "分钟");
		}
		if (second > 0 && format.contains("ss")) {
			result.append(second + "秒");
		}
		return result.toString();
    }

	/**
	 * 字符串日期转换成中文格式日期
	 * 
	 * @param date	字符串日期 yyyy-MM-dd
	 * @return 	yyyy年MM月dd日
	 * @throws Exception
	 */
	public static String dateToCnDate(String date) {
		StringBuilder result = new StringBuilder();
		String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String ten = "十";
		String[] cnDay = new String[] {"年", "月", "日"};
		String[] dateStr = date.split("-");
		for (int i = 0; i < dateStr.length; i++) {
			
			for (int j = 0; j < dateStr[i].length(); j++) {
				String charStr = dateStr[i];
				String str = String.valueOf(charStr.charAt(j));
				if (charStr.length() == NUMBER2) {
					if (charStr.equals(String.valueOf(NUMBER10))) {
						result.append(ten);
						break;
					} else {
						if (j == 0) {
							if (charStr.charAt(j) == '1') {
								result.append(ten);
							} else if (charStr.charAt(j) == '0') {
								result.append(StringUtils.EMPTY);
							} else {
								result.append(cnDate[Integer.parseInt(str)] + ten);
							}
						}
						if (j == 1) {
							if (charStr.charAt(j) == '0') {
								result.append(StringUtils.EMPTY);
							} else {
								result.append(cnDate[Integer.parseInt(str)]);
							}
						}
					}
				} else {
					result.append(cnDate[Integer.parseInt(str)]);
				}
			}
			
			result.append(cnDay[i]);
		}
		return result.toString();
	}

    /**
     * 获取某年某月的天数
     * 
     * @param year
     * @param month
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     * 
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     * 
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     * 
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     * 
     * @param date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     * 
     * @param date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     * 
     * @param date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取每月的第一周
     * @param year
     * @param month
     * @return int
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     * @param year
     * @param month
     * @return int
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 根据生日获取星座
     * 
     * @param birth yyyy-MM-dd
     * @return String
     */
    public static String getAstro(String birth) {
    	String birthName = birth;
        if (!isDate(birth)) {
        	birthName += "2000";
        }
        if (!isDate(birthName)) {
            return "";
        }
        int month = Integer.parseInt(birthName.substring(birthName.indexOf('-') + 1, birthName.lastIndexOf('-')));
        int day = Integer.parseInt(birthName.substring(birthName.lastIndexOf('-') + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arr = { NUMBER20, NUMBER19, NUMBER21, NUMBER21, NUMBER21,
				NUMBER22, NUMBER23, NUMBER23, NUMBER23, NUMBER23, NUMBER22,
				NUMBER22 };
        int start = month * NUMBER2 - (day < arr[month - 1] ? NUMBER2 : 0);
        return s.substring(start, start + NUMBER2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     * 
     * @param date yyyy-MM-dd
     * @return boolean
     */
    public static boolean isDate(String date) {
        StringBuilder reg = new StringBuilder (
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }
    
    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * 
     * @param date 日期 为null时表示当天
     * @param month 相加(相减)的月数
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     * @param day
     * @param format
     * @return String
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 week 周后的日期 (当 week 为负数表示指定月之前)
     * 
     * @param date 日期 为null时表示当天
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }
    
    /**
     *  取得指定日期过 minutes 分钟后的日期 (当 分钟 为负数表示指定分钟之前)
     * @param date
     * @param minutes
     * @return
     */
    public static Date nextMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }
    
    /**
     *  取得指定日期过 hour 小时后的日期 (当 小时 为负数表示指定分钟之前)
     * @param date
     * @param hour
     * @return
     */
    public static Date nextHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }
    
    /**
     * 根据日期获得下周一的日期
     * @param week
     * @return
     */
	public static Date getNextWeekFirstDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
		cal.add(Calendar.DAY_OF_WEEK, (NUMBER9 - cal.get(Calendar.DAY_OF_WEEK)) % NUMBER7);
        return cal.getTime();
    }

    /**
     * 获取当前的日期 yyyy-MM-dd
     */
    public static String getCurrentDate() {
        return dateToString(new Date(), FULL_DATE_FORMAT);
    }
    /**
     * 获取当前的时间 yyyy-MM-dd HH:mm:ss 
     */
    public static String getCurrentTime() {
    	return dateToString(new Date(), FULL_TIME_FORMAT);
    }
    
    /**
     * 获得当前天开始时间 yyyy-MM-dd 00:00:00
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateStartTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获得当前天结束时间 yyyy-MM-dd 23:59:59
     * @param date 当前时间
     * @return Date
     */
    public static Date getDateEndTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前小时开始时间
     * @param date
     * @return Date
     */
    public static Date getHourStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前小时结束时间
     * @param date
     * @return Date
     */
    public static Date getHourEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前10分钟开始时间
     * @param date
     * @return Date
     */
    public static Date getTenMinuteStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/NUMBER10*NUMBER10);
    	cal.set(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前10分钟结束时间
     * @param date
     * @return Date
     */
    public static Date getTenMinuteEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.add(Calendar.MINUTE, NUMBER10);
    	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/NUMBER10*NUMBER10);
    	cal.set(Calendar.SECOND, 0);
    	cal.add(Calendar.SECOND, -1);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前周开始时间
     * @param date
     * @return Date
     */
    public static Date getWeekStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前周结束时间
     * @param date
     * @return Date
     */
    public static Date getWeekEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);  
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前月开始时间
     * @param date
     * @return Date
     */
    public static Date getMonthStartTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DATE, 1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取当前月结束时间
     * @param date
     * @return Date
     */
    public static Date getMonthEndTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DATE, 1);  
        cal.roll(Calendar.DATE, -1);  
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前年开始时间
     * @param date
     * @return Date
     */
    public static Date getYearStartTime(String date) {
    	Date d = stringToDate(date, "yyyy");
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取当前年结束时间
     * @param date
     * @return Date
     */
    public static Date getYearEndTime(String date) {
    	Date d = stringToDate(date, "yyyy");
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.roll(Calendar.DAY_OF_YEAR, -1); 
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上个10分钟开始时间
     * @param date
     * @return Date
     */
    public static Date getLastTenMinuteStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.add(Calendar.MINUTE, -NUMBER10);
    	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/NUMBER10*NUMBER10);
    	cal.set(Calendar.SECOND, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上个10分钟结束时间
     * @param date
     * @return Date
     */
    public static Date getLastTenMinuteEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)/NUMBER10*NUMBER10);
    	cal.set(Calendar.SECOND, 0);
    	cal.add(Calendar.SECOND, -1);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上个小时开始时间
     * @param date
     * @return Date
     */
    public static Date getLastHourStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上个小时结束时间
     * @param date
     * @return Date
     */
    public static Date getLastHourEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 1);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获得昨天开始时间 yyyy-MM-dd 00:00:00
     * @param date 当前时间
     * @return Date
     */
    public static Date getLastDateStartTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获得昨天结束时间 yyyy-MM-dd 23:59:59
     * @param date 当前时间
     * @return Date
     */
    public static Date getLastDateEndTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上周开始时间
     * @param date
     * @return Date
     */
    public static Date getLastWeekStartTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
    	cal.set(Calendar.DATE, cal.get(Calendar.DATE) - NUMBER7);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上周结束时间
     * @param date
     * @return Date
     */
    public static Date getLastWeekEndTime(String date) {
    	Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.setFirstDayOfWeek(Calendar.MONDAY);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.add(Calendar.SECOND, -1);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上月开始时间
     * @param date
     * @return Date
     */
    public static Date getLastMonthStartTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取上月结束时间
     * @param date
     * @return Date
     */
    public static Date getLastMonthEndTime(String date) {
    	Date d = stringToDate(date, FULL_DATE_FORMAT);
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.DATE, 1);  
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        cal.roll(Calendar.DATE, -1);
        return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上一年开始时间
     * @param date
     * @return Date
     */
    public static Date getLastYearStartTime(String date) {
    	Date d = stringToDate(date, "yyyy");
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
    	return new Date(cal.getTimeInMillis());
    }
    
    /**
     * 获取上一年结束时间
     * @param date
     * @return Date
     */
    public static Date getLastYearEndTime(String date) {
    	Date d = stringToDate(date, "yyyy");
    	Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, MAX_HOUR);
        cal.set(Calendar.SECOND, MAX_SECOND);
        cal.set(Calendar.MINUTE, MAX_MINUTE);
        cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
        cal.roll(Calendar.DAY_OF_YEAR, -1); 
    	return new Date(cal.getTimeInMillis());
    }

    /**
     * 获取昨天的日期
     * 
     * @return
     */
    public static String getYesterday() {
        return yesterday(FULL_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     * @param format
     * @return
     */
    public static String yesterday(String format) {
        return dateToString(nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     */
    public static String getNextDay() {
        return dateToString(nextDay(new Date(), 1), FULL_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     * 
     * @return
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(YEAR1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (DAY_SECONDS * MILLI_SECOND));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     * 
     * @param day
     * @return
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(YEAR1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /**
     * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd
     * @param datestr
     * @return
     */
    public static String getYmdDateCN(String datestr) {
        if (datestr != null) {
        	if (datestr.length() < NUMBER10) {
                return "";
        	}
        	StringBuilder buf = new StringBuilder();
            buf.append(datestr.substring(0, NUMBER4)).append(datestr.substring(NUMBER5, NUMBER7))
                    .append(datestr.substring(NUMBER8, NUMBER10));
            return buf.toString();
        }
        return "";
    }

    /**
     * 获取日期的星期
     * @param date
     * @return String
     */
    public static String getWeekByDate(Date date) {
    	Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        
        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
        	week = 0;
        }
        return WEEK_NAME[week];
    }
    
    /**
     * 获取timestamp的星期
     * @param time
     * @return String
     */
	public static String getWeekByTimestamp(Timestamp time) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(time.getTime());
    	
    	int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) {
        	week = 0;
        }
        return WEEK_NAME[week];
    }
    
    /**
     * 获得日期列表
     * @param resultList	定义好的集合
     * @param startDate	开始日期
     * @param endDate		结束日期
     * @return
     */
    public static List<String> getDialectDate(String startDate, String endDate) {
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(DateTools.stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(DateTools.stringToDate(endDate, FULL_DATE_FORMAT)); 
        List<String> resultList = Lists.newArrayList();
        while (!c1.after(c2)) {  
        	resultList.add(dateToString(c1.getTime(), FULL_DATE_FORMAT));
        	c1.add(Calendar.DATE, 1);
        }
		return resultList;
    }
    /**
     * 获得两个日期间的周 
     * @param startDate	开始日期
     * @param endDate		结束日期
     * @return 周开始时间和结束时间的list<map>
     */
    public static List<Map<String,String>> getDialectWeek(String startDate, String endDate){
    	final String startString = "start";
    	final String endString = "end";
    	Calendar c1 = Calendar.getInstance();    
        Calendar c2 = Calendar.getInstance();
        c1.setTime(stringToDate(startDate, FULL_DATE_FORMAT));    
        c2.setTime(stringToDate(endDate, FULL_DATE_FORMAT)); 
        
        List<Map<String,String>> resultList = Lists.newArrayList();
		while (c1.compareTo(c2) < 0) {
			Map<String,String> map = Maps.newHashMap();
			String date = dateToString(c1.getTime(), FULL_DATE_END_TIME_FORMAT);
			map.put(startString, dateToString(getDateStartTime(dateToString(stringToDate(date, FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			c1.add(Calendar.DAY_OF_YEAR, -c1.get(Calendar.DAY_OF_WEEK) + NUMBER8);
			if (c1.compareTo(c2) > 0) {
				map.put(endString, dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
				break;
			}
			date = dateToString(c1.getTime(), FULL_DATE_END_TIME_FORMAT);
			map.put(endString, dateToString(getDateEndTime(dateToString(stringToDate(date, FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			c1.setTime(addDateByDay(c1.getTime(),1));
			resultList.add(map);
		}
		
		Calendar lastEndTime =  Calendar.getInstance();
		lastEndTime.setTime(addDateByDay(stringToDate(resultList.get(resultList.size()-1).get("end"),FULL_DATE_FORMAT),1));
		if (lastEndTime.compareTo(c2) < 0) {
			Map<String,String> map = Maps.newHashMap();
			map.put(startString, dateToString(getDateStartTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			map.put(endString, dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			resultList.add(map);
		} else if (lastEndTime.compareTo(c2) == 0) {
			Map<String,String> map = Maps.newHashMap();
			map.put(startString, dateToString(getDateStartTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			map.put(endString, dateToString(getDateEndTime(dateToString(stringToDate(dateToString(lastEndTime.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
			resultList.add(map);
		} else {
			resultList.get(resultList.size()-1).put(endString,dateToString(getDateEndTime(dateToString(stringToDate(dateToString(c2.getTime(), FULL_DATE_END_TIME_FORMAT), FULL_DATE_TIME_FORMAT),FULL_DATE_FORMAT)),FULL_TIME_FORMAT));
		}
    	return resultList;
    }
    /**
	 * 给日期累加指定天数并返回累加后的日期
	 * @param time 日期参数
	 * @param add_day 累加天数
	 * @return 累加后的日期
	 */
	public static Date addDateByDay(Date time, int addDay) {
		if (time == null) {
			return new Date();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.DAY_OF_YEAR, addDay);
		return cal.getTime();
	}
	
	/**
	 * 获取查询日期以前的日期
	 * @param date
	 * @param calProp
	 * @return
	 */
	public static String getBefore(String date,int calProp){
		Date d = stringToDate(date, FULL_TIME_FORMAT);
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.add(calProp, -1);
        return dateToString(new Date(cal.getTimeInMillis()), FULL_TIME_FORMAT) ;
	}
	
}
