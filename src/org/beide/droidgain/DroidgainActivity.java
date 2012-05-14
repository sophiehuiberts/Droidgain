package org.beide.droidgain;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.lang.Process;
import java.lang.Runtime;

public class DroidgainActivity extends Activity {
	
	String url;
	LinearLayout column;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Button BtnPick = new Button(getApplicationContext());
		BtnPick.setText("Pick");
		BtnPick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				intent.setType("audio/mpeg");
				startActivityForResult(intent, 299792458);
			}
		});
		
		setContentView(BtnPick);
	}
	
	public void onActivityResult(int request, int result, Intent data) {
		Uri uri = data.getData();
		
		if(uri.getScheme().compareTo("file") == 0) {
			
			String schemepart = uri.getSchemeSpecificPart();
			if(schemepart.substring(0, 2).compareTo("//") == 0) {
				url = schemepart.substring(2);
			} else {
				url = schemepart;
			}
			
			mp3gain(url);
			
		}
	}
	
	public void mp3gain(String file) {
		try {
			Process process = Runtime.getRuntime().exec(["/sdcard/mp3gain", file]);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
