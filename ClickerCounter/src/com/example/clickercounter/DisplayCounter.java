package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class DisplayCounter extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_counter);
		
		//get intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_counter, menu);
		return true;
	}

}
