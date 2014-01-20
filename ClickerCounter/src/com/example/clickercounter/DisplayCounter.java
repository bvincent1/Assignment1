package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class DisplayCounter extends Activity {
	public int ClickerCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_counter);
		
		//get intent
		Intent intent = getIntent();
		String message = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println("Startup");
		System.out.println(message);
		
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
