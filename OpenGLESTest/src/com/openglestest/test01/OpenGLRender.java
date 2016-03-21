package com.openglestest.test01;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;

public class OpenGLRender implements Renderer {

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		GLES20.glClearColor(0f, 0.2f, 0.2f, 1.0f);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// 创建顶点着色器
		int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		String vertexShaderSource = 
				"attribute vec4 vPosition;    \n"
				+ "void main()                  \n"
				+ "{                            \n"
				+ "   gl_Position = vPosition;  \n"
				+ "}                            \n";
		
		GLES20.glShaderSource(vertexShader, vertexShaderSource);
		GLES20.glCompileShader(vertexShader);

		// 创建片元着色器
		int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		String fragmentShaderSource = 
				"precision mediump float;    \n"
				+ "void main()                  \n"
				+ "{                            \n"
				+ "   gl_FragColor = vec4 ( 1.0, 0.0, 0.0, 1.0 );    \n"
				+ "}                            \n";
		GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
		GLES20.glCompileShader(fragmentShader);
		
		// 创建程序容器并连接
		int programObject = GLES20.glCreateProgram();
		GLES20.glAttachShader(programObject, vertexShader);
		GLES20.glAttachShader(programObject, fragmentShader);
		GLES20.glBindAttribLocation(programObject, 0, "vPosition");
		GLES20.glLinkProgram(programObject);
		
		// 释放着色器
		GLES20.glDeleteShader(vertexShader);
		GLES20.glDeleteShader(fragmentShader);
		
		// 运行程序
		GLES20.glViewport(0, 0, width, height);
		GLES20.glUseProgram(programObject);
		
		// 释放程序
		GLES20.glDeleteProgram(programObject);
		
		// 设置顶点坐标
		Buffer ptr = bufferUtil(new float[]{
			0.0f,  0.5f, 0.0f,
			-0.5f, -0.5f, 0.0f,
			0.5f, -0.5f, 0.0f
		});

		// 将顶点坐标传递到顶点着色器源码的vPosition变量中
		int mPositionHandle = GLES20.glGetAttribLocation(programObject, "vPosition");
		GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 0, ptr);
		GLES20.glEnableVertexAttribArray(mPositionHandle);
		
		// 画图
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
		
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
	}
	
	// 创建Buffer
	public Buffer bufferUtil(float[] arr) {
		FloatBuffer mBuffer;

		// 先初始化buffer,数组的长度*4,因为一个float占4个字节
		ByteBuffer qbb = ByteBuffer.allocateDirect(arr.length * 4);
		// 数组排列用nativeOrder
		qbb.order(ByteOrder.nativeOrder());

		mBuffer = qbb.asFloatBuffer();
		mBuffer.put(arr);
		mBuffer.position(0);

		return mBuffer;
	}

}
