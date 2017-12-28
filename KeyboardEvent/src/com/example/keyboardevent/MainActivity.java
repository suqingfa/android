package com.example.keyboardevent;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.text);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		textView.setText("keydown " + keyCode);

		if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			Toast.makeText(this, "°´ÏÂÁËmenu¼ü", Toast.LENGTH_LONG).show();
		}
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		textView.setText("keyup " + keyCode);
		return true;
	}
}
