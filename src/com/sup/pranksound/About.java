package com.sup.pranksound;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;

public class About extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getActionBar().hide();
		new AlertDialog.Builder(this)
		.setTitle(R.string.error_title)
		.setMessage(R.string.error_message)
		.setPositiveButton(R.string.about_ok,listener)
		.show();
		
	}
	
	private DialogInterface.OnClickListener listener =new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			playVideo();
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.about, menu);
		return true;
	}
	
	public void playVideo(){
		
	}

}
