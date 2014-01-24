package com.example.clickercounter;

import java.util.ArrayList;
import java.util.Calendar;

public class ClickerCounterModel {
	
	protected int clickerCount = 0;
	protected String clickerName;
	protected ArrayList<Calendar> clickerTimestamps;

	// assigns clicker counter name and sets count to 0
	public ClickerCounterModel(String clickerName) {
		super();
		this.clickerName = clickerName;
		this.clickerCount = 0;
		this.clickerTimestamps = new ArrayList<Calendar>();
	}
	
	public void incrementClickerCount(){
		this.clickerCount += 1;
		this.clickerTimestamps.add(Calendar.getInstance());
	}

	/** getters and setters */
	public ArrayList<Calendar> getClickerTimestamps() {
		return clickerTimestamps;
	}
	public void setClickerTimestamps(ArrayList<Calendar> clickerTimestamps) {
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
	
	public void resetCount(){
		this.clickerCount = 0;
		this.clickerTimestamps.clear();
	}
}
