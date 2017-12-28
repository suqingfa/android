package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;

public class MainActivity extends Activity
{
	private MySurfaceView mGLSurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGLSurfaceView = new MySurfaceView(this);
		mGLSurfaceView.requestFocus();
		mGLSurfaceView.setFocusable(true);

		((RelativeLayout) findViewById(R.id.rl)).addView(mGLSurfaceView);
	}
}
