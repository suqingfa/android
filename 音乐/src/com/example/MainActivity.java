package com.example;

import java.io.IOException;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity
{
	// 音效播放控制器
	SoundPool sp;
	int id;
	
	// 音乐播放控制器
	AudioManager am;
	MediaPlayer mp = new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		// 从资源加载音效文件
		id = sp.load(this, R.raw.login, 1);
		
		am = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		
		// 增大音量
		// am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
		// 获取最大音量
		//am.getStreamMaxVolume(streamType);
		//获取当前音量
		//am.getStreamVolume(streamType);
		
		try
		{
			mp.setDataSource("/sdcard/music.mid");
			mp.prepare();
			mp.start();
			//mp.pause();
			//mp.stop();
			//mp.reset();
			
			System.out.println("开始播放音乐");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * 用于播放较短小的音效文件
	 */
	public void play(View v)
	{
		sp.play(id, 10, 10, 1, 0, 1);
	}

	public void pause(View v)
	{
		sp.pause(id);
	}
}
