package com.example;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class CubeVertex
{

	private FloatBuffer mVertexBuffer;// �����������ݻ���
	private FloatBuffer mNormalBuffer;// ���������ݻ���
	public float mOffsetX;
	public float mOffsetY;// ��Y����ת
	float scale; // ������߶�
	int vCount;// ��������

	public CubeVertex(float scale, float length, float width)
	{
		this.scale = scale;
		vCount = 36;
		float UNIT_SIZE = 0.5f;
		float UNIT_HIGHT = 0.5f;
		float[] verteices =
		{

				// ����
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,

				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,

				// ����
				-UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				-UNIT_SIZE * width,

				UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				-UNIT_SIZE * width,

				// ǰ��
				-UNIT_SIZE * length, UNIT_HIGHT * scale, UNIT_SIZE * width,
				-UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,

				UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				-UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				UNIT_SIZE * width,

				// ����
				-UNIT_SIZE * length, -UNIT_HIGHT * scale, UNIT_SIZE * width,
				-UNIT_SIZE * length, -UNIT_HIGHT * scale, -UNIT_SIZE * width,
				UNIT_SIZE * length, -UNIT_HIGHT * scale, UNIT_SIZE * width,

				UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				-UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				-UNIT_SIZE * width,
				UNIT_SIZE * length,
				-UNIT_HIGHT * scale,
				-UNIT_SIZE * width,

				// ����
				-UNIT_SIZE * length, -UNIT_HIGHT * scale, -UNIT_SIZE * width,
				-UNIT_SIZE * length, -UNIT_HIGHT * scale, UNIT_SIZE * width,
				-UNIT_SIZE * length, UNIT_HIGHT * scale, -UNIT_SIZE * width,

				-UNIT_SIZE * length, UNIT_HIGHT * scale, -UNIT_SIZE * width,
				-UNIT_SIZE * length, -UNIT_HIGHT * scale,
				UNIT_SIZE * width,
				-UNIT_SIZE * length,
				UNIT_HIGHT * scale,
				UNIT_SIZE * width,

				// ����
				UNIT_SIZE * length, UNIT_HIGHT * scale, -UNIT_SIZE * width,
				UNIT_SIZE * length, UNIT_HIGHT * scale, UNIT_SIZE * width,
				UNIT_SIZE * length, -UNIT_HIGHT * scale, -UNIT_SIZE * width,

				UNIT_SIZE * length, -UNIT_HIGHT * scale, -UNIT_SIZE * width,
				UNIT_SIZE * length, UNIT_HIGHT * scale, UNIT_SIZE * width,
				UNIT_SIZE * length, -UNIT_HIGHT * scale, UNIT_SIZE * width

		};

		ByteBuffer vbb = ByteBuffer.allocateDirect(verteices.length * 4); // ���������������ݻ���
		vbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mVertexBuffer = vbb.asFloatBuffer();// ת��Ϊfloat�ͻ���
		mVertexBuffer.put(verteices);// �򻺳����з��붥����������
		mVertexBuffer.position(0);// ���û�������ʼλ��

		float[] normals =
		{
				// ���涥�㷨����
				0, 1, 0, 0, 1, 0, 0, 1, 0,

				0, 1, 0, 0, 1, 0, 0, 1, 0,
				// ���涥�㷨����
				0, 0, -1, 0, 0, -1, 0, 0, -1,

				0, 0, -1, 0, 0, -1, 0, 0, -1,
				// ǰ�涥�㷨����
				0, 0, 1, 0, 0, 1, 0, 0, 1,

				0, 0, 1, 0, 0, 1, 0, 0, 1,
				// ���涥�㷨����
				0, -1, 0, 0, -1, 0, 0, -1, 0,

				0, -1, 0, 0, -1, 0, 0, -1, 0,
				// ���涥�㷨����
				-1, 0, 0, -1, 0, 0, -1, 0, 0,

				-1, 0, 0, -1, 0, 0, -1, 0, 0,
				// ���涥�㷨����
				1, 0, 0, 1, 0, 0, 1, 0, 0,

				1, 0, 0, 1, 0, 0, 1, 0, 0 };
		ByteBuffer nbb = ByteBuffer.allocateDirect(normals.length * 4);// ������ɫ�������ݻ���
		nbb.order(ByteOrder.nativeOrder());// �����ֽ�˳��
		mNormalBuffer = nbb.asFloatBuffer();// ת��Ϊfloat�ͻ���
		mNormalBuffer.put(normals);// �򻺳����з��붥����������
		mNormalBuffer.position(0);// ���û�������ʼλ��
	}

	public void drawSelf(GL10 gl)
	{
		gl.glRotatef(mOffsetX, 1, 0, 0);
		gl.glRotatef(mOffsetY, 0, 1, 0);

		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);// ����ʹ�ö�������

		// Ϊ����ָ��������������
		gl.glVertexPointer(3, // ÿ���������������Ϊ3 xyz
				GL10.GL_FLOAT, // ��������ֵ������Ϊ GL_FIXED
				0, // ����������������֮��ļ��
				mVertexBuffer // ������������
		);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);// ����ʹ�÷���������
		gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalBuffer);// Ϊ����ָ�����㷨��������

		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);// ����ͼ��
	}

}
