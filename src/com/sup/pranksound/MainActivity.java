package com.sup.pranksound;


import java.io.FileDescriptor;
import java.io.IOException;


import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


public class MainActivity extends Activity {

	MediaPlayer player=null;
	AssetManager manager;
	AssetFileDescriptor afd;
	private FileDescriptor fd;
	SurfaceView surface;
	SurfaceHolder holder;

	
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		
		
		
		try{
			manager=getAssets();
			Log.d("prank","manager created!");
		}catch(Exception e){
			Log.e("prank","cant get manager");
		}
		
		try {
			afd=manager.openFd("intro movie.mp4");
			Log.d("prank","afd created!");
		} catch (IOException e1) {
			Log.e("prank", "afd IOexception "+e1.toString());
			e1.printStackTrace();
		}
		
		try{
			fd=afd.getFileDescriptor();
			Log.d("prank","fd created"+fd.toString());
		}catch(Exception e){
			Log.e("prank","cant create fd "+e.toString());
		}
		
		    
		try{
			player=new MediaPlayer();
		}catch(Exception e){
			Log.e("prank","mediaplayer cannot be created "+e.toString());
		}
		player.setOnPreparedListener(preplis);
		player.setOnErrorListener(errlis);
		player.setOnCompletionListener(complistener);
		
		
			
	
		try {
			player.setDataSource(fd,afd.getStartOffset(),afd.getDeclaredLength());
			Log.d("prank","fd set!!!");
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
			
		try {
			afd.close();
			fd=null;
			Log.d("prank","afd&fd released!");
		} catch (Exception e) {
			Log.e("prank","afd close failed "+e.toString());
			e.printStackTrace();
		}
			
		try{
			surface=(SurfaceView)findViewById(R.id.surface);
			Log.d("prank","surface found!");
		}catch(Exception e){
			Log.e("prank", "surfaceview err"+e.toString());
		}
		


			
		try{
			holder=surface.getHolder();
			Log.d("prank","getting holder!");
		}catch(Exception e){
			Log.e("prank", "surfaceholder err"+e.toString());
		}
		
		holder.setFixedSize(surface.getWidth(), surface.getHeight());
		holder.setKeepScreenOn(true);
		holder.addCallback(new SurfaceHolder.Callback() {
				
			@Override
			public void surfaceDestroyed(SurfaceHolder arg0) {
				Log.d("prank","surface destroyed");
			}
				
			@Override
			public void surfaceCreated(SurfaceHolder holder_1) {
				Log.d("prank","holder created!");
				holder=holder_1;
				try{
					player.setDisplay(holder);
				}catch(Exception e){
					Log.e("prank","set holder failed "+e.toString());
				}
				Log.d("prank","Display set!");
					
				player.prepareAsync();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
					Log.d("prank","surface changed");
			}
		});
	}	

	

	MediaPlayer.OnPreparedListener preplis=new MediaPlayer.OnPreparedListener(){

		@Override
		public void onPrepared(MediaPlayer mp) {
			Log.d("prank","player prepared!");
			player.setVolume(1, 1);
			player.start();
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
			player.release();
			finish();
			Intent intent=new Intent(MainActivity.this,Categories.class);
			startActivity(intent);
		}
		
	};
	
	

}
