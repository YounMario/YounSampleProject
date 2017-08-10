package com.younchen.younsampleproject.commons.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.younchen.younsampleproject.commons.utils.DimenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yinlongquan on 2017/8/2.
 */

public class LineTextView extends View {

    private List<String> mString;
    private Paint mTextPaint;
    private Paint mBackPaint;
    private Rect mTextBounds;
    private int mTextSpanY;

    public LineTextView(Context context) {
        super(context);
    }

    public LineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mString = new ArrayList<>();
        mTextPaint = new Paint(Color.GRAY);
        mTextPaint.setTextSize(DimenUtils.dp2px(16));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.BLUE);
        mTextBounds = new Rect();
        mTextSpanY = DimenUtils.dp2px(5);

        mBackPaint = new Paint(Color.YELLOW);
    }

    public LineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mString = new ArrayList<>();
    }

    public void setStringList(List<String> list) {
        this.mString = list;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int lineCount = mString.size();
        int height = (int) (lineCount * mTextPaint.getTextSize() + (lineCount - 1) * mTextSpanY);
        int measureHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, measureHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        for (String text : mString) {
            int textEnd = text.length() - 1;
            mTextPaint.getTextBounds(text, 0, textEnd, mTextBounds);
            canvas.drawText(text, mTextBounds.left, mTextBounds.height(), mTextPaint);
            canvas.translate(0, -(mTextBounds.height() + mTextSpanY));
        }
        canvas.restore();
    }
}
