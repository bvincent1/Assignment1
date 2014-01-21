package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DisplayCounter extends Activity {
	public int ClickerCount = 0;
	public String counterName;

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
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		// TODO add file check to see if we need a new name or we are searching for an existing one
		// get name and check to see if we need to generate new name or import old one
		EditText clickerName = (EditText) findViewById(R.id.clikerCounterName);
		clickerName.setHint(counterName);
	}

	@Override
	protected void onPause(){
		super.onPause();
		// TODO add file saver to write to file any changes upon pausing
	}

	public void incrementCounter(View view){
		System.out.println("startup");
	
		Button ClickerCounter = (Button) findViewById(R.id.counterButton);
		ClickerCounter.setTag(1);
		ClickerCounter.setText(Integer.toString(++ClickerCount));
	
		System.out.println("shutdown");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_counter, menu);
		return true;
	}

}
