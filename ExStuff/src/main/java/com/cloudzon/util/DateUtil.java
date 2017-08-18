package com.cloudzon.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

	public static Date getCurrentDate() {
		return new Date();
	}

	public static Date getDate(String date, String dateFormat) {
		DateTimeFormatter objDateFormat = null;
		LocalDate objLocalDate = null;
		Date objDate = null;
		try {
			objDateFormat = DateTimeFormat.forPattern(dateFormat);
			objLocalDate = LocalDate.parse(date, objDateFormat);
			objDate = objLocalDate.toDate();
		} catch (Exception exception) {
			objDate = null;
		} finally {
			objDateFormat = null;
			objLocalDate = null;
		}
		return objDate;
	}

	public static String getDate(Date date, String dateFormat) {
		DateTimeFormatter objDateFormat = null;
		DateTime objDateTime = null;
		String objFormateDate = null;
		try {
			objDateFormat = DateTimeFormat.forPattern(dateFormat);
			objDateTime = new DateTime(date);
			objFormateDate = objDateFormat.print(objDateTime);
		} catch (Exception exception) {
			objFormateDate = null;
		} finally {
			objDateFormat = null;
			objDateTime = null;
		}
		return objFormateDate;
	}

	public static Date getAddedDate(int seconds, int minutes, int hours) {
		DateTime objDateTime = null;
		objDateTime = new DateTime();
		objDateTime.plusSeconds(seconds);
		objDateTime.plusMinutes(minutes);
		objDateTime.plusHours(hours);
		return objDateTime.toDate();
	}

	public static Date getPastOrAddedDate(int hours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR, hours);
		Date dateBeforeDays = cal.getTime();
		return dateBeforeDays;
	}

	public static Date currentDateWithoutTime() {
		Date currentDate = null;
		Calendar newDate = null;
		try {
			currentDate = new Date();
			newDate = Calendar.getInstance();
			newDate.setLenient(false);
			newDate.setTime(currentDate);
			newDate.set(Calendar.HOUR_OF_DAY, 0);
			newDate.set(Calendar.MINUTE, 0);
			newDate.set(Calendar.SECOND, 0);
			newDate.set(Calendar.MILLISECOND, 0);
			currentDate = newDate.getTime();
		} finally {

		}
		return currentDate;

	}

}
