package com.xiaotu.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期操作工具类
 * @author wuxincheng
 */
public class DateUtils
{
	private static final Logger LOGGER = LoggerFactory
			.getLogger(DateUtils.class);
	
	public static final String SECONDS_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String HOUR_FORMAT = "yyyy-MM-dd HH";
	public static final String DAY_FORMAT = "yyyy-MM-dd";
	public static final String MONTH_FORMAT = "yyyy-MM";
	public static final String YEAR_FORMAT = "yyyy";
	public static final String FILE_NOMENCLATURE_FORMAT = "yyyyMMdd";
	
	public static String getCurrDayString()
	{
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		return sdf.format(date);
	}
	
	public static Date getBeforeDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return date;
	}
	
	public static Date getNextDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	
	public static Date getNextMonth(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		date = calendar.getTime();
		return date;
	}
	
	public static String getBeforeDayString(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		return sdf.format(date);
	}
	
	public static String getNextDayString(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		date = calendar.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat(DAY_FORMAT);
		return sdf.format(date);
	}
	
	public static String getDateString(Date date, String type)
	{
		
		switch (type)
		{
			case SECONDS_FORMAT:
				type = SECONDS_FORMAT;
				break;
			case MINUTE_FORMAT:
				type = MINUTE_FORMAT;
				break;
			case HOUR_FORMAT:
				type = HOUR_FORMAT;
				break;
			case DAY_FORMAT:
				type = DAY_FORMAT;
				break;
			case MONTH_FORMAT:
				type = MONTH_FORMAT;
				break;
			case YEAR_FORMAT:
				type = YEAR_FORMAT;
				break;
			case FILE_NOMENCLATURE_FORMAT:
				type = FILE_NOMENCLATURE_FORMAT;
				break;
			default:
				type = DAY_FORMAT;
				break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		return sdf.format(date);
	}
	
	public static Date getDate(String date, String type)
	{
		
		switch (type)
		{
			case SECONDS_FORMAT:
				type = SECONDS_FORMAT;
				break;
			case MINUTE_FORMAT:
				type = MINUTE_FORMAT;
				break;
			case HOUR_FORMAT:
				type = HOUR_FORMAT;
				break;
			case DAY_FORMAT:
				type = DAY_FORMAT;
				break;
			case MONTH_FORMAT:
				type = MONTH_FORMAT;
				break;
			case YEAR_FORMAT:
				type = YEAR_FORMAT;
				break;
			default:
				type = DAY_FORMAT;
				break;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		Date result = null;
		try
		{
			result = sdf.parse(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return result;
	}
	
	public static String transferLongToDateString(String dateFormat,
			Long millSec)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}
	
	public static Date transferLongToDate(String dateFormat, Long millSec)
	{
		return getDate(transferLongToDateString(dateFormat, millSec),
				dateFormat);
	}
	
	/**
	 * 把 xx秒前,xx分钟前,x小时前,昨天,前天 转换成data格式
	 */
	public static Date compuTime(String str)
	{
		Calendar data = Calendar.getInstance();
		
		Pattern p = Pattern.compile("([0-9]{1,}|)([\\u4e00-\\u9fa5]{1,})");
		Matcher m = p.matcher(str);
		String disparitieStr = "";
		String compuType = "";
		Integer disparity;
		if (m.find())
		{
			disparitieStr = m.group(1);
			compuType = m.group(2);
		}
		if (disparitieStr != null && compuType.contains("秒前"))
		{
			disparity = Integer.parseInt(disparitieStr);
			data.add(Calendar.SECOND, -disparity);
		}
		else if (disparitieStr != null && str.contains("分钟前"))
		{
			disparity = Integer.parseInt(disparitieStr);
			data.add(Calendar.MINUTE, -disparity);
		}
		else if (disparitieStr != null && str.contains("小时前"))
		{
			disparity = Integer.parseInt(disparitieStr);
			data.add(Calendar.HOUR, -disparity);
		}
		else if (str.contains("昨天"))
		{
			data.add(Calendar.DATE, -1);
		}
		else if (str.contains("前天"))
		{
			data.add(Calendar.DATE, -2);
		}
		return data.getTime();
	}
	
	/**
	 * 获取前几周，或者后几周，星期几的日期， n =0 本周 ，-1 前一周， 1 后一周 @Title:
	 * WeeksOfDate @Description: 获取前几周，或者后几周，星期几的日期， n =0 本周 ，-1 前一周， 1
	 * 后一周 @author ChaoYingDi @param n @param date @return @throws
	 */
	public static String WeeksOfDate(int n, int date)
	{
		Calendar cal = Calendar.getInstance();
		// n为推迟的周数，0本周，-1向前推迟一周，1下周，依次类推
		cal.add(Calendar.DATE, n * 7);
		// 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		cal.set(Calendar.DAY_OF_WEEK, date);
		return new SimpleDateFormat(DAY_FORMAT).format(cal.getTime());
	}
	
	/**
	 * 把字符串转换成日期类型 比如: 把字符串20121212转换成日期类型
	 * @param dateString 要转换的字符串
	 * @param fomart 转换成的格式
	 * @return
	 */
	public static Date fomartStringToDate(String dateString, String fomart)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(fomart);
		Date date = null;
		try
		{
			date = sdf.parse(dateString);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 把日期转换成字符串
	 * @param date
	 * @param fomart
	 * @return
	 */
	public static String fomartDateToString(Date date, String fomart)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(fomart);
		return sdf.format(date);
	}
	
	/**
	 * 根据一个日期, 获得这个日期后的某一天的日期, 就是日期的计算 比如: 日期20121213 + 30天的日期
	 * @param date 指定日期
	 * @param formart 格式化的格式
	 * @param days 天数
	 * @return
	 */
	public static String getSpecifiedDayAfterDate(Date date, String formart,
			Integer days)
	{
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return fomartDateToString(now.getTime(), formart);
	}
	
	/**
	 * 获得当前日期时间
	 * @return
	 */
	public static String getCurrentDateTime(Date date)
	{
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sd.format(date);
	}
	
	/**
	 * 获得当前日期
	 * @return
	 */
	public static String getCurrentDate(Date date, String formart)
	{
		SimpleDateFormat sd = new SimpleDateFormat(formart);
		return sd.format(date);
	}
	
	/**
	 * 获得指定日期的前一天
	 * @param specifiedDay
	 * @param format yyyy-MM-dd / yyyy-MM-dd hh:mm:ss
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay,
			String format)
	{
		Calendar c = Calendar.getInstance();
		Date date = null;
		try
		{
			date = new SimpleDateFormat(format).parse(specifiedDay);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);
		
		String dayBefore = new SimpleDateFormat(format).format(c.getTime());
		return dayBefore;
	}
	
	/**
	 * 获得指定日期的后一天
	 * @param specifiedDay
	 * @param format yyyy-MM-dd / yyyy-MM-dd hh:mm:ss
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay,
			String format)
	{
		Calendar c = Calendar.getInstance();
		Date date = null;
		try
		{
			date = new SimpleDateFormat(format).parse(specifiedDay);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);
		
		String dayAfter = new SimpleDateFormat(format).format(c.getTime());
		return dayAfter;
	}
	
	/**
	 * 根据日期获得所在周的日期
	 * @param mdate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> dateToWeek(String dateString)
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date mdate;
		List<Date> list = new ArrayList<Date>();
		try
		{
			mdate = sdf.parse(dateString);
			int b = mdate.getDay();
			Date fdate;
			Long fTime = mdate.getTime() - b * 24 * 3600000;
			for (int a = 0; a <= 7; a++)
			{
				fdate = new Date();
				fdate.setTime(fTime + (a * 24 * 3600000));
				list.add(a, fdate);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 获得最近七天的日期
	 * @return
	 */
	public static List<Date> getRecently7Days()
	{
		List<Date> the7Days = new ArrayList<Date>();
		try
		{
			String currentDate = getCurrentDate(new Date(), "yyyy-MM-dd");
			String specDay = currentDate;
			String specBeforeDay = currentDate;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (int i = 0; i < 7; i++)
			{
				specDay = specBeforeDay;
				specBeforeDay = getSpecifiedDayBefore(specDay, "yyyy-MM-dd");
				the7Days.add(i, sdf.parse(specBeforeDay));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return the7Days;
	}
	
	/**
	 * 根据一个日期获得一个起始日期和一个结束日期
	 * @param specDay 日期
	 * @param timeStamp 时间戳
	 * @return
	 */
	public static String[] getStartAndEndTime(String specDay, String timeStamp)
	{
		String[] stratAndEndTime = new String[2];
		String specifiedDay = specDay + " " + timeStamp;
		stratAndEndTime[0] = getSpecifiedDayBefore(specifiedDay, "yyyy-MM-dd")
				+ " " + timeStamp;
		stratAndEndTime[1] = specifiedDay;
		return stratAndEndTime;
	}
	
	/**
	 * 时间格式转换成秒
	 * @param dateTime 时间格式：00:00:00.00
	 * @return 秒数，秒以下数字存在，则秒数+1
	 */
	public static String dateTimeToSec(String dateTime)
	{
		if (StringUtils.isEmpty(dateTime))
			return null;
		String[] timeArr = dateTime.split(":");
		if (timeArr.length != 3)
			return null;
		int hourSec = Integer.parseInt(timeArr[0]) * 3600;
		int minSec = Integer.parseInt(timeArr[1]) * 60;
		String[] secArr = timeArr[2].split("\\.");
		int sec = Integer.parseInt(secArr[0]);
		if (Integer.parseInt(secArr[1]) > 0)
			sec++;
		int totalSec = hourSec + minSec + sec;
		LOGGER.info("total second:" + totalSec);
		return totalSec + "";
	}
	
	public static Date getWeekFirstDate()
	{
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); // 获取本周一的日期
		return cal.getTime();
	}
	
	 /* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(SECONDS_FORMAT);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
	
	public static void main(String[] args)
	{
		System.out.println(getWeekFirstDate());
	}
	
}
