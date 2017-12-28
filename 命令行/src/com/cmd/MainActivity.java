package com.cmd;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
	// 获取输出流
	PrintStream cout;
	Scanner cin;

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
			cin = new Scanner(process.getInputStream());
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
	}

}
