package com.example.map;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class MainActivity extends Activity
{
	MapView mMapView = null;
	BaiduMap mBaiduMap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		// ��ȡ��ͼ�ؼ�����
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		// ���ǵ�ͼ
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		mBaiduMap.setPadding(0, 0, 0, 0);

		Mark();
	}

	/**
	 * ��ͼ��ע
	 */
	void Mark()
	{
		// ����Maker�����
		LatLng point = new LatLng(39.963175, 116.400244);
		// ����Markerͼ��
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_launcher);
		// ����MarkerOption�������ڵ�ͼ�����Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap).zIndex(9);
		// �ڵ�ͼ�����Marker������ʾ
		mBaiduMap.addOverlay(option);

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener()
		{
			@Override
			public boolean onMarkerClick(Marker marker)
			{
				System.out.println("onMarkerClick");
				return false;
			}
		});
	}
}
