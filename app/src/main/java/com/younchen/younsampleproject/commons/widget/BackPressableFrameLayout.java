package com.younchen.younsampleproject.commons.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/5/27.
 */

public abstract class BackPressableFrameLayout extends FrameLayout {

    public BackPressableFrameLayout(Context context) {
        super(context);
    }

    public BackPressableFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BackPressableFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return onBackKeyPressed();
        }
        return super.dispatchKeyEvent(event);
    }

    public abstract boolean onBackKeyPressed();

}
