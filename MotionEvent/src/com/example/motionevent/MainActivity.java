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

	// 获取输出流
	PrintStream cout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		try
		{
			// 申请获取root权限，这一步很重要，不然会没有作用
			Process process = Runtime.getRuntime().exec("su");
			cout = new PrintStream(process.getOutputStream());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 执行shell命令
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
