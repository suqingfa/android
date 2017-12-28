package com.example.uithread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

public class MainActivity extends ActionBarActivity
{

	private Button button2 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 移动按钮
		Button button1 = (Button) findViewById(R.id.button1);
		TranslateAnimation tran = new TranslateAnimation(0, 150, 0, 0);
		tran.setRepeatCount(30);
		tran.setDuration(1000);
		button1.setAnimation(tran);

		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new Task().execute();
			}
		});
	}

	// 异步任务
	public class Task extends AsyncTask<Object, Object, Integer>
	{
		@Override
		protected void onPostExecute(Integer result)
		{
			button2.setText("Thread Stop" + result);
			super.onPostExecute(result);
		}

		@Override
		protected Integer doInBackground(Object... arg0)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			return 100;
		}
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
