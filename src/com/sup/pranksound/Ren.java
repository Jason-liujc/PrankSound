package com.sup.pranksound;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Ren extends Activity {

	private String[] human_description;
	private ArrayList<Content> items=new ArrayList<Content>();
	TPB progress;
	AssetFileDescriptor afd;
	private FileDescriptor fd;
	
	String pause="pause";
	String resume="resume";
	
	
	
	
	private MediaPlayer player=new MediaPlayer();
	public void formItems(){
		items.add(new Content("atchoum",human_description[0]));
		items.add(new Content("awkward",human_description[1]));
		items.add(new Content("awkard2",human_description[2]));
		items.add(new Content("burp",human_description[3]));
		items.add(new Content("burp2",human_description[4]));
		items.add(new Content("cat",human_description[5]));
		items.add(new Content("click",human_description[6]));
		items.add(new Content("cough",human_description[7]));
		items.add(new Content("dogbark",human_description[8]));
		items.add(new Content("fart with a song",human_description[9]));
		items.add(new Content("fart",human_description[10]));
		items.add(new Content("Ilikethat",human_description[11]));
		items.add(new Content("kiss",human_description[12]));
		items.add(new Content("moan",human_description[13]));
		items.add(new Content("snore",human_description[14]));
		items.add(new Content("spit",human_description[15]));
		items.add(new Content("yamedai",human_description[16]));
		items.add(new Content("ZZZZzzzzz",human_description[17]));
	}

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ren);
		player.setOnPreparedListener(preplis);
		player.setOnErrorListener(errlis);
		player.setOnCompletionListener(complistener);
		
		progress=(TPB)findViewById(R.id.human_progress);
		progress.setOnClickListener(progress_listener);
	    progress.setText(getString(R.string.choose_hint));
	    Resources res=getResources();
	    human_description=res.getStringArray(R.array.human);
	    formItems();
		
		ListView human_list=(ListView)findViewById(R.id.human_list);
		human_list.setAdapter(new ContentAdapter(this,R.layout.list_items_layout,items));
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_ren, menu);
		return true;
	}
	
	
	MediaPlayer.OnPreparedListener preplis=new MediaPlayer.OnPreparedListener(){

		@Override
		public void onPrepared(MediaPlayer mp) {
			Log.d("prank","player prepared!");
			player.setVolume(1, 1);
			player.start();
			Thread thread=new Thread(new Runnable(){

				@Override
				public void run() {
					while (player.isPlaying()==true){
						int mprogress=100*player.getCurrentPosition()/player.getDuration();
						progress.setProgress(mprogress);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				
			});
			thread.start();
			
		}
		
	};
	

	MediaPlayer.OnErrorListener errlis=new MediaPlayer.OnErrorListener(){

		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			Log.e("prank","error state");
			return false;
		}
		
	};
	
	
	
	MediaPlayer.OnCompletionListener complistener=new MediaPlayer.OnCompletionListener(){

		@Override
		public void onCompletion(MediaPlayer mp) {
			player.reset();
			progress.setProgress(0);
			progress.setText(getString(R.string.choose_hint));
			
		
}
	};
	private OnClickListener progress_listener=new OnClickListener(){

		public void onClick(View arg0) {
			if (progress.text==getString(R.string.choose_hint)){
				Toast.makeText(getApplicationContext(), getString(R.string.choose_warning), Toast.LENGTH_SHORT).show();
			}
			else{
				if (player.isPlaying()==true){
					player.pause();
					progress.setText(resume);
				}
				else{
					player.start();
					progress.setText(pause);
					Thread thread=new Thread(new Runnable(){

						@Override
						public void run() {
							while (player.isPlaying()==true){
								int mprogress=100*player.getCurrentPosition()/player.getDuration();
								progress.setProgress(mprogress);
								try {
									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
						
					});
					thread.start();
				}
			}
		}
	};	
	public class ContentAdapter extends ArrayAdapter<Content>{
		
		private ArrayList<Content> items;

		public ContentAdapter(Context context, int textViewResourceId, ArrayList<Content> objects) {
			super(context, textViewResourceId, objects);
			this.items=objects;
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v=convertView;
			TextView nm = null;
			TextView sm = null;
			if (v==null){
				LayoutInflater inflater=(LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
			v=inflater.inflate(R.layout.list_items_layout,null);
			}
			
			Content content=items.get(position);
			if (content!=null){
				nm=(TextView)v.findViewById(R.id.itemname);
				sm=(TextView)v.findViewById(R.id.itemsubname);
				nm.setTextColor(Color.RED);
				nm.setText(content.getName());
				sm.setTextColor(Color.GRAY);
				sm.setText(content.getSubname());
			}
			else{
				Log.d("prank","no content");
			}
			v.setOnClickListener(listener);
			nm.setOnClickListener(textlistener);
			sm.setOnClickListener(textlistener);
			
			return v;
		}
		private View.OnClickListener textlistener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				LinearLayout parent=(LinearLayout)v.getParent();
				TextView nameview=(TextView)parent.findViewById(R.id.itemname);
				String musicname=nameview.getText().toString();
				
				progress.setText(pause);
				
				player.reset();
				
				try {
					afd=getAssets().openFd(musicname+".mp3");
					Log.d("prank",musicname);
				} catch (IOException e1) {
					Log.e("prank","afd cant create "+e1.toString());
					e1.printStackTrace();
				}
				
				fd=afd.getFileDescriptor();
				Log.d("prank", "fd created"+fd.toString());
				
				try {
					player.setDataSource(fd,afd.getStartOffset(), afd.getDeclaredLength());
				} catch (IllegalArgumentException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				} catch (IllegalStateException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				} catch (IOException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				}
				
				try{
					fd=null;
				}catch(Exception e){
					Log.e("prank","fd release failed "+e.toString());
				}
				
				player.prepareAsync();
			}
		};
		private View.OnClickListener listener=new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TextView nameview=(TextView)v.findViewById(R.id.itemname);
				String musicname=nameview.getText().toString();
				Log.d("prank",musicname);
				
				progress.setText(pause);
				
				player.reset();
				
				try {
					afd=getAssets().openFd(musicname+".mp3");
				} catch (IOException e1) {
					Log.e("prank","afd cant create "+e1.toString());
					e1.printStackTrace();
				}
				
				fd=afd.getFileDescriptor();
				Log.d("prank", "fd created"+fd.toString());
				
				try {
					player.setDataSource(fd,afd.getStartOffset(), afd.getDeclaredLength());
				} catch (IllegalArgumentException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				} catch (IllegalStateException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				} catch (IOException e) {
					Log.e("prank","set data source failed "+e.toString());
					e.printStackTrace();
				}
				
				try{
					fd=null;
				}catch(Exception e){
					Log.e("prank","fd release failed "+e.toString());
				}
				
				player.prepareAsync();
			}
		};

	}
	

}

	
	
