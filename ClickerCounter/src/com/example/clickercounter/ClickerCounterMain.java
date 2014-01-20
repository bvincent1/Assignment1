package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class ClickerCounterMain extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.CLickerCounter.MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_counter_main, menu);
		return true;
	}

	public void newClickerCounter(View view){
		// go to clicker with the name specified by this button
		Intent intent = new Intent(this, DisplayCounter.class);
		Button newCounter = (Button) findViewById(R.id.createCounterButton);
	    String message = newCounter.getText().toString();
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}

}
