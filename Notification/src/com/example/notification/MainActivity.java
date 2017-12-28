package com.example.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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

	public void Notification(View v)
	{
		NotificationManager manager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 发送通知
		// manager.notify(id, notification);
		// 取消通知
		// manager.cancel(id);

		Notification notification = new NotificationCompat.Builder(this)
				.setSmallIcon(android.R.drawable.sym_def_app_icon)
				.setContentTitle("通知栏标题")
				.setContentText("通知栏消息")
				.setTicker("来消息啦.....")
				.build();

		notification.defaults = 0xffffffff;
		manager.notify(111, notification);
	}
}
