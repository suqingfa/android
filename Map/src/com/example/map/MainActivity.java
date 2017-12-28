package com.example.map;

import android.app.Activity;
import android.os.Bundle;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;

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

		// ��ͨ��ͼ
		//mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		// ���ǵ�ͼ
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

		// �հ׵�ͼ,
		// ������ͼ��Ƭ�����ᱻ��Ⱦ���ڵ�ͼ����������ΪNONE��������ʹ���������ػ�����ͼ��Ƭͼ�㡣ʹ�ó���������Ƭͼ��һ��ʹ�ã���ʡ�����������Զ�����Ƭͼ�����ٶȡ�
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

		// ������ͨͼ
		// mBaiduMap.setTrafficEnabled(true);

		// �ٶȳ�������ͼ
		// mBaiduMap.setBaiduHeatMapEnabled(true);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onDestroy();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mMapView.onPause();
	}
}
