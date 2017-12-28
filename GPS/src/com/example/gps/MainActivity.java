package com.example.gps;

import java.text.SimpleDateFormat;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements
		LocationListener, GpsStatus.Listener
{
	final String TAG = "GPS";

	private TextView textView;
	private TextView textViewStatus;

	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView = (TextView) findViewById(R.id.textView);
		textViewStatus = (TextView) findViewById(R.id.textViewStatus);

		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);

		// 判断GPS是否正常启动
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();
			// 返回开启GPS导航设置界面
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
			return;
		}

		// 状态监听
		locationManager.addGpsStatusListener(this);

		// 绑定监听，有4个参数
		// 参数1，设备：有GPS_PROVIDER和NETWORK_PROVIDER两种
		// 参数2，位置信息更新周期，单位毫秒
		// 参数3，位置变化最小距离：当位置距离变化超过此值时，将更新位置信息
		// 参数4，监听
		// 备注：参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
		// 1秒更新一次，或最小位移变化超过1米更新一次；
		// 注意：此处更新准确度非常低，推荐在service里面启动一个Thread，在run中sleep(10000);然后执行handler.sendMessage(),更新位置
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 0, this);

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

	/**
	 * 
	 * GPS位置信息变化时触发
	 * 
	 */
	@Override
	public void onLocationChanged(Location location)
	{

		/**
		 * 主动获取位置用
		 * 
		 * Location lct =
		 * locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 * 
		 */

		if (location == null)
		{
			textView.setText("查询失败....");
			return;
		}

		String str = "";

		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");

		str = "时间" + fmt.format(location.getTime()) + "\n经度"
				+ location.getLongitude() + "\n纬度" + location.getLatitude()
				+ "\n海拔" + location.getAltitude();

		textView.setText(str);
	}

	/**
	 * GPS状态变化时触发
	 * 
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		switch (status)
		{
		// 状态为可见时
		case LocationProvider.AVAILABLE:
			break;

		// 状态为服务区外
		case LocationProvider.OUT_OF_SERVICE:
			break;

		// 服务为暂停时
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			break;
		}
	}

	/**
	 * GPS开启时触发
	 */
	@Override
	public void onProviderEnabled(String provider)
	{
		/**
		 * location = locationManager.getLastKnownLocation(provider);
		 */
	}

	/**
	 * GPS 禁用时触发
	 */
	@Override
	public void onProviderDisabled(String provider)
	{

	}

	// GPS状态监听
	@Override
	public void onGpsStatusChanged(int event)
	{
		switch (event)
		{
		// 第一次定位
		case GpsStatus.GPS_EVENT_FIRST_FIX:
			Log.i(TAG, "第一次定位");
			textViewStatus.setText("第一次定位");
			break;
		// 卫星状态改变
		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
			Log.i(TAG, "卫星状态改变");
			// 获取当前状态
			GpsStatus gpsStatus = locationManager.getGpsStatus(null);
			// 获取卫星颗数的默认最大值
			int maxSatellites = gpsStatus.getMaxSatellites();
			// 创建一个迭代器保存所有卫星
			Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
			int count = 0;
			while (iters.hasNext() && count <= maxSatellites)
			{
				GpsSatellite s = iters.next();
				count++;
			}
			System.out.println("搜索到：" + count + "颗卫星");
			textViewStatus.setText("搜索到：" + count + "颗卫星");
			break;
		// 定位启动
		case GpsStatus.GPS_EVENT_STARTED:
			Log.i(TAG, "定位启动");
			textViewStatus.setText("定位启动");
			break;
		// 定位结束
		case GpsStatus.GPS_EVENT_STOPPED:
			Log.i(TAG, "定位结束");
			textViewStatus.setText("定位结束");
			break;
		}
	}
}
