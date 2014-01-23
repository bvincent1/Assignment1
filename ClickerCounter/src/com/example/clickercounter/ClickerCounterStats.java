package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class ClickerCounterStats extends Activity {
	public final String filename = "ClickerCounter.sav";
	public ClickerCounterModel clickerCountObject = null;
	public ClickerCounterModel[] objectArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_stats);
		
		Intent intent = getIntent();
		String tempCounterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(tempCounterName);
		
		objectArray = readObjectFromFile();
		for (int i = 0; i < objectArray.length; i++){
			if (objectArray[i].getClickerName().equals(tempCounterName)){
				clickerCountObject = objectArray[i];
			}
		}
		
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
}
