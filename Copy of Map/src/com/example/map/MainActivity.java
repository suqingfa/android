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

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		// 卫星地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
		mBaiduMap.setPadding(0, 0, 0, 0);

		Mark();
	}

	/**
	 * 地图标注
	 */
	void Mark()
	{
		// 定义Maker坐标点
		LatLng point = new LatLng(39.963175, 116.400244);
		// 构建Marker图标
		BitmapDescriptor bitmap = BitmapDescriptorFactory
				.fromResource(R.drawable.ic_launcher);
		// 构建MarkerOption，用于在地图上添加Marker
		OverlayOptions option = new MarkerOptions().position(point)
				.icon(bitmap).zIndex(9);
		// 在地图上添加Marker，并显示
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
