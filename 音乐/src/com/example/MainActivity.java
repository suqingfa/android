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
	// ��Ч���ſ�����
	SoundPool sp;
	int id;
	
	// ���ֲ��ſ�����
	AudioManager am;
	MediaPlayer mp = new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
		// ����Դ������Ч�ļ�
		id = sp.load(this, R.raw.login, 1);
		
		am = (AudioManager) this.getSystemService(AUDIO_SERVICE);
		
		// ��������
		// am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
		// ��ȡ�������
		//am.getStreamMaxVolume(streamType);
		//��ȡ��ǰ����
		//am.getStreamVolume(streamType);
		
		try
		{
			mp.setDataSource("/sdcard/music.mid");
			mp.prepare();
			mp.start();
			//mp.pause();
			//mp.stop();
			//mp.reset();
			
			System.out.println("��ʼ��������");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * ���ڲ��Ž϶�С����Ч�ļ�
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
