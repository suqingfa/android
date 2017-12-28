package com.example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends Activity
{
	MySurfaceView mySurfaceView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mySurfaceView = new MySurfaceView(this);
		mySurfaceView.requestFocus();
		mySurfaceView.setFocusable(true);

		((LinearLayout) findViewById(R.id.lla)).addView(mySurfaceView);

		((ToggleButton) findViewById(R.id.ToggleButton01))
				.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked)
					{
						mySurfaceView.openLightFlag = isChecked;
					}
				});
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mySurfaceView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mySurfaceView.onResume();
	}
}
