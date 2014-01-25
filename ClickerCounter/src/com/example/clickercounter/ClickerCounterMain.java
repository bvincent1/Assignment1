package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clickercounter.R;
import com.google.gson.Gson;

public class ClickerCounterMain extends Activity {
    public final static String EXTRA_MESSAGE = "com.example.CLickerCounter.MESSAGE";
    
    private ListView counterList;
    private ArrayAdapter<String> adapter;
    protected Context mContext;
	public final String filename = "ClickerCounter.sav";
	private ArrayList<String> listViewStringArray;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_clicker_counter_main);
		
		if (readObjectFromFile() == null) {
			// make default clicker object with "new counter +"
			ClickerCounterModel[] objectArray = makeClickerModelArray(1);
			writeObjectToFile(objectArray);
		}
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		// build clicker model object array
		listViewStringArray = new ArrayList<String>();		
		ClickerCounterModel[] objectArray = readObjectFromFile();
		
		// check if we need empty clicker leading the list
		if (objectArray[0].getClickerName().equals("New Counter +") == false){
			int num = objectArray.length+1;
			System.out.println("adding");
			System.out.println(num);
			
			// insert new empty counter in front of others		
			ClickerCounterModel[] tempObjectArray = new ClickerCounterModel[num];
			tempObjectArray[0] = new ClickerCounterModel("New Counter +");
			tempObjectArray[0].setClickerCount(0);
			for(int i = 1; i < num; i++){
				tempObjectArray[i] = new ClickerCounterModel(objectArray[i-1].getClickerName());
				tempObjectArray[i].setClickerCount(objectArray[i-1].getClickerCount());
				// TODO implement time stamps
				tempObjectArray[i].setClickerTimestamps(objectArray[i-1].getClickerTimestamps());
			}
			// reassign object to new array
			writeObjectToFile(tempObjectArray);
			objectArray = tempObjectArray;
		}
		
		// build ListView array from object array clicker names
		for (int i = 0;i< objectArray.length;i++){
			listViewStringArray.add(objectArray[i].getClickerName()+";"+objectArray[i].getClickerCount());
		}
		
		//Collections.sort(myArrayString, String.CASE_INSENSITIVE_ORDER);
		
		// build array list adapter with counters as source
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listViewStringArray);
		counterList = (ListView) findViewById(R.id.counterListView);
		counterList.setAdapter(adapter);

		// set listener for list view and goto display counter activity
		counterList.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	                int position, long id) {
	        	String selectedFromList = (String) counterList.getItemAtPosition(position);
	        	selectedFromList = selectedFromList.substring(0, selectedFromList.lastIndexOf(";")); 
	        	System.out.println(selectedFromList);
	        	// go to display counter activity
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

	public void changeOrder(View view){
		// TODO add sort
		 Collections.sort(listViewStringArray, new Comparator<String>(){
		      public int compare(String obj1, String obj2)
		      {
		            // TODO Auto-generated method stub
		            return obj1.substring(obj1.indexOf(";"), obj1.length())
		            		.compareTo(obj2.substring(obj2.indexOf(";"), obj2.length()));
		      }
		});
		adapter.notifyDataSetChanged();
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
		// check if generating new files
		if (sb.equals(null)){
			return null;
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
