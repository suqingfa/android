package com.example.g;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity implements BDLocationListener,
		SensorEventListener
{

	MapView mMapView = null;
	BaiduMap mBaiduMap = null;
	public LocationClient mLocationClient = null;

	TextView text;
	private SensorManager sm;

	// 需要两个Sensor
	private Sensor aSensor;
	private Sensor mSensor;

	// 罗盘
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		text = (TextView) findViewById(R.id.text);
		// 罗盘
		image = (ImageView) findViewById(R.id.img);

		// 注册事件监听器
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sm.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(this); // 注册监听函数

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		option.setScanSpan(1000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	@Override
	public void onReceiveLocation(BDLocation location)
	{
		// Receive Location
		StringBuffer sb = new StringBuffer(256);
		sb.append("time : ");
		sb.append(location.getTime());
		sb.append("\nlatitude : ");
		sb.append(location.getLatitude());
		sb.append("\nlontitude : ");
		sb.append(location.getLongitude());
		sb.append("\nradius : ");
		sb.append(location.getRadius());

		if (location.getLocType() == BDLocation.TypeGpsLocation)
		{// GPS定位结果
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());// 单位：公里每小时
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\nheight : ");
			sb.append(location.getAltitude());// 单位：米
			sb.append("\ndirection : ");
			sb.append(location.getDirection());// 单位度
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("gps定位成功");

		}
		else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
		{// 网络定位结果
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("网络定位成功");
		}
		Log.i("BaiduLocationApiDem", sb.toString());
		text.setText("位置：" + location.getAddrStr() + "\n经度："
				+ location.getLatitude() + "\n纬度" + location.getLongitude()
				+ "\n海拔：" + location.getAltitude() + "M\n速度："
				+ location.getSpeed() + "Km/h");

		// 构造定位数据
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				.latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();

		LatLng cenpt = new LatLng(location.getLatitude(),
				location.getLongitude());
		// 定义地图状态
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
				.rotate(rotate).build();
		// 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);

		// 改变地图状态
		mBaiduMap.setMyLocationData(locData);
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	// 保存手机方向和磁场大小
	float[] accelerometerValues = new float[3];
	float[] magneticFieldValues = new float[3];
	float rotate;

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
		rotate = values[0];

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

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mMapView.onDestroy();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mMapView.onPause();
	}
}
