package com.example.payvideo.net;

import android.media.AudioRecord;
import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AutomaticGainControl;

/**
 * Srs推流
 */
public class SrsPublisher {

    //音频采集
    private static AudioRecord mic;
    //回音消除类
    private static AcousticEchoCanceler aec;
    //自定增强控制器
    private static AutomaticGainControl agc;
    //缓存大小
    private byte[] mPcmBuffer = new byte[4096];
    private Thread aWorker;




}
