package com.example;

import java.io.InputStream;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

public class SurfaceView extends GLSurfaceView
{
	private SceneRenderer sceneRenderer;

	public SurfaceView(Context context)
	{
		super(context);

		sceneRenderer = new SceneRenderer();
		setRenderer(sceneRenderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);
	}

	public int initTexture(GL10 gl, int textureId)
	{
		int textures[] = new int[1];
		gl.glGenTextures(1, textures, 0);
		int currTextureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, currTextureId);

		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_REPEAT);

		InputStream is = getResources().openRawResource(textureId);
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmap.recycle();

		return currTextureId;
	}

	private class SceneRenderer implements Renderer
	{
		Graph graph;

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			gl.glDisable(GL10.GL_DITHER);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

			gl.glClearColor(0, 0, 0, 0);
			gl.glEnable(GL10.GL_DEPTH_TEST);

			graph = new Graph(initTexture(gl, R.drawable.duke));

			// 设置雾特效
			// 开启雾特效
			gl.glEnable(GL10.GL_FOG);
			float fogColor[] =
			{ 1, 0.2f, 0.4f, 0 };
			gl.glFogfv(GL10.GL_FOG_COLOR, fogColor, 0);// 颜色
			gl.glFogx(GL10.GL_FOG_MODE, GL10.GL_EXP2);// 雾模式
			gl.glFogf(GL10.GL_FOG_DENSITY, 0.3f);// 浓度
			gl.glFogf(GL10.GL_FOG_START, 2);// 开始距离
			gl.glFogf(GL10.GL_FOG_END, 10);// 结束距离
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			float ratio = (float) width / height;
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 20);
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);

			gl.glLoadIdentity();
			graph.draw(gl);
		}
	}
}