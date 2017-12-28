package com.loveya.ctrl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import android.os.Message;

public class UDP implements Runnable
{
	static DatagramSocket socket;
	static InetAddress host;
	static int port = 8888;
	
	static
	{
		try
		{
			socket = new DatagramSocket();
			host = InetAddress.getByName("192.168.1.101");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	// 接收线程
	@Override
	public void run()
	{
		byte buf[] = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);

		while (true)
		{
			try
			{
				socket.receive(packet);
				Message msg = new Message();
				msg.obj = new String(packet.getData(), 0,
						packet.getLength());
				
				//handler.sendMessage(msg);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	static void send(final byte[] data, final int len)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					DatagramPacket packet = new DatagramPacket(data, len, host,
							port);

					socket.send(packet);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}).start();
	}
}
