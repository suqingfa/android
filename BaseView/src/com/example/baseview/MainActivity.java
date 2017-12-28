package com.example.baseview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity
{

	private int imgId[] =
	{ R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4 };
	int index = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		EditText txt = (EditText) findViewById(R.id.editText1);
		/*
		 * txt.addTextChangedListener(new TextWatcher() {
		 * 
		 * @Override public void onTextChanged(CharSequence arg0, int arg1, int
		 * arg2, int arg3) { }
		 * 
		 * @Override public void beforeTextChanged(CharSequence arg0, int arg1,
		 * int arg2, int arg3) { }
		 * 
		 * @Override public void afterTextChanged(Editable arg0) { String str =
		 * arg0.toString(); Toast.makeText(getApplicationContext(), str,
		 * 0).show(); } });
		 */

		final int width = this.getWindowManager().getDefaultDisplay()
				.getWidth();

		ImageView imgView = (ImageView) findViewById(R.id.imageView1);
		imgView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				ImageView v = (ImageView) arg0;
				int x = (int) arg1.getX();

				if (2 * x < width)
				{
					index--;
				}
				else
				{
					index++;
				}
				if (index < 0)
					index = 3;
				if (index > 3)
					index = 0;
				v.setImageResource(imgId[index]);

				return true;
			}
		});

		/*
		 * µã»÷¸Ä±äÍ¼Æ¬ imgView.setOnClickListener(new OnClickListener() { int i = 0;
		 * 
		 * @Override public void onClick(View arg0) { ImageView v = (ImageView)
		 * arg0;
		 * 
		 * i++; i %= imgId.length;
		 * 
		 * v.setImageResource(imgId[i]); } });
		 */
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
