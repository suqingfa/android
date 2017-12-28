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

		// �ж�GPS�Ƿ���������
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
		{
			Toast.makeText(this, "�뿪��GPS����...", Toast.LENGTH_SHORT).show();
			// ���ؿ���GPS�������ý���
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivityForResult(intent, 0);
			return;
		}

		// ״̬����
		locationManager.addGpsStatusListener(this);

		// �󶨼�������4������
		// ����1���豸����GPS_PROVIDER��NETWORK_PROVIDER����
		// ����2��λ����Ϣ�������ڣ���λ����
		// ����3��λ�ñ仯��С���룺��λ�þ���仯������ֵʱ��������λ����Ϣ
		// ����4������
		// ��ע������2��3���������3��Ϊ0�����Բ���3Ϊ׼������3Ϊ0����ͨ��ʱ������ʱ���£�����Ϊ0������ʱˢ��
		// 1�����һ�Σ�����Сλ�Ʊ仯����1�׸���һ�Σ�
		// ע�⣺�˴�����׼ȷ�ȷǳ��ͣ��Ƽ���service��������һ��Thread����run��sleep(10000);Ȼ��ִ��handler.sendMessage(),����λ��
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
	 * GPSλ����Ϣ�仯ʱ����
	 * 
	 */
	@Override
	public void onLocationChanged(Location location)
	{

		/**
		 * ������ȡλ����
		 * 
		 * Location lct =
		 * locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		 * 
		 */

		if (location == null)
		{
			textView.setText("��ѯʧ��....");
			return;
		}

		String str = "";

		SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");

		str = "ʱ��" + fmt.format(location.getTime()) + "\n����"
				+ location.getLongitude() + "\nγ��" + location.getLatitude()
				+ "\n����" + location.getAltitude();

		textView.setText(str);
	}

	/**
	 * GPS״̬�仯ʱ����
	 * 
	 */
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		switch (status)
		{
		// ״̬Ϊ�ɼ�ʱ
		case LocationProvider.AVAILABLE:
			break;

		// ״̬Ϊ��������
		case LocationProvider.OUT_OF_SERVICE:
			break;

		// ����Ϊ��ͣʱ
		case LocationProvider.TEMPORARILY_UNAVAILABLE:
			break;
		}
	}

	/**
	 * GPS����ʱ����
	 */
	@Override
	public void onProviderEnabled(String provider)
	{
		/**
		 * location = locationManager.getLastKnownLocation(provider);
		 */
	}

	/**
	 * GPS ����ʱ����
	 */
	@Override
	public void onProviderDisabled(String provider)
	{

	}

	// GPS״̬����
	@Override
	public void onGpsStatusChanged(int event)
	{
		switch (event)
		{
		// ��һ�ζ�λ
		case GpsStatus.GPS_EVENT_FIRST_FIX:
			Log.i(TAG, "��һ�ζ�λ");
			textViewStatus.setText("��һ�ζ�λ");
			break;
		// ����״̬�ı�
		case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
			Log.i(TAG, "����״̬�ı�");
			// ��ȡ��ǰ״̬
			GpsStatus gpsStatus = locationManager.getGpsStatus(null);
			// ��ȡ���ǿ�����Ĭ�����ֵ
			int maxSatellites = gpsStatus.getMaxSatellites();
			// ����һ��������������������
			Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
			int count = 0;
			while (iters.hasNext() && count <= maxSatellites)
			{
				GpsSatellite s = iters.next();
				count++;
			}
			System.out.println("��������" + count + "������");
			textViewStatus.setText("��������" + count + "������");
			break;
		// ��λ����
		case GpsStatus.GPS_EVENT_STARTED:
			Log.i(TAG, "��λ����");
			textViewStatus.setText("��λ����");
			break;
		// ��λ����
		case GpsStatus.GPS_EVENT_STOPPED:
			Log.i(TAG, "��λ����");
			textViewStatus.setText("��λ����");
			break;
		}
	}
}
