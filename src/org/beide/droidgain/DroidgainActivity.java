package org.beide.droidgain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.lang.Process;
import java.lang.Runtime;

public class DroidgainActivity extends Activity {
	
	String exec;
	LinearLayout log;
	
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
		
		//TODO: put the executable there
		exec = getApplicationContext().getFilesDir().getPath() + "/mp3gain";
		
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
		
		ScrollView scroll = new ScrollView(context);
		scroll.addView(log);
		addToLog("Welcome to Droidgain.");
		addToLog("If you have any problems, feel free to contact me at");
		addToLog("itissohardtothinkofagoodemail@gmail.com");
		
		LinearLayout root = new LinearLayout(context);
		root.setOrientation(LinearLayout.VERTICAL);
		root.addView(BtnPick);
		root.addView(scroll);
		
		setContentView(root);
	}
	
	/**
	 * Gets the result of ACTION_GET_CONTENT.
	 * 
	 * Will be deprecated as soon as the file-browser is coded.
	 */
	public void onActivityResult(int request, int result, Intent data) {
		Uri uri = data.getData();
		
		if(uri.getScheme().compareTo("file") == 0) {
			
			String schemepart = uri.getSchemeSpecificPart();
			if(schemepart.substring(0, 2).compareTo("//") == 0) {
				mp3gain(schemepart.substring(2));
			} else {
				mp3gain(schemepart);
			}
		}
	}
	
	/**
	 * Executes the mp3gain executable, on the given file.
	 */
	public void mp3gain(String file) {
		try {
			Process process = Runtime.getRuntime().exec(new String[]{exec, file});
			readOutput(process.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
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
				}
			}
			addToLog(bo.toString());
		} catch (IOException e) {}
	}
	
	public void addToLog(String str) {
		TextView tv = new TextView(context);
		tv.setText(str);
		
		log.addView(tv);
	}
}
