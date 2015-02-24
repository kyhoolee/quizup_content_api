package com.ctv.quizup.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ComUtils {

	public static String current(Date now) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String day(Date now) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String week(Date now) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-w");
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String month(Date now) {
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM");
		String strDate = sdfDate.format(now);
		return strDate;
	}

	public static String week() {

		// Create calendar instance
		Calendar calendar = Calendar.getInstance();

		System.out.println("Current week of this month = "
				+ calendar.get(Calendar.WEEK_OF_MONTH));

		System.out.println("Current week of this year = "
				+ calendar.get(Calendar.WEEK_OF_YEAR));
		return "";

	}

	public static void main(String[] args) {
		String c = "Mon Jan 19 21:39:26 ICT 2015";
		Date now = new Date();
		System.out.println(now.getTime());
		System.out.println(ComUtils.current(now));
		System.out.println(ComUtils.day(now));
		System.out.println(ComUtils.week(now));
		System.out.println(ComUtils.month(now));
	}

}
