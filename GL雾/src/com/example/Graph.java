package com.example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.opengles.GL10;

public class Graph
{
	private IntBuffer vertexBuffer;// 顶点坐标缓冲
	private FloatBuffer textureBuffer;

	private int vCount;// 索引数目

	float zAngle;// 绕Z旋转
	float z = -1.5f;

	int textureId;

	public Graph(int textureId)
	{
		this.textureId = textureId;

		final int UNIT_SIZE = 30000;
		vCount = 3;
		int vertices[] =
		{//
		2 * UNIT_SIZE, 0, 0,//
				-2 * UNIT_SIZE, 0, 0,//
				0, 4 * UNIT_SIZE, 0 //
		};

		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());
		vertexBuffer = vbb.asIntBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		// 顶点纹理S、T坐标
		float textureCoors[] =
		{ 0, 1, 1, 1, 0.5f, 0 };
		ByteBuffer cbb = ByteBuffer.allocateDirect(textureCoors.length * 4);
		cbb.order(ByteOrder.nativeOrder());
		textureBuffer = cbb.asFloatBuffer();
		textureBuffer.put(textureCoors);
		textureBuffer.position(0);

		// 旋转图形
		new Timer().schedule(new TimerTask()
		{
			boolean f = true;

			@Override
			public void run()
			{
				//zAngle += 0.5;
				if (z > -1)
					f = true;
				if (z < -5)
					f = false;

				if (f)
					z -= 0.03;
				else
					z += 0.03;
			}
		}, 0, 10);
	}

	public void draw(GL10 gl)
	{
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// 启用顶点坐标数组
		gl.glEnable(GL10.GL_TEXTURE_2D);// 启用纹理
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);// 允许使用纹理数组

		// 把坐标系向z负方向平衡2个单位
		gl.glTranslatef(0, 0, z);

		// 缩放
		gl.glScalef(1f, 1f, 1);

		// 旋转
		gl.glRotatef(zAngle, 0, 0, 1);

		gl.glVertexPointer(3, GL10.GL_FIXED, 0, vertexBuffer);// 顶点
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);

		gl.glDisable(GL10.GL_TEXTURE_2D);
	}
}