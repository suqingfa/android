package com.example.motionevent;

import java.io.IOException;
import java.io.PrintStream;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity
{

	// ��ȡ�����
	PrintStream cout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try
		{
			// �����ȡrootȨ�ޣ���һ������Ҫ����Ȼ��û������
			Process process = Runtime.getRuntime().exec("su");
			cout = new PrintStream(process.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ִ��shell����
	 * 
	 * @param cmd
	 */
	private void ShellCmd(String cmd)
	{
		cout.println(cmd);
		// cout.flush();
	}

	public void onClickButton(View v)
	{
		// ShellCmd("input swipe 400 400 0 500");
		ShellCmd("input keyevent " + KeyEvent.KEYCODE_VOLUME_DOWN);
	}
}
