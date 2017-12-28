package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity
{
	SurfaceView surfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		surfaceView = new SurfaceView(this);
		surfaceView.requestFocus(); // 获取焦点
		surfaceView.setFocusable(true);// 设置为可触控

		((RelativeLayout) findViewById(R.id.rl)).addView(surfaceView);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		surfaceView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		surfaceView.onResume();
	}
}
