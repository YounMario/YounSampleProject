package com.younchen.younsampleproject.ui.view.ExpandableLayout;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

    //    private int mExpandViewHeight;
    private boolean mIsExpendBegin;
    private int mDefaultVisibleHeight;
    private int mFullVisibleHeight;

    private int mExpandableViewId;
    private boolean mHasMeasuredExpendViewHeight = false;

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout, defStyleAttr, 0);
        mIsExpendBegin = a.getBoolean(R.styleable.ExpandableLayout_is_expend, false);
        mDefaultVisibleHeight = (int) a.getDimension(R.styleable.ExpandableLayout_visible_part_height, 0);
        mFullVisibleHeight = (int) a.getDimension(R.styleable.ExpandableLayout_expend_height, 0);
        mExpandableViewId = a.getResourceId(R.styleable.ExpandableLayout_expend_view_id, -1);
        a.recycle();
        checkExpandable();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        setData();
    }

    private void checkExpandable() {
        if (mExpandableViewId == -1) {
            mIsExpandable = false;
        } else {
            mIsExpandable = true;
            if (mIsExpendBegin) {
                mCurrentState = EXPAND;
            } else {
                mCurrentState = HIDE;
            }
        }
    }


    private void setData() {
        if (mDefaultVisibleHeight == 0) {
            this.mDefaultVisibleHeight = getMeasuredHeight();
        }
        if (mFullVisibleHeight == 0) {
            this.mDefaultVisibleHeight = getMeasuredHeight();
        }
    }

    public void setVisiblePartHeight(int visiblePartHeight) {
        this.mDefaultVisibleHeight = visiblePartHeight;
        refresh();
    }

    public void refresh() {
        if (mCurrentState == EXPAND) {
            expendWithoutAnim();
        } else {
            hideWithoutAnim();
        }
    }


    public void hide() {
        if (!mIsExpandable) {
            return;
        }
        ValueAnimator anim = resizeHeightWithAnim(mFullVisibleHeight, mDefaultVisibleHeight);
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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHasMeasuredExpendViewHeight) {
            return;
        }
        mExpandableView = findViewById(mExpandableViewId);
        if (mExpandableView == null) {
            return;
        }

        View expandableView = null;
        for (int i = 0; i < getChildCount(); i++) {
            if (mExpandableView == getChildAt(i)) {
                expandableView = getChildAt(i);
                break;
            }
        }
        if (expandableView != null) {
            if (mExpandableView instanceof RecyclerView) {
                RecyclerView recyclerView = (RecyclerView) mExpandableView;
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    mDefaultVisibleHeight = recyclerView.findViewHolderForLayoutPosition(0).itemView.getMeasuredHeight();
                }
            }
            mFullVisibleHeight = expandableView.getMeasuredHeight();
            mHasMeasuredExpendViewHeight = true;
        }
        if(mCurrentState == EXPAND){
            mExpandableView.getLayoutParams().height = mFullVisibleHeight;
        }else {
            mExpandableView.getLayoutParams().height = mDefaultVisibleHeight;
        }
        mExpandableView.requestLayout();
    }

    public void expand() {
        if (!mIsExpandable) {
            return;
        }
        ValueAnimator anim = resizeHeightWithAnim(mDefaultVisibleHeight, mFullVisibleHeight);
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

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
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

    public void hideWithoutAnim() {
        if (mExpandableView == null) {
            return;
        }
        ViewCompat.postOnAnimation(mExpandableView, new Runnable() {
            @Override
            public void run() {
                mExpandableView.getLayoutParams().height = mDefaultVisibleHeight;
                mExpandableView.requestLayout();
            }
        });
        if (mExpandListener != null) {
            mExpandListener.onHide();
        }
        mCurrentState = HIDE;
    }

    public void expendWithoutAnim() {
        if (mExpandableView == null) {
            return;
        }
        ViewCompat.postOnAnimation(mExpandableView, new Runnable() {
            @Override
            public void run() {
                mExpandableView.getLayoutParams().height = mFullVisibleHeight;
                mExpandableView.requestLayout();
            }
        });
        if (mExpandListener != null) {
            mExpandListener.onExpand();
        }
        mCurrentState = EXPAND;
    }

    public interface ExpandListener {

        void onExpand();

        void onHide();
    }
}
