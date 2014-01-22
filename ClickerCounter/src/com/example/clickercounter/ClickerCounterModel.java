package com.example.clickercounter;

import java.util.ArrayList;
import java.util.Date;

public class ClickerCounterModel {
	
	protected int clickerCount = 0;
	protected String clickerName;
	protected ArrayList<Date> clickerTimestamps;
	
	
	public ArrayList<Date> getClickerTimestamps() {
		return clickerTimestamps;
	}
	public void setClickerTimestamps(ArrayList<Date> clickerTimestamps) {
		this.clickerTimestamps = clickerTimestamps;
	}
	public int getClickerCount() {
		return clickerCount;
	}
	public void setClickerCount(int clickerCount) {
		this.clickerCount = clickerCount;
	}
	public String getClickerName() {
		return clickerName;
	}
	public void setClickerName(String clickerName) {
		this.clickerName = clickerName;
	}
}
