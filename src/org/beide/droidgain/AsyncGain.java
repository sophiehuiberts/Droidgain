package org.beide.droidgain;

import android.os.AsyncTask;

public class AsyncGain extends AsyncTask<String, Boolean, Void> {
	
	public Void doInBackground(String... files) {
		Boolean[] completion = new Boolean[files.length];
		for(int i = 0; i < files.length; i++) {
			completion[i] = mp3gain(files[i]);
			publishProgress(completion);
		}
		return null;
	}
	
	public void onProgressUpdate(Boolean... progress) {
		
	}
	
	public native boolean mp3gain(String filename);
	
	static {
		System.loadLibrary("mp3gain");
	}
}