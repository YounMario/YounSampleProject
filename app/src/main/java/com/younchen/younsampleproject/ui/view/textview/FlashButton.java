package com.younchen.younsampleproject.ui.view.textview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.younchen.younsampleproject.R;


/**
 * Created by yinlongquan on 2017/9/26.
 */

public class FlashButton extends TextView {

    private Bitmap mFlashImage;
    private Paint mPaint;
    private int mLastFlashPosition;
    private int mImageWidth;

    public FlashButton(Context context) {
        this(context, null);
    }

    public FlashButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlashButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mFlashImage = BitmapFactory.decodeResource(getResources(), R.drawable.img_flash);
        mImageWidth = mFlashImage.getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mFlashImage, mLastFlashPosition, 0, mPaint);
        mLastFlashPosition = (mLastFlashPosition + 50);
        if (mLastFlashPosition > getMeasuredWidth()) {
            mLastFlashPosition = -mImageWidth;
        }
        postInvalidateDelayed(50);
    }
}
