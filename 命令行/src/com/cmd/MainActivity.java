package com.cmd;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity
{
	// ��ȡ�����
	PrintStream cout;
	Scanner cin;

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
			cin = new Scanner(process.getInputStream());
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
	}

}
