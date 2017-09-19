package com.younchen.younsampleproject.sys.topquery;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.younchen.younsampleproject.commons.log.YLog;
import com.younchen.younsampleproject.sys.topquery.model.TopAppQuery;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by yinlongquan on 2017/9/14.
 */

public class TopAppQueryService extends Service {

    private Thread mThread;
    private boolean mIsInited;
    private Handler mHandler;
    private AtomicBoolean mThreadRunning;

    private final String TAG = "TopAppQueryService";

    private static final String KEY_COMMAND = "COMMAND";

    private static final int INIT_MONITOR = 0;
    private static final int START_MONITOR = 1;
    private static final int STOP_MONITOR = 2;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int command = intent.getIntExtra(KEY_COMMAND, INIT_MONITOR);
        switch (command) {
            case STOP_MONITOR:
                stopMonitor();
                break;
            case START_MONITOR:
                startMonitor();
                break;
            case INIT_MONITOR:
                if (!mIsInited) {
                    YLog.i(TAG, "init");
                    mIsInited = true;
                    init();
                }
                break;
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startMonitor() {
        YLog.i(TAG, "start monitor");
        if (!mThreadRunning.get()) {
            mThreadRunning.set(true);
            mThread.start();
        }
    }

    private void stopMonitor() {
        YLog.i(TAG, "stop monitor");
        if (mThreadRunning.get()) {
            mThreadRunning.set(false);
            mThread.interrupt();
        }
    }

    private void init() {
        mThreadRunning = new AtomicBoolean(true);
        mHandler = new Handler(getMainLooper());
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (mThreadRunning.get()) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ComponentName componentName = TopAppQuery.getTopAppPkgName(TopAppQueryService.this);
                    final String packageName = componentName.getPackageName();
                    final String className = componentName.getShortClassName();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            YLog.i(TAG, "querying package Name:" + packageName + " class Name:" + className);
                        }
                    });
                }
            }
        });
        YLog.i(TAG, "run monitor thread");
        mThread.start();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThreadRunning.set(false);
        if (mThread != null && !mThread.isInterrupted()) {
            mThread.interrupt();
            mThread = null;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, TopAppQueryService.class);
        context.startService(intent);
    }

    public static void stopMonitor(Context context) {
        Intent intent = new Intent(context, TopAppQueryService.class);
        intent.putExtra(KEY_COMMAND, STOP_MONITOR);
        context.startService(intent);
    }

    public static void startMonitor(Context context) {
        Intent intent = new Intent(context, TopAppQueryService.class);
        intent.putExtra(KEY_COMMAND, START_MONITOR);
        context.startService(intent);
    }
}
