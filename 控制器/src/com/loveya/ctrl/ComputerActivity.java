package com.loveya.ctrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ComputerActivity extends Activity
{
	final static byte CTRL_SHUTDOWN = 50; // �ػ�
	final static byte CTRL_UNSHUTDOWN = 51; // ȡ���ػ�
	final static byte CTRL_LOCK_COMPUTER = 52; // ��������
	final static byte CTRL_LOCK_KEY = 53;// ��������
	final static byte CTRL_UNLOCK_KEY = 54;// ��������
	final static byte CTRL_LOCK_MOUSE = 55;// �������
	final static byte CTRL_UNLOCK_MOUSE = 56;// �������
	final static byte CTRL_CLOSE_SCREEN = 57;// �ر���Ļ
	final static byte CTRL_OPEN_SCREEN = 58;// ����Ļ
	final static byte CTRL_CMD = 60; //������

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
