package com.younchen.younsampleproject.ui.window;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.younchen.younsampleproject.R;
import com.younchen.younsampleproject.commons.utils.DimenUtils;

/**
 * Created by Administrator on 2017/4/12.
 */

public class FloatWindow {

    private Context mContext;
    private View mTopView;
    private LayoutInflater mLayoutInflater;
    private WindowManager mWindowManager;
    private KeyBoardController mKeyBoardController;
    private EditText mEditText;

    public FloatWindow(Context context) {
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mTopView = mLayoutInflater.inflate(R.layout.activity_float_window, null);
        mEditText = (EditText) mTopView.findViewById(R.id.edit_text);
        mTopView.findViewById(R.id.btn_float_window_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hide();
            }
        });
        mKeyBoardController = new KeyBoardController(mTopView, mEditText);
        mKeyBoardController.setKeyBoardShowListener(new KeyBoardController.KeyBoardShowListener() {
            @Override
            public void onKeyBoardShow(int keyboardTop, int keyboardHeight) {

            }

            @Override
            public void onKeyBoardHide() {

            }
        });
    }


    public static FloatWindow newInstance(Context context) {
        return new FloatWindow(context);
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        WindowManager.LayoutParams params = getFullParams();
        params.type = WindowManager.LayoutParams.TYPE_TOAST;
        mWindowManager.addView(mTopView, params);
    }


    public WindowManager.LayoutParams getFullParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        if (Build.VERSION.SDK_INT >= 19) { // 19以上透明通知栏和虚拟键
            params.width = DimenUtils.getRealWidth();
            params.height = DimenUtils.getRealHeight();
            params.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
                    | WindowManager.LayoutParams.FLAG_FULLSCREEN
           //         | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            ;
        } else {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            ;
        }

        //input mode
//        params.softInputMode |= WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        //system ui visibility
//        boolean isShowStatusBar = ServiceConfigManager.getInstanse(mContext).isShowStatusBar();
//        if (isShowStatusBar) {
//            if (KMarshmallowFingerprint.isEnable(mContext)) {
//                params.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN |
//                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
//                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION|
//                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
//                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
//            } else {
//                params.systemUiVisibility = 0x0;
//            }
//        } else {
//            params.systemUiVisibility = SystemFlag.getSystemUiVisibility();
//        }
        params.format = PixelFormat.TRANSLUCENT;
        params.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        return params;
    }

    public void hide() {
        if (!isShowing()) {
            return;
        }
        mWindowManager.removeView(mTopView);
    }

    public boolean isShowing() {
        return mTopView.getParent() != null;
    }


}
