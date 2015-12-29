package com.sup.pranksound;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class Categories extends Activity {
	
	
	TextView human;
	TextView movies;
	TextView environment;
	TextView about;
	
	public static final int MENU_ABOUT=Menu.FIRST;
	public static final int MENU_QUIT=Menu.FIRST+1;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_categories);
		ActionBar actionbar=getActionBar();
		actionbar.hide();
		human=(TextView)findViewById(R.id.human);
		human.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Holo_Large);
		human.setTextColor(Color.BLACK);
		movies=(TextView)findViewById(R.id.movies);
		movies.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Holo_Large);
		movies.setTextColor(Color.BLACK);
		environment=(TextView)findViewById(R.id.environment);
		environment.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Holo_Large);
		environment.setTextColor(Color.BLACK);
		about=(TextView)findViewById(R.id.about);
		about.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Holo_Large);
		about.setTextColor(Color.BLACK);
		
		human.setOnClickListener(lh);
		movies.setOnClickListener(lm);
		environment.setOnClickListener(le);
		about.setOnClickListener(labout);
		
	}
	
	private OnClickListener lh=new OnClickListener(){

		@Override
		public void onClick(View v) {
			Intent huintent=new Intent(Categories.this,Ren.class);
			startActivity(huintent);
			
		}
		
	};
	
	private OnClickListener lm=new OnClickListener(){
		@Override
		public void onClick(View v) {
			Intent mointent=new Intent(Categories.this,Movie.class);
			startActivity(mointent);
			
		}
		
	};
	
	private OnClickListener le=new OnClickListener(){
		@Override
		public void onClick(View v) {
			//switch to environment sound
			Intent enintent=new Intent(Categories.this,Environment.class);
			startActivity(enintent);
			
		}
		
	};
	
	private OnClickListener labout=new OnClickListener(){


		@Override
		public void onClick(View arg0) {
			openAboutDialog();
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,MENU_ABOUT,0,R.string.about);
		menu.add(0,MENU_QUIT,0,R.string.quit);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case MENU_ABOUT:
			openAboutDialog();
			break;
		case MENU_QUIT:
		    finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}



	
	private void openAboutDialog(){
		new AlertDialog.Builder(Categories.this)
		.setTitle(R.string.abouttitle)
		.setMessage(R.string.aboutmessage)
		.setNegativeButton(R.string.about_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		})
		
	
		.show();
	}
	


}
