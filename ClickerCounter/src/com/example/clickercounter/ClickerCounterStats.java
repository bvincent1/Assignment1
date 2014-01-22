package com.example.clickercounter;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ClickerCounterStats extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_stats);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_counter_stats, menu);
		return true;
	}

	public void deleteClicker(View view){
		// TODO add clicker delete button
		System.out.println("delete");
	}

	public void resetClicker(View view){
		System.out.println("reset");
		Button ClickerCounter = (Button) findViewById(R.id.counterButton);
		ClickerCounter.setTag(1);
		//setClickerCount(0);
		//ClickerCounter.setText(Integer.toString(ClickerCount));
	}
}
