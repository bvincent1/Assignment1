package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayCounter extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.CLickerCounter.MESSAGE";
	public int ClickerCount = 0;
	public String counterName;

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public int getClickerCount() {
		return ClickerCount;
	}

	public void setClickerCount(int clickerCount) {
		ClickerCount = clickerCount;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_counter);
		
		//get intent & message
		Intent intent = getIntent();
		counterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(counterName);
		
		// TODO add file check to see if we need a new name or we are searching for an existing one
		// get name and check to see if we need to generate new name or import old one
		EditText clickerName = (EditText) findViewById(R.id.clikerCounterName);
		clickerName.setHint(counterName);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_counter, menu);
		return true;
	}

	public void incrementCounter(View view){
		System.out.println("increment");
		Button ClickerCounter = (Button) findViewById(R.id.counterButton);
		ClickerCounter.setTag(1);
		ClickerCounter.setText(Integer.toString(++ClickerCount));
		
		// TODO add file saver to write to file any changes upon pausing
		EditText clickerName = (EditText) findViewById(R.id.clikerCounterName);
		//setCounterName(clickerName);
	}

	public void getStatistics(View view){
		// go to clicker display activity, which will load the clicker
		Intent intent = new Intent(this, ClickerCounterStats.class);
	    intent.putExtra(EXTRA_MESSAGE, counterName);
	    startActivity(intent);
	}
}
