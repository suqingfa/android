package com.example.E_Compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author �� ��
 * 
 *         ͨ���ֻ�����ʹų���С�����ֻ���Ե�����
 * 
 * 
 */
public class MainActivity extends ActionBarActivity implements
		SensorEventListener
{
	private SensorManager sm;
	// ��Ҫ����Sensor
	private Sensor aSensor;
	private Sensor mSensor;

	private TextView m_textViewX;
	private TextView m_textViewY;
	private TextView m_textViewZ;

	// ����
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_textViewX = (TextView) findViewById(R.id.textViewX);
		m_textViewY = (TextView) findViewById(R.id.textViewY);
		m_textViewZ = (TextView) findViewById(R.id.textViewZ);

		// ����
		image = (ImageView) findViewById(R.id.imageView);

		// ע���¼�������
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sm.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

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

	// �����ֻ�����ʹų���С
	float[] accelerometerValues = new float[3];
	float[] magneticFieldValues = new float[3];

	@Override
	public void onSensorChanged(SensorEvent event)
	{
		if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
			magneticFieldValues = event.values;
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
			accelerometerValues = event.values;

		float[] R = new float[9];
		float[] values = new float[3];
		// ͨ���ֻ�����ʹų���С�����ֻ���Ե�����
		SensorManager.getRotationMatrix(R, null, accelerometerValues,
				magneticFieldValues);
		SensorManager.getOrientation(R, values);

		// Ҫ����һ�����ݸ�ʽ��ת����ת��Ϊ��
		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]);
		values[2] = (float) Math.toDegrees(values[2]);

		// �����
		m_textViewX.setText("X=" + values[0]);
		// ǰ��ƫת
		m_textViewY.setText("Y=" + values[1]);
		// ����ƫת
		m_textViewZ.setText("Z=" + values[2]);

		// ��ת����
		RotateAnimation animation = new RotateAnimation(-values[0], -values[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(200);// ���ö�������ʱ��
		image.setAnimation(animation);
		animation.startNow();

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}
