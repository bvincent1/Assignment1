package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
		
		//get intent
		Intent intent = getIntent();
		counterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(counterName);
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		
		TextView clickerName = (TextView) findViewById(R.id.displyCounterName);
		clickerName.setText(counterName);
	}

	public void incrementCounter(View view){
		System.out.println("startup");
	
		Button ClickerCounter = (Button) findViewById(R.id.counterButton);
		ClickerCounter.setTag(1);
		ClickerCounter.setText(Integer.toString(++ClickerCount));
	
		System.out.println("shutdown");
	}
	
	public void editName(View view){
		// go to edit activity and allow for name change
		Intent intent = new Intent(this, EditCounterName.class);
		Button editCounter = (Button) findViewById(R.id.editCounterButton);
	    String message = editCounter.getText().toString();
	    intent.putExtra(counterName, message);
	    startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.display_counter, menu);
		return true;
	}

}
