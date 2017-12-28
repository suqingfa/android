package com.loveya.ctrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ComputerActivity extends Activity
{
	final static byte CTRL_SHUTDOWN = 50; // 关机
	final static byte CTRL_UNSHUTDOWN = 51; // 取消关机
	final static byte CTRL_LOCK_COMPUTER = 52; // 锁定电脑
	final static byte CTRL_LOCK_KEY = 53;// 锁定键盘
	final static byte CTRL_UNLOCK_KEY = 54;// 解锁键盘
	final static byte CTRL_LOCK_MOUSE = 55;// 锁定鼠标
	final static byte CTRL_UNLOCK_MOUSE = 56;// 解锁鼠标
	final static byte CTRL_CLOSE_SCREEN = 57;// 关闭屏幕
	final static byte CTRL_OPEN_SCREEN = 58;// 打开屏幕
	final static byte CTRL_CMD = 60; //命令行

	byte buf[] = new byte[1024];

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_computer);
	}

	public void close(View v)
	{
		buf[0] = CTRL_SHUTDOWN;
		UDP.send(buf, 1);
	}

	public void clock(View v)
	{
		buf[0] = CTRL_LOCK_COMPUTER;
		UDP.send(buf, 1);
	}

	public void closeScreen(View v)
	{
		buf[0] = CTRL_CLOSE_SCREEN;
		UDP.send(buf, 1);
	}
}
