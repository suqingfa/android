package com.example;

import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

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

	// 初始化光源
	private void initLight(GL10 gl)
	{
		gl.glEnable(GL10.GL_LIGHT0);// 打开0号灯

		// 环境光设置
		float[] ambientParams =
		{ 0.05f, 0.05f, 0.025f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_AMBIENT, ambientParams, 0);

		// 散射光设置
		float[] diffuseParams =
		{ 1f, 1f, 0.5f, 1.0f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_DIFFUSE, diffuseParams, 0);

		// 反射光设置
		float[] specularParams =
		{ 0.5f, 0.5f, 1f, 1f };// 光参数 RGBA
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_SPECULAR, specularParams, 0);

		// 设定光源的位置
		float[] positionParamsGreen =
		{ -14.14f, 8.28f, 6f, 0 };// 最后的0表示使用定向光
		gl.glLightfv(GL10.GL_LIGHT0, GL10.GL_POSITION, positionParamsGreen, 0);
	}

	// 初始化材质
	private void initMaterial(GL10 gl)
	{
		// 材质为白色时什么颜色的光照在上面就将体现出什么颜色
		// 环境光为白色材质
		float ambientMaterial[] =
		{ 0.7f, 0.7f, 0.7f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
				ambientMaterial, 0);
		// 散射光为白色材质
		float diffuseMaterial[] =
		{ 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
				diffuseMaterial, 0);
		// 高光材质为白色
		float specularMaterial[] =
		{ 1f, 1f, 1f, 1.0f };
		gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
				specularMaterial, 0);
	}

	// 初始化纹理
	public int initTexture(GL10 gl, int drawableId)
	{
		// 生成纹理ID
		int[] textures = new int[1];
		gl.glGenTextures(1, textures, 0);
		int textureId = textures[0];
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
				GL10.GL_CLAMP_TO_EDGE);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T,
				GL10.GL_CLAMP_TO_EDGE);

		InputStream is = this.getResources().openRawResource(drawableId);
		Bitmap bitmapTmp;
		bitmapTmp = BitmapFactory.decodeStream(is);

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmapTmp, 0);
		bitmapTmp.recycle();

		return textureId;
	}

	private class SceneRenderer implements Renderer
	{
		Ball earth;
		Ball moon;

		Celestial celestialSmall;// 小星星星空
		Celestial celestialBig;// 大星星星空

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			gl.glDisable(GL10.GL_DITHER);
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
			gl.glShadeModel(GL10.GL_SMOOTH);
			gl.glEnable(GL10.GL_CULL_FACE);
			gl.glClearColor(0, 0, 0, 0);
			gl.glEnable(GL10.GL_DEPTH_TEST);

			gl.glEnable(GL10.GL_LIGHTING);
			initLight(gl);
			initMaterial(gl);
			earth = new Ball(6, initTexture(gl, R.drawable.earth));
			moon = new Ball(2, initTexture(gl, R.drawable.moon));

			celestialBig = new Celestial(0, 0, 1, 0, 750);
			celestialSmall = new Celestial(0, 0, 2, 0, 200);

			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					earth.mAngleY += 2 * 180f / 320;
					moon.mAngleY += 2 * 180f / 320;
				}
			}, 0, 50);

			new Timer().schedule(new TimerTask()
			{
				@Override
				public void run()
				{
					celestialSmall.yAngle += 0.5;
					if (celestialSmall.yAngle >= 360)
						celestialSmall.yAngle = 0;

					celestialBig.yAngle += 0.5;
					if (celestialBig.yAngle >= 360)
						celestialBig.yAngle = 0;
				}
			}, 0, 100);

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
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
			gl.glMatrixMode(GL10.GL_MODELVIEW);
			gl.glLoadIdentity();

			gl.glTranslatef(0, 0, -3.6f);
			gl.glEnable(GL10.GL_LIGHTING);
			gl.glPushMatrix();
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 3.5f);
			earth.drawSelf(gl);

			gl.glTranslatef(0, 0, 1.5f);
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 1f);
			moon.drawSelf(gl);

			gl.glPopMatrix();
			gl.glDisable(GL10.GL_LIGHTING);

			gl.glPushMatrix();
			gl.glTranslatef(0, -8f, 0);
			celestialSmall.drawSelf(gl);
			celestialBig.drawSelf(gl);
			gl.glPopMatrix();
		}
	}
}