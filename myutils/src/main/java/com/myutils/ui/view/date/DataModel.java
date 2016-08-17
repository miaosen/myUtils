package com.myutils.ui.view.date;

import java.util.Calendar;

public class DataModel {
	
	
	private Calendar c ;
	private int year;// 年
	private int month;// 月
	private int day;// 日
	private int hour;// 时
	private int minute;// 分
	private int seconds;// 秒
	
	public DataModel(){
		init();
	}
	
	
	/**
	 * 初始化日期
	 */
	private void init() {
		c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);// 获取年份
		month = c.get(Calendar.MONTH) + 1;// 获取月份
		day = c.get(Calendar.DAY_OF_MONTH);// 获取日
		hour = c.get(Calendar.HOUR_OF_DAY);// 获取时
		minute = c.get(Calendar.MINUTE);// 获取分
		seconds = c.get(Calendar.SECOND);// 获取秒
	}
	
	
	
	
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	public int getHour() {
		return hour;
	}
	public int getMinute() {
		return minute;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public void setMinute(int minute) {
		this.minute = minute;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	
	
	

}
