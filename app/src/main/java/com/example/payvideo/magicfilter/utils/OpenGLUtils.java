package com.example.payvideo.magicfilter.utils;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OpenGLUtils {

    /**
     * 初始化着色器
     * @param strVSource
     * @param strFSource
     * @return
     */
    public static int loadProgram(String strVSource, String strFSource){
        int iVShader;
        int iFShader;
        int iProgId;
        int[] link = new int[1];
        iVShader = loadShader(strVSource, GLES20.GL_VERTEX_SHADER);
        //顶点着色器加载失败
        if (iVShader == 0){
            Log.d("Load Program", "Vertex Shader Failed");
            return 0;
        }
        iFShader = loadShader(strFSource, GLES20.GL_FRAGMENT_SHADER);
        //片段着色器失败
        if (iFShader == 0){
            Log.d("Load Program", "Fragment Shader Failed");
            return 0;
        }

        iProgId = GLES20.glCreateProgram();
        GLES20.glAttachShader(iProgId, iVShader);
        GLES20.glAttachShader(iProgId, iFShader);
        GLES20.glLinkProgram(iProgId);
        GLES20.glGetProgramiv(iProgId, GLES20.GL_LINE_STRIP, link, 0);
        //链接失败
        if (link[0] <= 0){
            Log.d("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(iVShader);
        GLES20.glDeleteShader(iFShader);
        return iProgId;
    }

    /**
     * 加载着色器
     * @param strSource
     * @param iType
     * @return
     */
    private static int loadShader(String strSource, int iType){
        int[] compiled = new int[1];
        int iShader = GLES20.glCreateShader(iType);
        GLES20.glShaderSource(iShader, strSource);
        GLES20.glCompileShader(iShader);
        GLES20.glGetShaderiv(iShader, GLES20.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0){
            Log.e("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(iShader));
            return 0;
        }
        return iShader;
    }

    /**
     * 从资源文件中读取着色器
     * @param context
     * @param resourceId 资源ID
     * @return
     */
    public static String readShaderFromRawResource(Context context, int resourceId){
        final InputStream inputStream = context.getResources().openRawResource(resourceId);
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String nextLine;
        final StringBuilder body = new StringBuilder();

        try {
            while ((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append('\n');
            }
        } catch (IOException e){
            return null;
        }
        return body.toString();
    }


}
