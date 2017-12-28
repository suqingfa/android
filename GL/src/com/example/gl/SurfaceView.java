package com.example.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SurfaceView extends GLSurfaceView
{
	private SceneRenderer sceneRenderer; // ������Ⱦ��

	public SurfaceView(Context context)
	{
		super(context);

		sceneRenderer = new SceneRenderer();
		setRenderer(sceneRenderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);// ������ȾģʽΪ������Ⱦ
	}

	private class SceneRenderer implements Renderer
	{
		Graph graph = new Graph(); // ����������

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			gl.glDisable(GL10.GL_DITHER);// �رտ�����
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

			gl.glClearColor(0, 0, 0, 0); // ������ɫ
			gl.glEnable(GL10.GL_DEPTH_TEST);// ������Ȳ���
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			gl.glViewport(0, 0, width, height); // �����ӿ�λ�úʹ�С
			gl.glMatrixMode(GL10.GL_PROJECTION); // ����ͶӰ��ʽ
			gl.glLoadIdentity();
			float ratio = (float) width / height;
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10); // ����ͶӰ����
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			gl.glEnable(GL10.GL_CULL_FACE);// �򿪱���ü�
			gl.glShadeModel(GL10.GL_SMOOTH);// ��ɫģ��Ϊƽ����ɫ
			gl.glFrontFace(GL10.GL_CCW);// ��ʱ��Ϊ����
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// ��������
			gl.glMatrixMode(GL10.GL_MODELVIEW);// ���õ�ǰ����Ϊģʽ����
			gl.glLoadIdentity();// ������������һ��ƽ����ת���ź������仯

			graph.draw(gl);
		}
	}
}