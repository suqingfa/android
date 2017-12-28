package com.example;

import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

public class MySurfaceView extends GLSurfaceView
{

	private SceneRenderer mRenderer;// ������Ⱦ��
	float cx = 0;// �����xλ��
	float cy = 3;// �����yλ��
	float cz = 40;// �����zλ��

	float tx = 0;// //Ŀ���xλ��
	float ty = 0;// Ŀ���yλ��
	float tz = 0;// Ŀ���zλ��

	public MySurfaceView(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
		mRenderer = new SceneRenderer(); // ����������Ⱦ��
		setRenderer(mRenderer); // ������Ⱦ��
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);// ������ȾģʽΪ������Ⱦ
	}

	private class SceneRenderer implements GLSurfaceView.Renderer
	{
		CubeVertex cubeVertex;// �������㷨������

		@Override
		public void onDrawFrame(GL10 gl)
		{
			// TODO Auto-generated method stub
			// ����ƽ����ɫ
			gl.glShadeModel(GL10.GL_SMOOTH);
			// ����Ϊ�򿪱������
			gl.glEnable(GL10.GL_CULL_FACE);
			// �����ɫ��������Ȼ���
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			// ���õ�ǰ����Ϊģʽ����
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			// ���õ�ǰ����Ϊ��λ����
			gl.glLoadIdentity();

			// ����cameraλ��
			GLU.gluLookAt(gl, cx, // ����λ�õ�X
					cy, // ����λ�õ�Y
					cz, // ����λ�õ�Z
					tx, // �����򿴵ĵ�X
					ty, // �����򿴵ĵ�Y
					tz, // �����򿴵ĵ�Z
					0, 1, 0);

			gl.glPushMatrix();// ��ȡ����ϵ
			gl.glRotatef(45, 0, 1, 0);// ��Y����ת45��
			gl.glRotatef(45, 1, 0, 0);// ��X����ת45��
			cubeVertex.drawSelf(gl);// ����������
			gl.glPopMatrix();// �ָ�����ϵ
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			// TODO Auto-generated method stub
			// �����Ӵ���С��λ��
			gl.glViewport(0, 0, width, height);
			// ���õ�ǰ����ΪͶӰ����
			gl.glMatrixMode(GL10.GL_PROJECTION);
			// ���õ�ǰ����Ϊ��λ����
			gl.glLoadIdentity();
			// ����͸��ͶӰ�ı���
			float ratio = (float) width / height;
			// ���ô˷����������͸��ͶӰ����
			gl.glFrustumf(-ratio, ratio, -1.0f, 1.0f, 8, 100);
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			// TODO Auto-generated method stub
			// �رտ�����
			gl.glDisable(GL10.GL_DITHER);
			// �����ض�Hint��Ŀ��ģʽ������Ϊ����Ϊʹ�ÿ���ģʽ
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			// ������Ļ����ɫ��ɫRGBA
			gl.glClearColor(0, 0, 0, 0);
			// ������ɫģ��Ϊƽ����ɫ
			gl.glShadeModel(GL10.GL_SMOOTH);
			// ������Ȳ���
			gl.glEnable(GL10.GL_DEPTH_TEST);
			// ����Ϊ�򿪱������
			gl.glEnable(GL10.GL_CULL_FACE);
			// ����ʹ�ù���
			gl.glEnable(GL10.GL_LIGHTING);
			// ��ʼ����Դ
			initLight(gl);
			// ��ʼ�����ʹ�Դ
			initMaterial(gl);

			cubeVertex = new CubeVertex(2.5f, 2.5f, 2.5f);

			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					cubeVertex.mOffsetY += 2.0f;// ÿ����ת
				}
			}, 0, 100);
		}

	}

	public void initLight(GL10 gl)
	{
		// TODO Auto-generated method stub
		gl.glEnable(GL10.GL_LIGHT0);// ��0�Ź�Դ

		// ����������
		float[] ambientParams =
		{ 0.46f, 0.21f, 0.05f, 1.0f };// ����� RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

		// ɢ�������
		float[] diffuseParams =
		{ 0.46f, 0.21f, 0.05f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

		// ���������
		float[] specularParams =
		{ 0.46f, 0.21f, 0.05f, 1.0f };
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

		// ָ����Դλ��
		float[] directionParams =
		{ -1f, 1f, 1f, 0 };// �����
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, directionParams, 0);
	}

	public void initMaterial(GL10 gl)
	{
		// TODO Auto-generated method stub
		// ������Ϊ��ɫ����
		float ambientMaterial[] =
		{ 0.6f, 0.6f, 0.6f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
				ambientMaterial, 0);
		// ɢ���Ϊ��ɫ����
		float diffuseMaterial[] =
		{ 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
				diffuseMaterial, 0);
		// �߹����Ϊ��ɫ
		float specularMaterial[] =
		{ 1f, 1.0f, 1f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				specularMaterial, 0);
	}
}
