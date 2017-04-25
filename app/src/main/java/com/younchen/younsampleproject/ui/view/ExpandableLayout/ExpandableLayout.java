package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.younchen.younsampleproject.R;

/**
 * Created by Administrator on 2017/4/25.
 */

public class ExpandableLayout extends LinearLayout {


    private View mExpandableView;
    private boolean mIsExpandable;

    private int mCurrentState;
    private ExpandListener mExpandListener;

    private static final int EXPAND = 1;
    private static final int HIDE = 2;

    private int mExpandViewHeight;
    private boolean mIsExpendBegin;

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout, defStyleAttr, 0);
        mExpandViewHeight = a.getDimensionPixelSize(R.styleable.ExpandableLayout_expend_height, 0);
        mIsExpendBegin = a.getBoolean(R.styleable.ExpandableLayout_is_expend, false);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        checkExpandable();
        setEvent();
    }


    private void setEvent() {

    }

    private void checkExpandable() {
        if (getChildCount() > 2) {
            throw new RuntimeException("children more than 2 !");
        }
        if (getChildCount() != 2) {
            mIsExpandable = false;
        } else {
            mIsExpandable = true;
            mExpandableView = getChildAt(1);
            if (mIsExpendBegin) {
                mCurrentState = EXPAND;
                mExpandableView.setVisibility(VISIBLE);
            } else {
                mCurrentState = HIDE;
                mExpandableView.setVisibility(GONE);
            }
        }
    }

    public void hide() {
        if (!mIsExpandable) {
            return;
        }
        ValueAnimator anim = resizeHeightWithAnim(mExpandViewHeight, 0);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mExpandListener != null) {
                    mExpandListener.onHide();
                }
                mCurrentState = HIDE;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    public void expand() {
        if (!mIsExpandable) {
            return;
        }
        mExpandableView.setVisibility(VISIBLE);
        ValueAnimator anim = resizeHeightWithAnim(0, mExpandViewHeight);
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mExpandListener != null) {
                    mExpandListener.onExpand();
                }
                mCurrentState = EXPAND;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        anim.start();
    }

    @NonNull
    private ValueAnimator resizeHeightWithAnim(int start, int end) {
        ValueAnimator anim = ValueAnimator.ofInt(start, end);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final int value = (int) animation.getAnimatedValue();
                ViewCompat.postOnAnimation(mExpandableView, new Runnable() {
                    @Override
                    public void run() {
                        mExpandableView.getLayoutParams().height = value;
                        mExpandableView.requestLayout();
                    }
                });
            }
        });
        anim.setDuration(300);
        return anim;
    }


    public void setExpandListener(ExpandListener expandListener) {
        this.mExpandListener = expandListener;
    }

    public void toggle() {
        if (mCurrentState == EXPAND) {
            hide();
        } else {
            expand();
        }
    }

    public interface ExpandListener {

        void onExpand();

        void onHide();
    }
}
