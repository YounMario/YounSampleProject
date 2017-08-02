package com.younchen.younsampleproject.ui.view.style;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.drawable.DrawableWrapper;

import com.younchen.younsampleproject.App;
import com.younchen.younsampleproject.commons.utils.DimenUtils;


/**
 * Created by yinlongquan on 2017/7/28.
 */

public class ClipDrawable extends DrawableWrapper {

    private int mStatusBarHeight = 0;


    public ClipDrawable(Drawable drawable) {
        super(drawable);
        mStatusBarHeight = DimenUtils.getStatusBarHeight(App.getInstance());
    }

    @Override
    public void draw(Canvas canvas) {
        int canvasHeight = canvas.getHeight();
        canvas.save();
        canvas.translate(0, -mStatusBarHeight);
        canvas.scale(1, canvasHeight * 1.0f / (canvasHeight - mStatusBarHeight));
        super.draw(canvas);
        canvas.restore();
    }
}
