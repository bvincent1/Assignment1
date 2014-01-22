package com.example.clickercounter;

import java.util.ArrayList;
import java.util.Date;

public class ClickerCounterModel {
	
	protected int clickerCount;
	protected String clickerName;
	protected ArrayList<Date> clickerTimestamps;

	// assigns clicker counter name and sets count to 0
	public ClickerCounterModel(String clickerName) {
		super();
		this.clickerName = clickerName;
		this.clickerCount = 0;
	}
	
	public void incrementClickerCount(){
		this.clickerCount += 1;
	}

	/** getters and setters */
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
	
	public void resetCount(){
		this.clickerCount = 0;
		this.clickerTimestamps.clear();
	}
}
