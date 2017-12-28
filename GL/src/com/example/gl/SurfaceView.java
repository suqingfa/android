package com.example.gl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class SurfaceView extends GLSurfaceView
{
	private SceneRenderer sceneRenderer; // 场景渲染器

	public SurfaceView(Context context)
	{
		super(context);

		sceneRenderer = new SceneRenderer();
		setRenderer(sceneRenderer);
		setRenderMode(RENDERMODE_CONTINUOUSLY);// 设置渲染模式为主动渲染
	}

	private class SceneRenderer implements Renderer
	{
		Graph graph = new Graph(); // 创建三角形

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config)
		{
			gl.glDisable(GL10.GL_DITHER);// 关闭抗抖动
			gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

			gl.glClearColor(0, 0, 0, 0); // 背景颜色
			gl.glEnable(GL10.GL_DEPTH_TEST);// 启用深度测试
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height)
		{
			gl.glViewport(0, 0, width, height); // 设置视口位置和大小
			gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影方式
			gl.glLoadIdentity();
			float ratio = (float) width / height;
			gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10); // 设置投影参数
		}

		@Override
		public void onDrawFrame(GL10 gl)
		{
			gl.glEnable(GL10.GL_CULL_FACE);// 打开背面裁剪
			gl.glShadeModel(GL10.GL_SMOOTH);// 着色模型为平滑着色
			gl.glFrontFace(GL10.GL_CCW);// 逆时针为正面
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);// 消除缓存
			gl.glMatrixMode(GL10.GL_MODELVIEW);// 设置当前矩阵为模式矩阵
			gl.glLoadIdentity();// 清除虚拟世界的一切平移旋转缩放和其它变化

			graph.draw(gl);
		}
	}
}