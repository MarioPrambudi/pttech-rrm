package com.djavafactory.pttech.rrm.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.djavafactory.pttech.rrm.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date Utility Class used to convert Strings to Dates and Timestamps
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by 
 *         <a href="mailto:dan@getrolling.com">Dan Kibler
 *         </a> to correct time pattern. Minutes should be mm not MM (MM is month).
 */
public class DateUtil {
	private static Log log = LogFactory.getLog(DateUtil.class);
	private static final String TIME_PATTERN = "HH:mm:ss";
	/**
	 * Checkstyle rule: utility classes should not have public constructor
	 */
	private DateUtil() {
	}

	/**
	 * Return default datePattern (dd/MM/yyyy)
	 */
	public static String getDatePattern() {
		return "dd/MM/yyyy";

	}

	public static String getDateTimePattern() {
		return DateUtil.getDatePattern() + " HH:mm:ss.S";
	}

	/**
	 * This method attempts to convert an Oracle-formatted date in the form dd-MMM-yyyy to mm/dd/yyyy.
	 * 
	 * @param aDate
	 *            date from database as a string
	 * @return formatted string for the ui
	 */
	public static String getDate(Date aDate) {
		SimpleDateFormat df;
		String returnValue = "";

		if (aDate != null) {
			df = new SimpleDateFormat(getDatePattern());
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param strDate
	 *            a string representation of a date
	 * @return a converted Date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 * @see java.text.SimpleDateFormat
	 */
	public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
		SimpleDateFormat df;
		Date date;
		if(aMask!=null) {	
			try {
				df = new SimpleDateFormat(aMask);
				date = df.parse(strDate);
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				if(cal.get(Calendar.YEAR)<=1970){
					cal.set(Calendar.YEAR, getToday().get(Calendar.YEAR));
					date = cal.getTime();
				}
			} catch (ParseException pe) {
				// log.error("ParseException: " + pe);
				throw new ParseException(pe.getMessage(), pe.getErrorOffset());
			}
		} else {
			date = smartConvertStringToDate(strDate);
		}
		
		return (date);
		
	}

	/**
	 * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
	 * 
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(TIME_PATTERN, theTime);
	}

	/**
	 * This method returns the current date in the format: MM/dd/yyyy
	 * 
	 * @return the current date
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTimeZone(Constants.TIMEZONE_GMT);
		cal.setTime(convertStringToDate(todayAsString));

		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in the format you specify on input
	 * 
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 * @see java.text.SimpleDateFormat
	 */
	public static String getDateTime(String aMask, Date aDate) {
		return getDateTime(aMask, aDate, null);
	}

	public static String getDateTime(String aMask, Date aDate, TimeZone timeZone) {
		SimpleDateFormat df = null;
		String returnValue = null;

		if (aDate != null) {
			df = new SimpleDateFormat(aMask);
			if (timeZone != null)
				df.setTimeZone(timeZone);
			returnValue = df.format(aDate);
		}

		return (returnValue);
	}

	/**
	 * This method generates a string representation of a date based on the System Property 'dateFormat' in the format you
	 * specify on input
	 * 
	 * @param aDate
	 *            A date to convert
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate) {
		return getDateTime(getDatePattern(), aDate);
	}

	public static String convertDateToString(Date aDate, TimeZone timeZone) {
		return getDateTime(getDatePattern(), aDate, timeZone);
	}

	/**
	 * convertDateToString with custom pattern
	 * 
	 * @author Mario Tinton Prambudi Nov 5, 2009
	 * 
	 * @param aDate
	 *            A date to convert
	 * @param customPattern
	 *            Custom date pattern
	 * @return a string representation of the date
	 */
	public static String convertDateToString(Date aDate, String customPattern) {
		return getDateTime(customPattern, aDate);
	}

	public static String convertDateToString(Date aDate, String customPattern, TimeZone timeZone) {
		return getDateTime(customPattern, aDate, timeZone);
	}

	/**
	 * This method converts a String to a date using the datePattern
	 * 
	 * @param strDate
	 *            the date to convert (in format MM/dd/yyyy)
	 * @return a date object
	 * @throws ParseException
	 *             when String doesn't match the expected format
	 */
	public static Date convertStringToDate(String strDate) throws ParseException {
		Date aDate = null;

		try {
//			if (log.isDebugEnabled()) {
//				log.debug("converting date with pattern: " + getDatePattern());
//			}

			aDate = convertStringToDate(getDatePattern(), strDate);
		} catch (ParseException pe) {
			log.error("Could not convert '" + strDate + "' to a date, throwing exception");
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return aDate;
	}

	/**
	 * Builds daily Cron expression used by CronTrigger
	 * 
	 * @param time
	 * @return Cron expression
	 */
	public static String buildDailyCronExpression(Date time) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(time);
		return cal.get(Calendar.SECOND) + " " + cal.get(Calendar.MINUTE) + " " + cal.get(Calendar.HOUR_OF_DAY) + " * * ?";
	}

	public static Date copyTime(Date sourceDate) {
		return copyTime(sourceDate, new Date());
	}

	public static Date copyTime(Date sourceDate, Date targetDate) {
		if (sourceDate == null || targetDate == null)
			return null;
		else {
			Calendar sourceCal = Calendar.getInstance();
			sourceCal.setTime(sourceDate);
			Calendar targetCal = Calendar.getInstance();
			targetCal.setTime(targetDate);
			targetCal.set(Calendar.HOUR_OF_DAY, sourceCal.get(Calendar.HOUR_OF_DAY));
			targetCal.set(Calendar.MINUTE, sourceCal.get(Calendar.MINUTE));
			targetCal.set(Calendar.SECOND, sourceCal.get(Calendar.SECOND));
			return targetCal.getTime();
		}
	}

	public static Date convertTimeToDate(java.sql.Time time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
		return cal.getTime();
	}

	public static java.sql.Time convertDateToTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return new java.sql.Time(cal.getTimeInMillis());
	}

	public static Date getCurrentDate(String pattern) throws ParseException {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());

		if (pattern != null) {
			String dateStr = convertDateToString(cal.getTime(), pattern);
			return convertStringToDate(pattern, dateStr);
		} else {
			return cal.getTime();
		}
	}

	public static Date smartConvertStringToDate(String param) {
		Date result = null;
		if (param != null) {
			String[] patternsWithBar = { "dd-MM-yyyy", "dd-MM-yyyy HH:mm:ss", "dd-MM-yy", "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yy-MM-dd" };
			String[] patternWithSlash = { "dd/MM/yyyy", "dd/MM/yyyy HH:mm:ss", "dd/MM/yy", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yy/MM/dd" };
			String[] patternWithoutSeparator = {"MMyy", "ddMM", "yyyyMMdd", "yyMMdd", "yyyyMMddHHmm", "yyyyMMddHHmmsszzz"};
			
			String[] patterns = null;
			if (param.contains("-")) patterns = patternsWithBar;
			else if (param.contains("/")) patterns = patternWithSlash;
			else patterns = patternWithoutSeparator;

			int index = 0;
			while (index < patterns.length && result == null) {
				if(patterns[index].length()==param.length()) {
					try {
						result = convertStringToDate(patterns[index], param);
						if (result.after(DateUtil.convertStringToDate("01/01/1970")))
							return result;
					} catch (Exception e) {}
				}
				index++;
			}
		}
		return result;
	}

	public static Date getDatesDifference(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) return null;
		else {
			long diff = endDate.getTime() - startDate.getTime();
			if (diff < 0) diff = 0;
			
			Date currentDate = resetTimeToMinimum(new Date());
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(currentDate.getTime()+diff);
			return cal.getTime();
		}
	}

	public static Date resetTimeToMinimum(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		else
			cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date resetTimeToMaximum(Date date) {
		Calendar cal = Calendar.getInstance();
		if (date != null)
			cal.setTime(date);
		else
			cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}
	
	public static Date add(Date date, int field, int value) {
		Calendar cal = Calendar.getInstance();
		if (date != null) cal.setTime(date);
		else cal.setTime(new Date());
		cal.add(field, value);
		return cal.getTime();
	}
	
	public static int getDayOfMonth(int year, int month, int date){
		int days = 0;
		Calendar cal = Calendar.getInstance();
		if(year > 0 && month > 0 && date > 0)
		{
			cal = new GregorianCalendar(year, month, date);
			days = cal.getActualMaximum(Calendar.DAY_OF_MONTH); 
		}
		return days;
	}
}
