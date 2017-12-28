package com.example;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements
		SensorEventListener
{
	private SensorManager m_SensorManager;
	private Sensor m_Sensor;

	private TextView m_textViewX;
	private TextView m_textViewY;
	private TextView m_textViewZ;

	private ImageButton button;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		m_textViewX = (TextView) findViewById(R.id.textViewX);
		m_textViewY = (TextView) findViewById(R.id.textViewY);
		m_textViewZ = (TextView) findViewById(R.id.textViewZ);

		button = (ImageButton) findViewById(R.id.imageButton);

		// 添加传感器
		m_SensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		m_Sensor = m_SensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);// TYPE_GRAVITY
		if (null == m_SensorManager)
		{
			Log.d("TAG", "deveice not support SensorManager");
		}
		// 参数三，检测的精准度
		m_SensorManager.registerListener(this, m_Sensor,
				SensorManager.SENSOR_DELAY_GAME);// SENSOR_DELAY_GAME
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

	float x;
	float y;
	float z;

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor == null)
		{
			return;
		}

		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
		{
			x = event.values[0];
			y = event.values[1];
			z = event.values[2];

			/*
			 * m_textViewX.setText("X=" + String.valueOf(x));
			 * m_textViewY.setText("Y=" + String.valueOf(y));
			 * m_textViewZ.setText("Z=" + String.valueOf(z));
			 */

			button.setX(button.getX() - x);
			button.setY(button.getY() + y);
		}
	}

	public void onClickButton(View v)
	{

		m_textViewX.setText("X=" + String.valueOf(x));
		m_textViewY.setText("Y=" + String.valueOf(y));
		m_textViewZ.setText("Z=" + String.valueOf(z));

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}
