package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;

public class DisplayCounter extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.CLickerCounter.MESSAGE";
    public final String filename = "ClickerCounter.sav";
	public ClickerCounterModel clickerCountObject = null;
	public ClickerCounterModel[] objectArray = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_counter);
		
		//get intent & message
		Intent intent = getIntent();
		String tempCounterName = intent.getStringExtra(ClickerCounterMain.EXTRA_MESSAGE);
		System.out.println(tempCounterName);
		
		// get counter object array and single it out to the specific element from the array
		objectArray = readObjectFromFile();
		for (int i = 0; i < objectArray.length; i++){
			if (objectArray[i].getClickerName().equals(tempCounterName)){
				clickerCountObject = objectArray[i];
			}
		}				
		EditText clickerName = (EditText) findViewById(R.id.clikerCounterName);
		clickerName.setText(clickerCountObject.getClickerName());
	}
	
	protected void onResume(){
		super.onResume();
		// get button element and show updated value
		objectArray = readObjectFromFile();
		for (int i = 0; i < objectArray.length; i++){
			if (objectArray[i].getClickerName().equals(clickerCountObject.getClickerName())){
				clickerCountObject = objectArray[i];
			}
		}
		Button clickerCounterButton = (Button) findViewById(R.id.counterButton);
		clickerCounterButton.setText(Integer.toString(clickerCountObject.getClickerCount()));
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
		if (clickerCountObject.getClickerName().compareTo(newName.getText().toString()) != 0){
			clickerCountObject.setClickerName(newName.getText().toString());
		}

		// save changes
		writeObjectToFile(objectArray);
	}

	public void getStatistics(View view){
		// go to clicker display activity, which will load the clicker
		Intent intent = new Intent(this, ClickerCounterStats.class);
	    intent.putExtra(EXTRA_MESSAGE, clickerCountObject.getClickerName());
	    startActivity(intent);
	}
	
	
	// needs to be removed *************************************************
	public ClickerCounterModel[] makeClickerModelArray(int num){
		// make array of clicker counter model objects
		ClickerCounterModel[] tempObject = new ClickerCounterModel[num];
		for(int i = 0; i < num; i++){
			tempObject[i] = new ClickerCounterModel("New Counter +");
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
