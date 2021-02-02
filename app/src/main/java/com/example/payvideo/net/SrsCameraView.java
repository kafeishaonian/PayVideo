package com.example.payvideo.net;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.payvideo.magicfilter.bean.gpuimage.GPUImageFilter;
import com.example.payvideo.magicfilter.utils.MagicFilterType;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * 相机视图类
 */
public class SrsCameraView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private GPUImageFilter magicFilter;



    public SrsCameraView(Context context) {
        this(context, null);
    }

    public SrsCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //创建一个OpenGL es 2.0 context
        setEGLContextClientVersion(2);
        //初始化控制GLSurfaceView绘制的工作
        setRenderer(this);
        //只有在绘制数据改变时才绘制view
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    /**
     * 设置View的OpenGL ES环境
     * 只调用一次
     * @param gl
     * @param config
     */
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //关闭服务端GL功能
        GLES20.glDisable(GL10.GL_DITHER);
        GLES20.glClearColor(0, 0, 0, 0);

        magicFilter = new GPUImageFilter(MagicFilterType.NONE);
        magicFilter.init(getContext().getApplicationContext());
//        magicFilter.

    }

    /**
     * 当View的几何图形发生变化的时候调用，如竖屏变横屏
     * @param gl
     * @param width
     * @param height
     */
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    /**
     * 每次View重新绘制的时候调用
     * @param gl
     */
    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
