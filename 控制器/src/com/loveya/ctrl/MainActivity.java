package com.loveya.ctrl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

public class MainActivity extends Activity
{
	// 更新UI
	Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 接收线程
		new Thread(new UDP()).start();
	}

	

	public void Kugou(View v)
	{
		Intent intent = new Intent();
		intent.setClass(this, KugouActivity.class);
		
		startActivity(intent);
	}
	
	public void Computer(View v)
	{
		Intent intent = new Intent();
		intent.setClass(this, ComputerActivity.class);
		
		startActivity(intent);
	}
}
