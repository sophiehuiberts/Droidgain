package org.beide.droidgain;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.Process;

public class Waiter extends AsyncTask<Object, Void, Boolean> {
	
	DroidgainActivity dg;
	
	/**
	 * Arguments:
	 * [0] = DroidgainActivity
	 * [1] = process
	 */
	public Boolean doInBackground(Object... args) {
		try {
			dg = (DroidgainActivity) args[0];
			((Process) args[1]).waitFor();
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public void onPostExecute(Boolean success) {
		if(success) {
			dg.addToLog("Done.");
		} else {
			dg.addToLog("Failed.");
		}
	}
	
}