package com.younchen.younsampleproject.ui.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 龙泉 on 2016/7/28.
 */
public class ScrollableLayout extends ViewGroup {

    private static final String TAG = "ScrollableLayout";
    private int maxScroll;
    private int minScroll;
    private int totalScroll;

    private int scrollFactor;
    private int distance;

    private float startX;
    private float startY;
    private float currentX;
    private float currentY;
    private float lastX;
    private float lastY;
    private float moveOffsetX;

    private boolean isMoveLeft;

    private VelocityTracker velocityTracker;
    private Scroller scroller;

    private OnScrollListener listener;

    private int MaximumFlingVelocity;


    public ScrollableLayout(Context context) {
        super(context);
        init();
    }

    public ScrollableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setScrollListener(OnScrollListener listener) {
        this.listener = listener;
    }

    public void init() {
        final ViewConfiguration configuration = ViewConfiguration
                .get(getContext());
        scroller = new Scroller(getContext());
        MaximumFlingVelocity = configuration.getScaledMaximumFlingVelocity();
    }

    //左右排列
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int totalWidth = getPaddingLeft();
        int topPadding = getPaddingTop();

        int childWidth;
        int childHeight;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            childWidth = getMeasuredWidth();
            childHeight = getMeasuredHeight();

            MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            totalWidth += params.leftMargin;
            child.layout(l + totalWidth, t + topPadding + params.topMargin, l + totalWidth + childWidth,
                    +topPadding + params.topMargin + childHeight);


            totalWidth += childWidth;
            totalWidth += params.rightMargin;
        }
        updateScrollableDistance();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
        currentX = event.getX();
        currentY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = startX = currentX;
                lastY = startY = currentY;
                scroller.abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                distance = (int) (currentX - lastX);
                isMoveLeft = distance > 0 ? true : false;
                if (canMove(-distance)) {
                    scrollBy(-distance, 0);
                }
                Log.i(TAG, "move left:" + isMoveLeft);
                break;
            case MotionEvent.ACTION_UP:
                velocityTracker.computeCurrentVelocity(1000, MaximumFlingVelocity);
                int during = (int) ((currentX - startX) /velocityTracker.getXVelocity());
                moveRemainPart(during);
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    velocityTracker = null;
                }
                break;
        }
        lastX = currentX;
        lastY = currentY;
        return true;
    }

    private void moveRemainPart(int during) {
        float childWidth = getChildWidth();
        int movedPage = (int) (getScrollX() / childWidth);
        int lave = (int) (getScrollX() - movedPage * childWidth);
        if (lave > childWidth / 2) {
            //move to next
            Log.i(TAG, "move to next");
            smoothScrollTo((int) (movedPage * childWidth + childWidth), 0, during);
        } else {
            //move to back;
            Log.i(TAG, "move to back");
            smoothScrollTo((int) (movedPage * childWidth), 0, during);
        }
    }


    private boolean canMove(int distance) {
        if (Math.abs(getScrollX() + distance) > totalScroll) {
            return false;
        }
        return true;
    }

    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
        float width = getChildWidth();
        if (width <= 0) return;
        if (x < width * getChildCount() - width) {
            moveOffsetX = (width - x) / width;
            if (listener != null) {
                listener.onScroll(moveOffsetX);
            }
        }
    }

    private void smoothScrollTo(int destX, int destY, int during) {
        int scrollX = getScrollX();
        int delta = destX - scrollX;
        scroller.startScroll(scrollX, 0, delta, 0, during);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            postInvalidate();
        }
    }

    private void updateScrollableDistance() {
        int childWidth = getChildWidth();
        totalScroll = childWidth * (getChildCount() - 1) + childWidth * scrollFactor;
        minScroll = childWidth * scrollFactor;
    }

    @Override
    public void addView(View child, int index) {
        if (child == null) {
            throw new RuntimeException("view is null");
        }
        MarginLayoutParams params = new MarginLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(child, index, params);
    }

    private int getChildWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public interface OnScrollListener {
        void onScroll(float offset);
    }
}
