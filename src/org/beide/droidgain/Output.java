package org.beide.droidgain;

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.Exception;

public class Output extends AsyncTask<InputStream, String, Void> {
	
	String TAG = "mp3gain";
	DroidgainActivity droidgain;
	
	
	
	public Output(DroidgainActivity dg) {
		super();
		droidgain = dg;
	}
	
	public Void doInBackground(InputStream... iss) {
		InputStream is = iss[0];
		
		int count = 0;
		
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = is.read();
			
			while(i != -1) {
				if(i != 13) {
					if(i == 10) {
						publishProgress(bo.toString());
						bo = new ByteArrayOutputStream();
					} else {
						bo.write(i);
						i = is.read();
					}
					if(i == 13) {
						publishProgress(bo.toString());
						throw new Exception();
					}
				}
			}
			Log.v(TAG, "Stream ended.");
			publishProgress(bo.toString());
		} catch (Exception e) {}
		return null;
	}
	
	
	public void onProgressUpdate(String str) {
		droidgain.addToLog(str);
	}
	
	
}