package com.example.clickercounter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ClickerCounterMain extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.CLickerCounter.MESSAGE";
    
    private ListView counterList;
    private ListAdapter adapter;

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
	
	@Override
	 protected void onResume () {
		super.onResume();
		// TODO build array on clicker counter names, or fill with new ones
		String[] myStringArray = {"New Counter +","test1", "test2"};
	
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myStringArray);
		counterList = (ListView) findViewById(R.id.counterListView);
		counterList.setAdapter(adapter);

		// set listener for list view and goto display counter activity
		counterList.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	String selectedFromList = (String) counterList.getItemAtPosition(position);
	        	System.out.println(selectedFromList);
	        	
	        	displayClickerCounter(view, selectedFromList);
	        }
	    });
	}
	
	public void displayClickerCounter(View view, String message){
		// go to clicker display activity, which will load the clicker
		Intent intent = new Intent(this, DisplayCounter.class);
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}

}
