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

	// ��Ҫ����Sensor
	private Sensor aSensor;
	private Sensor mSensor;

	// ����
	ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		text = (TextView) findViewById(R.id.text);
		// ����
		image = (ImageView) findViewById(R.id.img);

		// ע���¼�������
		sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		aSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mSensor = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

		sm.registerListener(this, aSensor, SensorManager.SENSOR_DELAY_NORMAL);
		sm.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);

		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(this); // ע���������

		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		option.setScanSpan(1000);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��

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
		{// GPS��λ���
			sb.append("\nspeed : ");
			sb.append(location.getSpeed());// ��λ������ÿСʱ
			sb.append("\nsatellite : ");
			sb.append(location.getSatelliteNumber());
			sb.append("\nheight : ");
			sb.append(location.getAltitude());// ��λ����
			sb.append("\ndirection : ");
			sb.append(location.getDirection());// ��λ��
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("gps��λ�ɹ�");

		}
		else if (location.getLocType() == BDLocation.TypeNetWorkLocation)
		{// ���綨λ���
			sb.append("\naddr : ");
			sb.append(location.getAddrStr());
			sb.append("\ndescribe : ");
			sb.append("���綨λ�ɹ�");
		}
		Log.i("BaiduLocationApiDem", sb.toString());
		text.setText("λ�ã�" + location.getAddrStr() + "\n���ȣ�"
				+ location.getLatitude() + "\nγ��" + location.getLongitude()
				+ "\n���Σ�" + location.getAltitude() + "M\n�ٶȣ�"
				+ location.getSpeed() + "Km/h");

		// ���춨λ����
		MyLocationData locData = new MyLocationData.Builder()
				.accuracy(location.getRadius())
				.latitude(location.getLatitude())
				.longitude(location.getLongitude()).build();

		LatLng cenpt = new LatLng(location.getLatitude(),
				location.getLongitude());
		// �����ͼ״̬
		MapStatus mMapStatus = new MapStatus.Builder().target(cenpt)
				.rotate(rotate).build();
		// ����MapStatusUpdate�����Ա�������ͼ״̬��Ҫ�����ı仯
		MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
				.newMapStatus(mMapStatus);

		// �ı��ͼ״̬
		mBaiduMap.setMyLocationData(locData);
		mBaiduMap.setMapStatus(mMapStatusUpdate);
	}

	// �����ֻ�����ʹų���С
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
		// ͨ���ֻ�����ʹų���С�����ֻ���Ե�����
		SensorManager.getRotationMatrix(R, null, accelerometerValues,
				magneticFieldValues);
		SensorManager.getOrientation(R, values);

		// Ҫ����һ�����ݸ�ʽ��ת����ת��Ϊ��
		values[0] = (float) Math.toDegrees(values[0]);
		values[1] = (float) Math.toDegrees(values[1]);
		values[2] = (float) Math.toDegrees(values[2]);
		rotate = values[0];

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
