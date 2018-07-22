package com.sandy.rate.vo;

import java.util.List;

public class ChartVO {

	List<Double> buyRateList;
	List<Double> sellRateList;
	List<String> timeList;
	String startDate;
	String endDate;

	public List<Double> getBuyRateList() {
		return buyRateList;
	}

	public void setBuyRateList(List<Double> buyRateList) {
		this.buyRateList = buyRateList;
	}

	public List<Double> getSellRateList() {
		return sellRateList;
	}

	public void setSellRateList(List<Double> sellRateList) {
		this.sellRateList = sellRateList;
	}

	public List<String> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<String> timeList) {
		this.timeList = timeList;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
