package com.example.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener
{
	private TextView textView;

	// 传感器管理
	SensorManager sensorManager;
	private Sensor sensor;

	// 传感器类型
	final int sensorType = Sensor.TYPE_LIGHT;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);

		// 取得SensorManager
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

		// 传感器类型
		sensor = sensorManager.getDefaultSensor(sensorType);
		if (null == sensor)
		{
			Log.d("TAG", "deveice not support SensorManager");
			textView.setText("deveice not support SensorManager");
			return;
		}

		// 注册SensorListener
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	protected void onDestroy()
	{
		// 取消注册SensorListener
		sensorManager.unregisterListener(this);
		super.onDestroy();
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

	// 传感器数据变化时调用
	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor == null)
			return;
		if (event.sensor.getType() != sensorType)
			return;

		String text = "";

		text += event.sensor;

		for (int i = 0; i < event.values.length; i++)
		{
			text += "\nvalues[" + i + "]= " + event.values[i];
		}

		textView.setText(text);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}
