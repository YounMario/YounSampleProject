package com.younchen.younsampleproject.ui.window.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.ui.window.FloatWindow;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/4/12.
 *
 */

public class FloatWindowService extends Service {

    private static final String TAG = "FloatWindowService";

    private static final String SHOW_FLOAT_WINDOW = "show_float_window";
    private static final String DISSMISSS_FLOAT_WINDOW = "dissmiss_float_window";

    private boolean mInit = false;
    private FloatWindow mFloatWindow;

    private ReceiverHandler mReceiverHandler = new ReceiverHandler(this);

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!mInit) {
            init();
        } else {
            String action = intent.getAction();
            switch (action) {
                case SHOW_FLOAT_WINDOW:
                    showWindow();
                    break;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void init() {
        mInit = true;
        registerReceiver(mBroadCastReceiver, getIntentFilter());
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, FloatWindowService.class);
        context.startService(intent);
    }

    public static void startShowFloatWindow(Context context) {
        Intent intent = new Intent(context, FloatWindowService.class);
        intent.setAction(SHOW_FLOAT_WINDOW);
        context.startService(intent);
    }

    public static void stopShowFloatWindow(Context context) {
        Intent intent = new Intent(context, FloatWindowService.class);
        intent.setAction(SHOW_FLOAT_WINDOW);
        context.startService(intent);
    }

    protected IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        return filter;
    }

    private void onScreenOff() {
        YLog.i(TAG, "onScreenOff");
    }


    private void onScreenOn() {
        YLog.i(TAG, "onScreenOn");
        mReceiverHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showWindow();
            }
        }, 3000);
    }

    private void showWindow() {
        if (mFloatWindow == null) {
            mFloatWindow = FloatWindow.newInstance(FloatWindowService.this);
        }
        mFloatWindow.show();
    }


    private static class ReceiverHandler extends Handler {

        private final WeakReference<FloatWindowService> mReference;
        private FloatWindowService mService;

        public static final int WHAT_ACTION_SCREEN_OFF = 1;
        public static final int WHAT_ACTION_SCREEN_ON = 2;

        ReceiverHandler(FloatWindowService service) {
            mReference = new WeakReference<FloatWindowService>(service);
        }

        @Override
        public void handleMessage(Message msg) {
            mService = mReference.get();
            if (mService == null || msg == null) {
                return;
            }
            switch (msg.what) {
                case WHAT_ACTION_SCREEN_OFF:
                    if (mReference.get() != null) {
                        mReference.get().onScreenOff();
                    }
                    break;
                case WHAT_ACTION_SCREEN_ON:
                    if (mReference.get() != null) {
                        mReference.get().onScreenOn();
                    }
                    break;
            }
        }
    }


    private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Intent.ACTION_SCREEN_OFF:
                    mReceiverHandler.sendEmptyMessage(ReceiverHandler.WHAT_ACTION_SCREEN_OFF);
                    break;
                case Intent.ACTION_SCREEN_ON:
                    mReceiverHandler.sendEmptyMessage(ReceiverHandler.WHAT_ACTION_SCREEN_ON);
                    break;
            }
        }
    };

}
