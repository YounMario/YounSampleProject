package com.younchen.younsampleproject.commons.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;



/**
 * Created by Administrator on 2017/4/28.
 */

public abstract class AbstractWindow {

    protected Context mContext;
    protected View mRootView;
    private WindowManager mWindowManager;

    private boolean mIsShow;

    public AbstractWindow(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    public void setContentView(int layoutId) {
        this.mRootView = LayoutInflater.from(mContext).inflate(layoutId, null);
        initView();
    }

    public void setContentView(View view) {
        this.mRootView = view;
        initView();
    }

    protected abstract void initView();
    public abstract void show();
    public abstract void hide();

    public View findViewById(int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        }
        return null;
    }

    public void showWindow() {
        WindowManager.LayoutParams params = getDefaultLayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        mWindowManager.addView(mRootView, params);
        mIsShow = true;
    }

    public void hideWindow() {
        mWindowManager.removeView(mRootView);
        mIsShow = false;
    }

    public boolean isShowing() {
        return mIsShow;
    }

    public WindowManager.LayoutParams getDefaultLayoutParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD;
        params.format = PixelFormat.TRANSLUCENT;
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        return params;
    }
}
