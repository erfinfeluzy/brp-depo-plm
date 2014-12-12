package com.depobrp.commons.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils{

	public static Integer totalDiffDay(Date startDate, Date endDate) {

		if(startDate == null || endDate == null)
			return 0;
		
		Long startTime = startDate.getTime();
		Long endTime = endDate.getTime();
		Long diffTime = endTime - startTime;
		Long diffDays = diffTime / (1000 * 60 * 60 * 24);

		return diffDays.intValue();
	}

	
	public static void main(String[] args) {
		
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.set(2010, 7, 23);
		end.set(2010, 8, 26);
		
		Integer diffDays = DateUtils.totalDiffDay(start.getTime(), end.getTime());
		
		System.out.println("diff betweenx " + start.getTime() + " and " + end.getTime() + " : " + diffDays + " days");
		
	}
}
