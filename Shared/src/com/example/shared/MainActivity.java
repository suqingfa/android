package com.example.shared;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends Activity
{
	private SharedPreferences preferences;
	private EditText text;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferences = getSharedPreferences("MyPref", MODE_PRIVATE);

		text = (EditText) findViewById(R.id.edit);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		// ȡ������
		String string = preferences.getString("text", "text");

		text.setText(string);
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		String string = text.getText().toString();

		// ȡ��editor����
		SharedPreferences.Editor editor = preferences.edit();

		editor.putString("text", string);

		// ȷ��д���ļ�
		editor.commit();
	}
}
