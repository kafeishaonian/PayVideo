package com.example.payvideo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;

public abstract class BaseActivity extends Activity {

    private InnerMainHandler mHandler = new InnerMainHandler(this, Looper.getMainLooper());
    private InnerThreadHandler mThreadHandler;

    private HandlerThread mThread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mThread = new HandlerThread("handler_thread");
        mThread.start();

        mThreadHandler = new InnerThreadHandler(this, mThread.getLooper());
    }


    private static class InnerMainHandler extends Handler{

        private WeakReference<BaseActivity> fragmentActivitys;

        public InnerMainHandler(BaseActivity activity, Looper looper){
            super(looper);
            fragmentActivitys = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            final BaseActivity activity = fragmentActivitys.get();
            if (activity == null){
                return;
            }
            activity.handleMainMessage(msg);
        }
    }

    protected void handleMainMessage(Message message){

    }

    private static class InnerThreadHandler extends Handler{

        private WeakReference<BaseActivity> fragmentActivitys;

        public InnerThreadHandler(BaseActivity activity, Looper looper){
            super(looper);
            fragmentActivitys = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            final BaseActivity activity = fragmentActivitys.get();
            if (activity == null){
                return;
            }
            activity.handleThreadMessage(msg);
        }
    }

    protected void handleThreadMessage(Message message){

    }




}
