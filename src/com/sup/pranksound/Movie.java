package com.sup.pranksound;

import java.io.FileDescriptor;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
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

public class Movie extends Activity {

	
	private ArrayList<Content> items=new ArrayList<Content>();
	
	TPB progress;
	
	AssetFileDescriptor afd;
	private FileDescriptor fd;
	
	String pause="pause";
	String resume="Resume";
	
	private MediaPlayer player=new MediaPlayer();

    public void formItems(){
    	
    	items.add(new Content("false","Dwight says False!"));
    	items.add(new Content("force", "may the force be with you"));
    	items.add(new Content("Batman","Neeerds"));
    	items.add(new Content("ChallengeAccepted","Let's pick up some chicks!"));
    	items.add(new Content("HowUDoing","Ladies dig this"));
    	items.add(new Content("HungerGame","Just like break a leg but without breaking th leg"));
    	items.add(new Content("Legal","DiCaprio on 'is it legal?'"));
    	items.add(new Content("MustHaveThis","*pointing at your friend's boobs*"));
    	items.add(new Content("NobodyLikeYou","A good way to play it to your friend"));
    	items.add(new Content("Sharted","wag of the finger to those who use this sound"));
    	items.add(new Content("WaitForIt","How long do I have to wait?"));
    	
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_movie);
		player.setOnPreparedListener(preplis);
		player.setOnErrorListener(errlis);
		player.setOnCompletionListener(complistener);
		
		progress=(TPB)findViewById(R.id.movie_progress);
		progress.setOnClickListener(progress_listener);
		progress.setText(getString(R.string.choose_hint));
			
	    	formItems();
			
	       
			
	        ListView movie_list=(ListView)findViewById(R.id.movie_list);
			movie_list.setAdapter(new ContentAdapter(this, R.layout.list_items_layout,items));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_movie, menu);
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
				v=inflater.inflate(R.layout.list_items_layout, null);
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
