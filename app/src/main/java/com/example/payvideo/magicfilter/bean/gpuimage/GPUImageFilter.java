package com.example.payvideo.magicfilter.bean.gpuimage;

import android.content.Context;
import android.opengl.GLES20;

import com.example.payvideo.R;
import com.example.payvideo.magicfilter.utils.MagicFilterType;
import com.example.payvideo.magicfilter.utils.OpenGLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.LinkedList;

import javax.microedition.khronos.opengles.GL;

/**
 * GPU 图像过滤器
 */
public class GPUImageFilter {


    private Context mContext;
    private MagicFilterType mType = MagicFilterType.NONE;
    private final LinkedList<Runnable> mRunOnDraw;
    private int mVertexShaderId;
    private int mFragmentShaderId;
    private boolean mIsInitialized;

    protected FloatBuffer mGLCubeBuffer;
    protected FloatBuffer mGLTextureBuffer;

    private int[] mGLCubeId;
    private int[] mGLTextureCoordinateId;

    private int mGLProgId;
    private int mGLPositionIndex;
    private int mGLInputImageTextureIndex;
    private int mGLTextureCoordinateIndex;
    private int mGLTextureTransformIndex;

    protected int mInputWidth;
    protected int mInputHeight;

    private int[] mGLFboId;
    private int[] mGLFboTexId;
    private IntBuffer mGLFboBuffer;

    public GPUImageFilter(){
        this(MagicFilterType.NONE);
    }

    public GPUImageFilter(MagicFilterType type){
        this(type, R.raw.vertex, R.raw.fragment);
    }

    public GPUImageFilter(MagicFilterType type, int fragmentShaderId){
        this(type, R.raw.vertex, fragmentShaderId);
    }

    /**
     * @param vertexShaderId 顶点着色器ID
     * @param fragmentShaderId 片段着色器ID
     */
    public GPUImageFilter(MagicFilterType type, int vertexShaderId, int fragmentShaderId){
        mType = type;
        mRunOnDraw = new LinkedList<>();
        mVertexShaderId = vertexShaderId;
        mFragmentShaderId = fragmentShaderId;
    }

    public void init(Context context){
        mContext = context;
        onInit();
        onInitialized();
    }


    protected void onInit(){
        initVbo();
        loadSamplerShader();
    }

    protected void onInitialized(){
        mIsInitialized = true;
    }



    private void initVbo(){
        final float VEX_CUBE[] = {
                -1.0f, -1.0f,  //bottom left
                1.0f, -1.0f,   //bottom right
                -1.0f, 1.0f,   //top left
                1.0f, 1.0f,    //top right
        };

        final float TEX_COORD[] = {
                0.0f, 0.0f, // Bottom left.
                1.0f, 0.0f, // Bottom right.
                0.0f, 1.0f, // Top left.
                1.0f, 1.0f // Top right.
        };

        //多维数据集的缓冲
        mGLCubeBuffer = ByteBuffer.allocateDirect(VEX_CUBE.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mGLCubeBuffer.put(VEX_CUBE).position(0);

        //纹理缓冲
        mGLTextureBuffer = ByteBuffer.allocateDirect(TEX_COORD.length * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer();
        mGLTextureBuffer.put(TEX_COORD).position(0);

        mGLCubeId = new int[1];
        mGLTextureCoordinateId = new int[1];

        //生成缓冲区对象的名称
        GLES20.glGenBuffers(1, mGLCubeId, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mGLCubeId[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mGLCubeBuffer.capacity() * 4, mGLCubeBuffer, GLES20.GL_STATIC_DRAW);

        GLES20.glGenBuffers(1, mGLTextureCoordinateId, 0);
        GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, mGLTextureCoordinateId[0]);
        GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, mGLTextureBuffer.capacity() * 4, mGLTextureBuffer, GLES20.GL_STATIC_DRAW);
    }

    /**
     * 取样器加载着色器
     */
    private void loadSamplerShader(){
        mGLProgId = OpenGLUtils.loadProgram(OpenGLUtils.readShaderFromRawResource(getContext(), mVertexShaderId),
                OpenGLUtils.readShaderFromRawResource(getContext(), mFragmentShaderId));
        mGLPositionIndex = GLES20.glGetAttribLocation(mGLProgId, "position");
        mGLTextureCoordinateIndex = GLES20.glGetAttribLocation(mGLProgId, "inputTextureCoordinate");
        mGLTextureTransformIndex = GLES20.glGetUniformLocation(mGLProgId, "textureTransform");
        mGLInputImageTextureIndex = GLES20.glGetUniformLocation(mGLProgId, "inputImageTexture");
    }


    protected Context getContext(){
        return mContext;
    }

    public void onInputSizeChanged(final int width, final int height){
        mInputWidth = width;
        mInputHeight = height;
        initFboTexture(width, height);
    }


    private void initFboTexture(int width, int height){
        if (mGLFboId != null && (mInputWidth != width || mInputHeight != height)){

        }

        mGLFboId = new int[1];
        mGLFboTexId = new int[1];
        mGLFboBuffer = IntBuffer.allocate(width * height);

        GLES20.glGenFramebuffers(1, mGLFboId, 0);
        GLES20.glGenTextures(1, mGLFboTexId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mGLFboTexId[0]);
    }
}
