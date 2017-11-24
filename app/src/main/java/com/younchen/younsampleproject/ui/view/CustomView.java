package com.younchen.younsampleproject.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.younchen.younsampleproject.R;

/**
 * Created by yinlongquan on 2017/11/9.
 */

public class CustomView extends View {

    private Rect rect;
    private Paint mPaint;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        rect = new Rect(200, 200, 200, 200);
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(100, 100);
        canvas.drawCircle(0, 0, 100, mPaint);
        canvas.restore();
    }
}
