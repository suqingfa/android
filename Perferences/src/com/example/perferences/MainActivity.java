package com.example.perferences;

import java.util.Date;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity
{
TextView text;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView) findViewById(R.id.text);
		
		SharedPreferences preferences = this.getSharedPreferences("share", MODE_PRIVATE);
		
		String str = preferences.getString("str", null);
		if(str == null)
			str = "第一次登录";
		else
			str = "上次运行时间："+str;
		text.setText(str);
		
		SharedPreferences.Editor edit = preferences.edit();
		edit.putString("str", new Date().toLocaleString());
		edit.commit();
	}
}
