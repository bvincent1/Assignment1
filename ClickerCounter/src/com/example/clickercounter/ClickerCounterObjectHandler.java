package com.example.clickercounter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;

import com.google.gson.Gson;

public class ClickerCounterObjectHandler {
	protected Context mContext;
	public final String filename = "ClickerCounter.sav";
	private ClickerCounterModel[] objectArray = null;
	
	// need personal context object
	public ClickerCounterObjectHandler(Context c) {
		mContext = c;
	}
	
	public void readObjectFromFile(){
		// read in ClickerCounterModel gson type from file
		StringBuilder sb = new StringBuilder();
		try{
			FileInputStream inputStream = mContext.openFileInput(filename);
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
		this.objectArray = gson.fromJson(json, ClickerCounterModel[].class);
	}

	public void writeObjectToFile(){
		// convert from object to gson and write to file
		try {
			Gson gson = new Gson();
			String string = gson.toJson(objectArray);
			FileOutputStream outputStream = mContext.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ClickerCounterModel getObjectModel(String name){
		if (this.objectArray == null){
			readObjectFromFile(); 
		}
		for(int i = 0; i < this.objectArray.length; i++){
			if (this.objectArray[i].getClickerName().equals(name) == true){
				return this.objectArray[i];
			}
		}
		return null;
	}
	
	public void saveObjectModel(String name){
		
	}
}
