package com.example.gl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

public class Graph
{
	private IntBuffer vertexBuffer;// �������껺��
	private IntBuffer colorBuffer;// ��ɫ��ɫ����
	private ByteBuffer indexBuffer; // ������������

	private int iCount;// ������Ŀ

	float yAngle;// ��Y��ת
	float zAngle;// ��Z��ת

	public Graph()
	{
		final int UNIT_SIZE = 8000;
		int vertices[] =
		{//
		-8 * UNIT_SIZE, 6 * UNIT_SIZE, 0,//
				-8 * UNIT_SIZE, -6 * UNIT_SIZE, 0,//
				8 * UNIT_SIZE, -6 * UNIT_SIZE, 0, //
				8 * UNIT_SIZE, 6 * UNIT_SIZE, 0, //
		};

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asIntBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		final int one = 65535;
		int colors[] =
		{//
		0, one, one, 0,//
				one, 0, one, 0,//
				one, one, 0, 0, //
				one, 0, 0, 0,//
		};

		ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		colorBuffer = cbb.asIntBuffer();
		colorBuffer.put(colors);
		colorBuffer.position(0);

		byte indices[] =
		{ 0, 1, 2, 3 };
		iCount = indices.length;
		indexBuffer = ByteBuffer.allocateDirect(indices.length);
		indexBuffer.put(indices);
		indexBuffer.position(0);

		// ��תͼ��
		new Timer().schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				zAngle += 0.5;
			}
		}, 0, 10);
	}

	public void draw(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// ���ö�����������
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);// ���ö�����ɫ����

		// ������ϵ��z������ƽ��2����λ
		gl.glTranslatef(0, 0, -2);

		// ��ת
		gl.glRotatef(yAngle, 0, 1, 0);
		gl.glRotatef(zAngle, 0, 0, 1);

		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);// ����
		gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);// ��ɫ

		// ����ͼ��
		gl.glDrawElements(GL10.GL_LINE_LOOP, iCount, GL10.GL_UNSIGNED_BYTE,
				indexBuffer);
	}
}