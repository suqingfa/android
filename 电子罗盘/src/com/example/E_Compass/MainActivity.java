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
 * @author 多 才
 * 
 *         通过手机方向和磁场大小计算手机相对地球方向
 * 
 * 
 */
public class MainActivity extends ActionBarActivity implements
		SensorEventListener
{
	private SensorManager sm;
	// 需要两个Sensor
	private Sensor aSensor;
	private Sensor mSensor;

	private TextView m_textViewX;
	private TextView m_textViewY;
	private TextView m_textViewZ;

	// 罗盘
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		m_textViewX = (TextView) findViewById(R.id.textViewX);
		m_textViewY = (TextView) findViewById(R.id.textViewY);
		m_textViewZ = (TextView) findViewById(R.id.textViewZ);

		// 罗盘
		image = (ImageView) findViewById(R.id.imageView);

		// 注册事件监听器
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

	// 保存手机方向和磁场大小
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
		// 通过手机方向和磁场大小计算手机相对地球方向
		SensorManager.getRotationMatrix(R, null, accelerometerValues,
				magneticFieldValues);
		SensorManager.getOrientation(R, values);

		// 要经过一次数据格式的转换，转换为度
		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]);
		values[2] = (float) Math.toDegrees(values[2]);

		// 方向角
		m_textViewX.setText("X=" + values[0]);
		// 前后偏转
		m_textViewY.setText("Y=" + values[1]);
		// 左右偏转
		m_textViewZ.setText("Z=" + values[2]);

		// 旋转罗盘
		RotateAnimation animation = new RotateAnimation(-values[0], -values[0],
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setDuration(200);// 设置动画持续时间
		image.setAnimation(animation);
		animation.startNow();

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy)
	{
	}
}
