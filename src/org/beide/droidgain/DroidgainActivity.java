package org.beide.droidgain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.Process;
import java.lang.Runtime;

public class DroidgainActivity extends Activity {
	
	private static final String TAG = "Droidgain";
	
	String exec;
	LinearLayout log;
	ScrollView scroll;
	EditText edittext;
	
	Context context;
	
	/**
	 * Called when activity is created.
	 * 
	 * It makes sure the executable is present, and makes the UI.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = getApplicationContext();
		
		Button BtnPick = new Button(context);
		BtnPick.setText("Pick");
		BtnPick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//TODO: implement own file-chooser
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("audio/mpeg");
				startActivityForResult(intent, 299792458);
			}
		});
		
		log = new LinearLayout(context);
		log.setOrientation(LinearLayout.VERTICAL);
		
		scroll = new ScrollView(context);
		scroll.addView(log);
		addToLog("Welcome to Droidgain.");
		addToLog("If you have any problems, too bad. I suggest");
		addToLog("you try some other app. Just search for ");
		addToLog("mp3 gain in the Play store.");
		
		exec = getApplicationContext().getFilesDir().getPath() + "/mp3gain";
		File f = new File(exec);
		if(! f.exists()) {
			addToLog("Executable not found. Placing it now.");
			try {
				// From the ApiDemos
				InputStream is = getResources().openRawResource(R.raw.mp3gain);
				int size = is.available();
				
				byte[] buffer = new byte[size];
				is.read(buffer);
				is.close();
				
				OutputStream out;
				out = new BufferedOutputStream(new FileOutputStream(f));
				out.write(buffer);
				out.close();
				out = null;
				
				if(!f.setExecutable(true)) {
					addToLog("Could not get permission.");
				} else {
					addToLog("Setup done.");
				}
				
				
			} catch(Exception e) {
				addToLog("Ooops, The application is broken!");
				e.printStackTrace();
				addToLog(e.getMessage());
				addToLog(e.toString());
			}
		}
		
		edittext = new EditText(context);
		edittext.setText("0", TextView.BufferType.EDITABLE);
		
		LinearLayout input = new LinearLayout(context);
		input.addView(BtnPick);
		input.addView(edittext);
		
		LinearLayout root = new LinearLayout(context);
		root.setOrientation(LinearLayout.VERTICAL);
		root.addView(input);
		root.addView(scroll);
		
		setContentView(root);
	}
	
	/**
	 * Gets the result of ACTION_GET_CONTENT.
	 * 
	 * Will be deprecated as soon as the file-browser is coded.
	 */
	public void onActivityResult(int request, int result, Intent data) {
		Log.v(TAG, "Got result");
		
		if(data == null) {
			Log.v(TAG, "Got null :(");
			return;
		}
		
		Uri uri = data.getData();
		
		String path = uri.getPath();
		mp3gain(path);
	}
	
	/**
	 * Executes the mp3gain executable, on the given file.
	 */
	public void mp3gain(String file) {
		try {
			addToLog(file);
			Process process = new ProcessBuilder()
			.command(exec, "-r", "-c", "-d", edittext.getText().toString(), file)
			.redirectErrorStream(true)
			.start();
			new Output(this).execute(process.getInputStream());
			new Waiter().execute(this, process);
		} catch(Exception e) {
			addToLog("Failed :(");
			e.printStackTrace();
		}
	}
	
	/**
	 * Accepts a InputStream, and puts all content in addToLog().
	 */
	public void readOutput(InputStream is) {
		try {
			ByteArrayOutputStream bo = new ByteArrayOutputStream();
			int i = is.read();
			
			while(i != -1) {
				if(i == 10 || i == 13) {
					addToLog(bo.toString());
					bo = new ByteArrayOutputStream();
				} else {
					bo.write(i);
					i = is.read();
					Log.v(TAG, bo.toString());
				}
			}
			Log.v(TAG, "Stream ended.");
			addToLog(bo.toString());
		} catch (IOException e) {}
	}
	
	/**
	 * Adds all input to the log view
	 */
	public void addToLog(String str) {
		TextView tv = new TextView(context);
		tv.setText(str);
		
		log.addView(tv);
		
		scroll.fullScroll(ScrollView.FOCUS_DOWN);
		
		Log.i(TAG, str);
	}
}
