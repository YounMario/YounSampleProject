package com.younchen.younsampleproject.commons.widget;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;


/**
 * Created by Administrator on 2017/4/28.
 */

public abstract class AbstractWindow {

    protected Context mContext;
    protected View mRootView;
    private WindowManager mWindowManager;

    private boolean mIsShow;
    private BackPressableFrameLayout mFrameLayout;

    public AbstractWindow(Context context) {
        mContext = context;
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mFrameLayout = new BackPressableFrameLayout(mContext) {
            @Override
            public boolean onBackKeyPressed() {
                return onBackPressed();
            }
        };
    }

    protected void setContentView(int layoutId) {
        this.mRootView = LayoutInflater.from(mContext).inflate(layoutId, null);
        addViewToContainer(mRootView);
        initView();
    }

    public void setContentView(View view) {
        this.mRootView = view;
        addViewToContainer(mRootView);
        initView();
    }

    private void addViewToContainer(View view){
        mFrameLayout.addView(mRootView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    protected abstract void initView();
    public abstract void show();
    public abstract void hide();
    public abstract boolean onBackPressed();

    public View findViewById(int id) {
        if (mRootView != null) {
            return mRootView.findViewById(id);
        }
        return null;
    }

    protected void showWindow() {
        WindowManager.LayoutParams params = getDefaultLayoutParams();
        mWindowManager.addView(mFrameLayout, params);
        mIsShow = true;
    }

    protected void hideWindow() {
        mWindowManager.removeView(mFrameLayout);
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
