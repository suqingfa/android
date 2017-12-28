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

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		// 普通地图
		//mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		// 卫星地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

		// 空白地图,
		// 基础地图瓦片将不会被渲染。在地图类型中设置为NONE，将不会使用流量下载基础地图瓦片图层。使用场景：与瓦片图层一起使用，节省流量，提升自定义瓦片图下载速度。
		// mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);

		// 开启交通图
		// mBaiduMap.setTrafficEnabled(true);

		// 百度城市热力图
		// mBaiduMap.setBaiduHeatMapEnabled(true);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
		mMapView.onDestroy();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}
}
