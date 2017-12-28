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
		ColorRect c1;
		ColorRect c2;

		TextureRect t1;
		TextureRect t2;

		int one = 65535;

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			gl.glDisable(GL10.GL_DITHER);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

			gl.glClearColor(0, 0, 0, 0);
			gl.glEnable(GL10.GL_DEPTH_TEST);

			gl.glEnable(GL10.GL_BLEND);
			gl.glBlendFunc(GL10.GL_SRC_COLOR, GL10.GL_DST_ALPHA);

			c1 = new ColorRect(one, 0, 0, one / 3 * 4);
			c2 = new ColorRect(0, one, 0, one / 2);

			t1 = new TextureRect(initTexture(gl, R.drawable.duke));
			t2 = new TextureRect(initTexture(gl, R.drawable.ic_launcher));
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			gl.glViewport(0, 0, width, height);
			gl.glMatrixMode(GL10.GL_PROJECTION);
			gl.glLoadIdentity();
			float ratio = (float) width / height;
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);

			gl.glLoadIdentity();

			gl.glPushMatrix();
			gl.glTranslatef(0, 0, -2);
			t1.drawSelf(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslatef(-0.7f, -0.3f, -1.9f);
			t2.drawSelf(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslatef(0.7f, 0.4f, -1.8f);
			c1.drawSelf(gl);
			gl.glPopMatrix();

			gl.glPushMatrix();
			gl.glTranslatef(-0.6f, 0.6f, -1.8f);
			c2.drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}