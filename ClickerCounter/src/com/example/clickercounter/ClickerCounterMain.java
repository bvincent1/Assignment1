package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
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
    protected Context mContext;
	public final String filename = "ClickerCounter.sav";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_main);
		
		ClickerCounterModel[] objectArray = makeClickerModelArray(1);
		
		writeObjectToFile(objectArray);
		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// TODO build array on clicker counter names, or fill with new ones
		// build clicker model object array
		
		ArrayList<String> myArrayString = new ArrayList<String>();		
		ClickerCounterModel[] objectArray = readObjectFromFile();
		/*
		if (objectArray[0].getClickerName() != "New Counter +"){
			int num = objectArray.length+1;
			System.out.println("adding");
			
			// create copy of object array and skip entry to which we want to remove
			ClickerCounterModel[] tempObjectArray = new ClickerCounterModel[num];
			for(int i = 0; i < num; i++){
				if (i == 0){
					tempObjectArray[i] = new ClickerCounterModel("New Counter +");
					tempObjectArray[i].setClickerCount(0);
				}
				else{
					tempObjectArray[i] = new ClickerCounterModel(objectArray[i].getClickerName());
					tempObjectArray[i].setClickerCount(objectArray[i].getClickerCount());
					tempObjectArray[i].setClickerTimestamps(objectArray[i].getClickerTimestamps());

				}
			}
			// reassign object to new array
			writeObjectToFile(tempObjectArray);
			objectArray = tempObjectArray;

		}
		*/
		
		for (int i = 0;i< objectArray.length;i++){
			myArrayString.add(objectArray[i].getClickerName());
		}
		
		//Collections.sort(myArrayString, String.CASE_INSENSITIVE_ORDER);
		
		// build array list adapter with counters as source
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, myArrayString);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_counter_main, menu);
		return true;
	}
	
	public void displayClickerCounter(View view, String message){
		// go to clicker display activity, which will load the clicker
		Intent intent = new Intent(this, DisplayCounter.class);
	    intent.putExtra(EXTRA_MESSAGE, message);
	    startActivity(intent);
	}

	public ClickerCounterModel[] makeClickerModelArray(int num){
		// make array of clicker counter model objects
		ClickerCounterModel[] tempObject = new ClickerCounterModel[num];
		for(int i = 0; i < num; i++){
			if (i>0){
				tempObject[i] = new ClickerCounterModel("New Counter +"+i);
			}
			else{
				tempObject[i] = new ClickerCounterModel("New Counter +");
			}
			tempObject[i].setClickerCount(i);
		}
		return tempObject;
	}
	
	public ClickerCounterModel[] readObjectFromFile(){
		// read in ClickerCounterModel gson type from file
		StringBuilder sb = new StringBuilder();
		try{
			FileInputStream inputStream = openFileInput(filename);
			InputStreamReader isr = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(isr);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// use Gson to convert it into and object
		String json = sb.toString();
		Gson gson = new Gson();
		ClickerCounterModel[] object = gson.fromJson(json, ClickerCounterModel[].class);
		return object;
	}

	public void writeObjectToFile(ClickerCounterModel[] temp){
		// convert from object to gson and write to file
		try {
			Gson gson = new Gson();
			String string = gson.toJson(temp);
			FileOutputStream outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
