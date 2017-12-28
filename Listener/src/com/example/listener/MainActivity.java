package com.example.listener;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 单击事件
		/*
		 * Button button = (Button) findViewById(R.id.button);
		 * button.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * Toast.makeText(MainActivity.this, "点击了按钮", Toast.LENGTH_SHORT)
		 * .show(); } });
		 */
		Button button1 = (Button) findViewById(R.id.button1);
		Button button2 = (Button) findViewById(R.id.button2);
		Button button3 = (Button) findViewById(R.id.button3);
		button1.setOnClickListener(click);
		button2.setOnClickListener(click);
		button3.setOnClickListener(click);

		Button button4 = (Button) findViewById(R.id.button4);
		/*
		 * button4.setOnLongClickListener(new OnLongClickListener() {
		 * 
		 * @Override public boolean onLongClick(View arg0) {
		 * System.out.println("长按按钮"); return true; } });
		 * button4.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) {
		 * System.out.println("点击按钮"); } });
		 */

		/*
		 * button4.setOnTouchListener(new OnTouchListener() {
		 * 
		 * @Override public boolean onTouch(View arg, MotionEvent e) { int type
		 * = e.getAction(); if (type == MotionEvent.ACTION_DOWN) {
		 * System.out.println("按下"); } else if (type == MotionEvent.ACTION_UP) {
		 * System.out.println("松开"); } else if (type == MotionEvent.ACTION_MOVE)
		 * { System.out.println("移动"); } return false; } });
		 */

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.Layout);
		viewGroup.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1)
			{
				Button button4 = (Button) findViewById(R.id.button4);
				button4.setX(arg1.getX());
				button4.setY(arg1.getY());
				return false;
			}
		});
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

	public OnClickListener click = new OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			TextView txt = (TextView) findViewById(R.id.TextView);

			switch (v.getId())
			{
			case R.id.button1:
				txt.setText("点击了按钮1");
				break;
			case R.id.button2:
				txt.setText("点击了按钮2");
				break;
			case R.id.button3:
				txt.setText("点击了按钮3");
				break;
			}
		}
	};
}
