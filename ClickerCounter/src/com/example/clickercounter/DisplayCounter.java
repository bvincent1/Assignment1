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
	public ClickerCounterModel clickerCountObject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_counter);
		
		//get intent & message
		Intent intent = getIntent();
		String tempCounterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(tempCounterName);
		
		// TODO add file check to see if we need a new name or we are searching for an existing one
		// get name and check to see if we need to generate new name or import old one
		
		clickerCountObject = new ClickerCounterModel(tempCounterName);
		
		EditText clickerName = (EditText) findViewById(R.id.clikerCounterName);
		clickerName.setHint(clickerCountObject.getClickerName());
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_counter, menu);
		return true;
	}

	public void incrementCounter(View view){
		System.out.println("increment");
		
		// increment clicker count
		clickerCountObject.incrementClickerCount();
		
		// get button element and show updated value
		Button ClickerCounter = (Button) findViewById(R.id.counterButton);
		ClickerCounter.setText(Integer.toString(clickerCountObject.getClickerCount()));
		
		// TODO add file saver to write to file any changes upon pausing
		
		// check if name has changed, if so update object
		EditText newName = (EditText) findViewById(R.id.clikerCounterName);
		if (clickerCountObject.getClickerName().compareTo(newName.toString()) != 0){
			clickerCountObject.setClickerName(newName.toString());
		}
	}

	public void getStatistics(View view){
		// go to clicker display activity, which will load the clicker
		Intent intent = new Intent(this, ClickerCounterStats.class);
	    intent.putExtra(EXTRA_MESSAGE, clickerCountObject.getClickerName());
	    startActivity(intent);
	}
	
	public ClickerCounterModel[] makeObjectArray(){
		ClickerCounterModel[] tempObject = new ClickerCounterModel[3];
		for(int i = 0; i < 3 + 1; i++){
			tempObject[i].setClickerName("test"+i);
		}
		return tempObject;
		
	}
}
