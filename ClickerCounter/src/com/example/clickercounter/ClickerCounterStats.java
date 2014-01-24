package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

public class ClickerCounterStats extends Activity {
	public final String filename = "ClickerCounter.sav";
	public ClickerCounterModel clickerCountObject = null;
	public ClickerCounterModel[] objectArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_stats);
		// receive counter name
		Intent intent = getIntent();
		String tempCounterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(tempCounterName);
		// build counter object from file
		objectArray = readObjectFromFile();
		for (int i = 0; i < objectArray.length; i++){
			if (objectArray[i].getClickerName().equals(tempCounterName)){
				clickerCountObject = objectArray[i];
			}
		}
		// set title name
		TextView counterName = (TextView) findViewById(R.id.textNameView);
		counterName.setText(tempCounterName);
		ArrayList <String> temp = null;
		temp =  getCountStatics();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,temp);
		ListView counterList = (ListView) findViewById(R.id.listStats);
		counterList.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.clicker_counter_stats, menu);
		return true;
	}

	public void deleteClicker(View view){
		// clicker remove array  entry
		int num = objectArray.length-1;
		System.out.println("delete");
		
		// create copy of object array and skip entry to which we want to remove
		ClickerCounterModel[] tempObjectArray = new ClickerCounterModel[num];
		for(int i = 0; i < num; i++){
			if (objectArray[i].getClickerName() != clickerCountObject.getClickerName()){
				tempObjectArray[i] = new ClickerCounterModel(objectArray[i].getClickerName());
				tempObjectArray[i].setClickerCount(objectArray[i].getClickerCount());
				tempObjectArray[i].setClickerTimestamps(objectArray[i].getClickerTimestamps());
			}
		}
		// reassign object to new array
		writeObjectToFile(tempObjectArray);
		
		// restart the application
		Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	public void resetClicker(View view){
		System.out.println("reset");
		// call the reset clicker reset method and save
		clickerCountObject.setClickerCount(0); // needs to be switched with clicker rest after implementing dates
		writeObjectToFile(objectArray);
		
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

	public ArrayList<String> getCountStatics(){
		// build statistics array
		int countsPerMinute = 0;
		int countsPerHour = 0;
		int countsPerMonth = 0;
		ArrayList<String> myArrayString = new ArrayList<String>();
		Calendar currDate = Calendar.getInstance();
		for (int i = 0; i < clickerCountObject.getClickerTimestamps().size();i++){
			System.out.println(
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.SECOND));
			if (currDate.get(Calendar.MINUTE) -1 < 
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.MINUTE) &&
					currDate.get(Calendar.MINUTE) <
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.MINUTE)){
				countsPerMinute += 1;
			}
			if (currDate.get(Calendar.HOUR_OF_DAY) - 1 < 
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.HOUR_OF_DAY) &&
					currDate.get(Calendar.HOUR_OF_DAY) < 
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.HOUR_OF_DAY)){
				countsPerHour += 1;
			}
			if ((currDate.get(Calendar.DAY_OF_MONTH) - 1 < 
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.DAY_OF_MONTH)) &&
					(currDate.get(Calendar.DAY_OF_MONTH) < 
					clickerCountObject.getClickerTimestamps().get(i).get(Calendar.DAY_OF_MONTH))){
				countsPerMonth += 1;
			}
		}
		myArrayString.add("Counts per Minute "+Integer.toString(countsPerMinute));
		myArrayString.add("Counts per Hour "+Integer.toString(countsPerHour));
		myArrayString.add("Counts per Day "+Integer.toString(countsPerMonth));
		
		return myArrayString;
	}
}
