package com.sandy.rate.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.sandy.rate.vo.TimePeriod;

public class MyUtils {
	
	public static TimePeriod getTimePeriodByRangeType(String rangeType) {
		TimePeriod timePeriod = new TimePeriod();
		LocalDateTime currentDateTime = LocalDateTime.now();
		String startDate = "";
		String endDate = currentDateTime.toString();
		switch(rangeType) {
		case "0"://當天
			startDate = currentDateTime.truncatedTo(ChronoUnit.DAYS).toString();
			break;
		case "1"://3個月
			startDate = currentDateTime.minusMonths(3).toString();
			break;
		case "2"://6個月
			startDate = currentDateTime.minusMonths(6).toString();
			break;
		case "3"://一年
			startDate = currentDateTime.minusMonths(12).toString();
			break;
		}
		timePeriod.setStartDate(startDate);
		timePeriod.setEndDate(endDate);
		return timePeriod;
	}
}
