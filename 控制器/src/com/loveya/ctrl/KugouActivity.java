package com.loveya.ctrl;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class KugouActivity extends Activity
{
	final static byte CTRL_KUGOU = 59;

	final static byte KUGOU_START = 0;
	final static byte KUGOU_EXIT = 1;
	final static byte KUGOU_PLAY = 2;
	final static byte KUGOU_NEXT = 3;
	final static byte KUGOU_PREV = 4;

	byte buf[] = new byte[2];

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kugou);

		buf[0] = CTRL_KUGOU;
		buf[1] = KUGOU_START;

		UDP.send(buf, buf.length);
	}

	public void play(View v)
	{
		buf[1] = KUGOU_PLAY;
		UDP.send(buf, buf.length);
	}

	public void exit(View v)
	{
		buf[1] = KUGOU_EXIT;
		UDP.send(buf, buf.length);
	}

	public void next(View v)
	{
		buf[1] = KUGOU_NEXT;
		UDP.send(buf, buf.length);
	}

	public void prev(View v)
	{
		buf[1] = KUGOU_PREV;
		UDP.send(buf, buf.length);
	}
}
