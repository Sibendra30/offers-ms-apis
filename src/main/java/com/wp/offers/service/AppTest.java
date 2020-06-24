package com.wp.offers.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AppTest {

	public static void main(String[] args) {
		Date d = new Date();
		System.out.println(d);
		Calendar cal = Calendar.getInstance(Locale.getDefault());
		cal.setTime(d);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
		System.out.println(cal.getTime());
		
		LocalDateTime ldt = LocalDateTime.of(1986, Month.APRIL, 18, 21, 30);
		System.out.println(ldt);
	}

}
