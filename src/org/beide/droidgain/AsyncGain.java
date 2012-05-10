package org.beide.droidgain;

import android.os.AsyncTask;

public class AsyncGain extends AsyncTask<String, Double, Void> {
	
	public Void doInBackground(String... files) {
		for(int i = 0; i < files.length; i++) {
			mp3gain(files[i]);
			publishProgress(i / files.length);
		}
	}
	
	public void onProgressUpdate(Double... progress) {
		
	}
	
	public native mp3gain(String filename);
}